package com.lvmama.infrastructure.codec.utils;

import com.lvmama.infrastructure.protocal.message.EOFPacket;
import com.lvmama.infrastructure.protocal.message.ErrorPacket;
import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import com.lvmama.infrastructure.protocal.message.OKPacket;
import com.lvmama.infrastructure.protocal.message.response.PacketWrapper;
import com.lvmama.infrastructure.protocal.message.response.client.*;
import com.lvmama.infrastructure.protocal.message.response.server.ServerHandshakeV10Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/3 19:49
 * @Description:
 */
public class PacketBuilder {

    public static  volatile  PacketBuilder instance;


    private PacketBuilder() {

    }

    public static PacketBuilder getInstance() {
        if (instance==null) {
            synchronized (PacketBuilder.class) {
                    instance = new PacketBuilder();

            }
        }
        return instance;

    }

    public   ByteBuf buildOkPacket(OKPacket okPacket){
        Assert.assertTrue(okPacket.getStatusFlags()!=0);
        byte header=0x00;
        ByteBuf payload = Unpooled.buffer();
        payload.writeByte(header);
        payload.writeBytes(new byte[]{0x00});//affected_rows
        payload.writeBytes(new byte[]{0x00});//last_insert_id
        payload.writeShortLE(okPacket.getStatusFlags());
        payload.writeShort(0);
        ByteBuf sequence_id = Unpooled.buffer();
        sequence_id.writeByte(0x00);
        return  payload;
    }

    public  PacketWrapper buildMysqlTextProtocalPackt(ByteBuf in) {
        PacketWrapper wrapper =  buildWrapper(in);

        MysqlTextPacket textProtocalPacket = new MysqlTextPacket();
        textProtocalPacket.setCommand(ByteBufUtils.saftyIntToB1(in.readByte()));
        textProtocalPacket.setStatement(ByteBufUtils.readEOFString(in));;
        wrapper.setPackets(textProtocalPacket);
        return wrapper;
    }

    public ComQueryResponse buildInsertOrUpdateResponse(ByteBuf in, int capabilities) {
        ComQueryResponse comQueryResponse = new ComQueryResponse();
        this.buildWrapper(in);
        int header = ByteBufUtils.saftyIntToB1(in.readByte());
        if(header == 0x00) {
            comQueryResponse.setOkPacket(buildOKPackt(header, in, capabilities));
        }else if(header == 0xff){
            comQueryResponse.setErrorPacket(buildErrorPacket(header, in, capabilities));
        }
        return comQueryResponse;
    }


    public ComQueryResponse buildSelectResponse(ByteBuf in, int capabilities) {
        ComQueryResponse comQueryResponse = new ComQueryResponse();
        this.buildWrapper(in);
        ComQueryResponseHeaderPacket comQueryResponseHeaderPacket = buildComQueryResponseHeaderPacket(in);
        comQueryResponse.setComQueryResponseHeaderPacket(comQueryResponseHeaderPacket);

        int fieldCount = comQueryResponseHeaderPacket.getFieldCount();
        if(fieldCount > 0) {
            List<ColumnDefinitionPacket> columnDefinitionPacketList = new ArrayList<>();
            for (int i = 0; i < fieldCount; i++) {
                this.buildWrapper(in);
                columnDefinitionPacketList.add(buildColumnDefinitionPacket(in, capabilities));
            }
            comQueryResponse.setColumnDefinitionPacketList(columnDefinitionPacketList);

            List<EOFPacket> eofPacketList = new ArrayList<>();
            // First EOF
            PacketWrapper eofWrapper = buildWrapper(in);
            eofPacketList.add(buildEOFPacket(in, capabilities));

            List<ResultsetRowPacket> result = new ArrayList<>();
            PacketWrapper wrapper = buildWrapper(in);
            // payload长度>eof,或者header != 0xfe
            int header = ByteBufUtils.saftyIntToB1(in.readByte());
            while(wrapper.getPayloadLength() > eofWrapper.getPayloadLength() || header != 0xfe){
                result.add(buildResultsetRowPacket(header, in, columnDefinitionPacketList));
                wrapper = buildWrapper(in);
                header = ByteBufUtils.saftyIntToB1(in.readByte());
            }
            comQueryResponse.setResultsetRowPacketList(result);

            // Last EOF || error
            if(header == 0xfe){
                eofPacketList.add(buildEOFPacket(header, in, capabilities));
            }else if(header == 0xff){
                comQueryResponse.setErrorPacket(buildErrorPacket(header, in, capabilities));
            }
            comQueryResponse.setEofPacketList(eofPacketList);
        }else if(fieldCount == 0){
            // TODO:?
        }else{
            // TODO:?
        }

        return comQueryResponse;
    }

    public ComQueryResponseHeaderPacket buildComQueryResponseHeaderPacket(ByteBuf in){
        ComQueryResponseHeaderPacket comQueryResponseHeaderPacket = new ComQueryResponseHeaderPacket();
        int fieldCount = ByteBufUtils.B1ToInt(in.readByte());
        comQueryResponseHeaderPacket.setFieldCount(fieldCount);
        return comQueryResponseHeaderPacket;
    }

