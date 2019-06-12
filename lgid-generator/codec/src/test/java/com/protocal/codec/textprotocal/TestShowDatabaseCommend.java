package com.protocal.codec.textprotocal;

import com.lvmama.infrastructure.codec.utils.PacketBuilder;
import com.lvmama.infrastructure.protocal.message.response.PacketWrapper;
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
        byteBuf.writeBytes(this.StringToBytes("textprotocal/showdatabase/show_database_rep.txt"));
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
    }

    @Test
    public void testReq(){
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("textprotocal/showdatabase/show_database_req.txt"));
        PacketWrapper<MysqlTextPacket> packets = PacketBuilder.getInstance().buildMysqlTextProtocalPackt(byteBuf);
        System.out.println(packets.getPackets().toString());
    }
}
