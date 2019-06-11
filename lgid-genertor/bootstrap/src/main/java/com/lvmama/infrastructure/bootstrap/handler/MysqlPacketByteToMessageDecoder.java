package com.lvmama.infrastructure.bootstrap.handler;

import com.lvmama.infrastructure.bootstrap.LgidBootstrap;
import com.lvmama.infrastructure.codec.utils.PacketBuilder;
import com.lvmama.infrastructure.lgid.codec.MysqlPacketDecoder;
import com.lvmama.infrastructure.protocal.message.response.PacketWrapper;
import com.lvmama.infrastructure.protocal.message.response.client.HandshakeResponse;
import com.lvmama.infrastructure.codec.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/31 16:01
 * @Description:
 */
public class MysqlPacketByteToMessageDecoder extends MysqlPacketDecoder {

//    @Override
//    protected void decode(ChannelHandlerContext ctx, MySQLPackets msg, List<Object> out) {
//
//    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
//        this.decodeHandShakePacket(in);
        MySQLSession session= LgidBootstrap.sessions.get(ctx.channel());
        if (session!=null && !session.isAuthed()) {
            PacketWrapper wrapper = decodeHeader(in);
            HandshakeResponse packets =  decodeHandShakePacket(in);
            /**
             * 设置session的客户端capability
             */
            session.setCapabilityFlags(packets.getCapabilityFlags());
            packets.setSequence_id(wrapper.getSequenceId());
            wrapper.setPackets(packets);
            out.add(wrapper);
        } else {

            out.add(PacketBuilder.getInstance().buildMysqlTextProtocalPackt(in));
        }
    }


//    private MySQLPackets decodeTextProtocalPackt(ByteBuf in)  throws Exception{
//        MysqlTextPacket textProtocalPacket = new MysqlTextPacket();
//        textProtocalPacket.setCommand(in.readByte());
//        textProtocalPacket.setStatement(ByteBufUtils.readEOFString(in));;
//        return textProtocalPacket;
//    }


    private HandshakeResponse decodeHandShakePacket(ByteBuf byteBuf) throws Exception {

        HandshakeResponse handshakeResponse41 = new HandshakeResponse(byteBuf);
        handshakeResponse41.setCapabilityFlags(byteBuf.readIntLE());
        handshakeResponse41.setMaxPacketSize(byteBuf.readIntLE());

        if (handshakeResponse41.isClientProtocal41()) {

            handshakeResponse41.setCharacterSet(byteBuf.readByte());

            byteBuf.readBytes(23);//读取23个bit的保留bit 都是[00]

            handshakeResponse41.setUsername(ByteBufUtils.nullToString(byteBuf));

            if (handshakeResponse41.isNeedAuth()) {
                handshakeResponse41.setPassword(ByteBufUtils.lengthEncoded2String(byteBuf));
            } else if (handshakeResponse41.isSSLConnection()) {
                //empty implemention
            } else {
//            string[NUL]    auth-response
                //empty implemention
            }

            if (handshakeResponse41.isWithDb()) {
                handshakeResponse41.setDatabase(ByteBufUtils.nullToString(byteBuf));
            }

            if (handshakeResponse41.isExsitClientPluginAuth()) {
                handshakeResponse41.setAuthPluginName(ByteBufUtils.nullToString(byteBuf));
            }

            if (handshakeResponse41.isHasConnAttr()) {

                byte[] attrs = ByteBufUtils.lengthEncoded2B(byteBuf);
                handshakeResponse41.setConnectionAttr(attrs);

            }
            return handshakeResponse41;
        } else { //Protocol::HandshakeResponse320:
            handshakeResponse41.setUsername(ByteBufUtils.nullToString(byteBuf));
            if (handshakeResponse41.isWithDb()) {
                handshakeResponse41.setPassword(ByteBufUtils.lengthEncoded2String(byteBuf));
                handshakeResponse41.setDatabase(ByteBufUtils.nullToString(byteBuf));
            } else {
//                byte[] passwords =
                handshakeResponse41.setPassword(ByteBufUtils.readEOFString(byteBuf));
            }
        }
        return handshakeResponse41;
    }



}