    /**
     *
     * @param in
     * @param columnDefinitionPacketList
     * @return
     */
    public ResultsetRowPacket buildResultsetRowPacket(int size, ByteBuf in, List<ColumnDefinitionPacket> columnDefinitionPacketList) {
        int len = columnDefinitionPacketList.size();
        ResultsetRowPacket resultsetRowPacket = new ResultsetRowPacket();
        Map<String, Object> result = new HashMap<>();
        for(int i = 0;i < len; i++){
            ColumnDefinitionPacket columnDefinitionPacket = columnDefinitionPacketList.get(i);
//            int columnType = columnDefinitionPacket.getColumnType();
            if(i > 0) {
                size = ByteBufUtils.saftyIntToB1(in.readByte());
            }
            result.put(columnDefinitionPacket.getName(), ByteBufUtils.readFixedString(in, size));

        }
        resultsetRowPacket.setResult(result);
        return resultsetRowPacket;
    }

    public EOFPacket buildEOFPacket(int header, ByteBuf in, int capabilities) {
        EOFPacket eofPacket = new EOFPacket();
        eofPacket.setHeader(header);
        if((capabilities & MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_PROTOCOL_41.code) > 0){
            eofPacket.setWarnings(in.readShortLE());
            eofPacket.setStatusFlags(in.readShortLE());
        }
        return eofPacket;
    }

    public EOFPacket buildEOFPacket(ByteBuf in, int capabilities) {
        return buildEOFPacket(ByteBufUtils.saftyIntToB1(in.readByte()), in, capabilities);
    }

    public ColumnDefinitionPacket buildColumnDefinitionPacket(ByteBuf in, int capabilities){

        ColumnDefinitionPacket columnDefinitionPacket = new ColumnDefinitionPacket();
        columnDefinitionPacket.setCatalog(ByteBufUtils.lengthEncoded2String(in));
        columnDefinitionPacket.setSchemal(ByteBufUtils.lengthEncoded2String(in));
        columnDefinitionPacket.setTable(ByteBufUtils.lengthEncoded2String(in));
        columnDefinitionPacket.setOrgTable(ByteBufUtils.lengthEncoded2String(in));
        columnDefinitionPacket.setName(ByteBufUtils.lengthEncoded2String(in));
        columnDefinitionPacket.setOrgName(ByteBufUtils.lengthEncoded2String(in));
        columnDefinitionPacket.setNextLength(ByteBufUtils.saftyIntToB1(in.readByte()));
        columnDefinitionPacket.setCharacterSet(in.readShortLE());
        columnDefinitionPacket.setColumnLength(in.readIntLE());
        columnDefinitionPacket.setColumnType(ByteBufUtils.saftyIntToB1(in.readByte()));
        if((capabilities & MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_LONG_FLAG.code) > 0){
            columnDefinitionPacket.setFlags(in.readShortLE());
            columnDefinitionPacket.setDecimals(in.readByte());
        }else{
            columnDefinitionPacket.setFlags(ByteBufUtils.saftyIntToB1(in.readByte()));
            columnDefinitionPacket.setDecimals(ByteBufUtils.saftyIntToB1(in.readByte()));
        }

        in.skipBytes(2);
        return columnDefinitionPacket;
    }

    public  ErrorPacket buildErrorPacket(int header, ByteBuf in,int capabilities){
        ErrorPacket errorPacket = new ErrorPacket();
        errorPacket.setHeader(header);
        errorPacket.setErrorCode(in.readShortLE());
        if ((capabilities & MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_PROTOCOL_41.code)>0) {
            errorPacket.setSqlStateMarker(ByteBufUtils.readFixedString(in,1));
            errorPacket.setSqlState(ByteBufUtils.readFixedString(in,5));
        }
        errorPacket.setErrorMessage(ByteBufUtils.readEOFString(in));
        return errorPacket;
    }

    public  ErrorPacket buildErrorPacket(ByteBuf in,int capabilities){
        return buildErrorPacket(ByteBufUtils.saftyIntToB1(in.readByte()), in, capabilities);
    }

    public OKPacket buildOKPackt(int header, ByteBuf in,int capabilities){
        OKPacket okPacket = new OKPacket();
        okPacket.setHeader(header);
        okPacket.setAffectedRows(ByteBufUtils.lengthEncodedInteger(in));
        okPacket.setLastInsertId(ByteBufUtils.lengthEncodedInteger(in));
//            okPacket.setStatusFlags(ByteBufUtils.saftyIntToB1(in.readByte()));
        if ((capabilities& MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_PROTOCOL_41.code)>0) {
            okPacket.setStatusFlags(ByteBufUtils.saftyIntToB2(in.readShortLE()));
            okPacket.setWarnings(ByteBufUtils.saftyIntToB2(in.readShortLE()));
        } else if((capabilities & MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_TRANSACTIONS.code)>0) {
            okPacket.setStatusFlags(ByteBufUtils.saftyIntToB2(in.readShortLE()));
        }

        if(in.readableBytes()>0 && (capabilities & MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_SESSION_TRACK.code)>0) {
            okPacket.setInfo(ByteBufUtils.lengthEncoded2String(in));
            if ((okPacket.getStatusFlags() & MySQLPackets.SERVER_STATUS.SERVER_SESSION_STATE_CHANGED.code)>0) {
                okPacket.setSessionStateChanges(ByteBufUtils.lengthEncoded2String(in));
//                 }
            }
        } else {
            if (in.readableBytes()>0) {
                okPacket.setInfo(ByteBufUtils.readEOFString(in));
            }
        }
        return okPacket;
    }

