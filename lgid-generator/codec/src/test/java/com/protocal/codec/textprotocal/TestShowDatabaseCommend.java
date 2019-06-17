package com.protocal.codec.textprotocal;

import com.lvmama.infrastructure.codec.utils.PacketBuilder;
import com.lvmama.infrastructure.protocal.message.OKPacket;
import com.lvmama.infrastructure.protocal.message.response.PacketWrapper;
import com.lvmama.infrastructure.protocal.message.response.client.ComQueryResponse;
import com.lvmama.infrastructure.protocal.message.response.client.MysqlTextPacket;
import com.protocal.codec.handshake.BaseTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/12 09:00
 * @Description:
 */
public class TestShowDatabaseCommend extends BaseTest {

    @Test
    public void testRep(){
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/showdatabase/show_database_rep.txt"));
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
        PacketWrapper packetWrapper = PacketBuilder.getInstance().buildOkPacket(byteBuf,7);
        System.out.println(packetWrapper.getPackets().toString());
        if(packetWrapper.getPackets() instanceof OKPacket){
            OKPacket okPacket = (OKPacket)packetWrapper.getPackets();
            System.out.println(okPacket.getInfo());
        }
    }

    @Test
    public void testReq(){
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/showdatabase/show_database_req.txt"));
        PacketWrapper<MysqlTextPacket> packets = PacketBuilder.getInstance().buildMysqlTextProtocalPackt(byteBuf);
        System.out.println(packets.getPackets().toString());
    }

    @Test
    public void testReq2(){
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/comquery/insert_table_req.txt"));
        PacketWrapper<MysqlTextPacket> packets = PacketBuilder.getInstance().buildMysqlTextProtocalPackt(byteBuf);
        System.out.println(packets.getPackets().toString());
    }

    @Test
    public void testRep2(){
        int cap = 0xffffc3ff;
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/comquery/select_table_resp2.txt"));
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
        ComQueryResponse comQueryResponse = PacketBuilder.getInstance().buildSelectResponse(byteBuf, cap);
        System.out.println(comQueryResponse.toString());
    }

    @Test
    public void testInsertRep(){
        int cap = 0xffffc3ff;
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/comquery/insert_table_resp.txt"));
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
        ComQueryResponse comQueryResponse = PacketBuilder.getInstance().buildInsertOrUpdateResponse(byteBuf, cap);
        System.out.println(comQueryResponse.toString());
    }
}