    public OKPacket buildOKPackt(ByteBuf in,int capabilities){
        int header = ByteBufUtils.saftyIntToB1(in.readByte());
        return buildOKPackt(header, in, capabilities);
    }

    public  PacketWrapper buildOkPacket(ByteBuf in,int capabilities){
//
//        OK: header = 0 and length of packet > 7
//
//        EOF: header = 0xfe and length of packet < 9

        PacketWrapper<OKPacket> wrapper =  buildWrapper(in);

        wrapper.setPackets(buildOKPackt(in, capabilities));
        return wrapper;

    }


    public  ByteBuf buildServerHandshakeV10PacketBuf(ServerHandshakeV10Packet serverHandshakeV10Packet){
        ByteBuf payload = Unpooled.buffer();
        payload.writeByte(serverHandshakeV10Packet.getProtocolVersion());
        //buf.writeBytes(server_version);
        ByteBufUtils.writeLengthNull(payload,serverHandshakeV10Packet.getServerVersion());

        payload.writeIntLE(serverHandshakeV10Packet.getThreadId());

        //      ByteBufUtils.writeL4B(buf,Thread.currentThread().getId());
//        ByteBufUtils.writeWithNull(payload,auth_plugin_data);
        payload.writeBytes(serverHandshakeV10Packet.getAuthPluginData().getBytes());
        payload.writeByte(serverHandshakeV10Packet.getFiller1());
        payload.writeShortLE(ByteBufUtils.B2ToInt(serverHandshakeV10Packet.getCapabilityFlag1()));
        payload.writeByte(serverHandshakeV10Packet.getCharacterSet());
        payload.writeBytes(serverHandshakeV10Packet.getStatusFlags());
        payload.writeShortLE(ByteBufUtils.B2ToInt(serverHandshakeV10Packet.getCapabilityFlags2()));
//        ByteBufUtils.writeWithNull(payload,":m;{zsS&^,");

        payload.writeByte(serverHandshakeV10Packet.getAuthPluginName().length());

        payload.writeBytes(new byte[10]);//string[10]     reserved (all [00])
        int auth_plugin_data_part_2_length =Math.max(13,serverHandshakeV10Packet.getAuthPluginName().length()-8);

//        System.out.println("xxx:auth_plugin_data_part_2_length"+auth_plugin_data_part_2_length);
        ByteBufUtils.writeLengthNull(payload,serverHandshakeV10Packet.getAuthPluginDataPart2().substring(0,auth_plugin_data_part_2_length-1));

        ByteBufUtils.writeLengthNull(payload,serverHandshakeV10Packet.getAuthPluginName());
//        int lengh = buf.capacity()&0xff
//        System.out.println("size:"+payload.writerIndex());
//        payload_length.writeMediumLE(payload.writerIndex() &0xffffff);
//        compositeByteBuf.addComponent(true,payload_length);
//        compositeByteBuf.addComponent(true,sequence_id);
//        /**
//         * mysql报文最大可以容纳16MB的数据，如果一次发送的数据超过16MB会被分割成多个mysql报文。
//         * 类似于tcp的报文分片  同一个报文可以被分隔程多个 那么相邻的报文报文是递增的
//         */
//        compositeByteBuf.addComponent(true,payload);
        ByteBuf sequence_id = Unpooled.buffer();
        sequence_id.writeByte(ByteBufUtils.saftyIntToB1(0));
//        return mysqlPackages(sequence_id,payload);
        return payload;
    }
    public  PacketWrapper buildWrapper(ByteBuf in){
        PacketWrapper wrapper = new PacketWrapper();
        int payload_length = in.readMediumLE();
        int sequence_id = (int) in.readByte();
        wrapper.setPayloadLength(payload_length);
        wrapper.setSequenceId(sequence_id);
        return wrapper;
    }

    public  PacketWrapper<ServerHandshakeV10Packet> buildServerHandshakeV10PacketBuf(ByteBuf in){
        ServerHandshakeV10Packet serverHandshakeV10Packet = new ServerHandshakeV10Packet();
        PacketWrapper wrapper =  buildWrapper(in);
        serverHandshakeV10Packet.setProtocolVersion(in.readByte());
        serverHandshakeV10Packet.setServerVersion(ByteBufUtils.nullToString(in));
        wrapper.setPackets(serverHandshakeV10Packet);
        return wrapper;
    }
}
