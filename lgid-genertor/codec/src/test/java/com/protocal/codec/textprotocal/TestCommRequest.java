package com.protocal.codec.textprotocal;

import com.lvmama.infrastructure.codec.utils.PacketBuilder;
import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import com.lvmama.infrastructure.protocal.message.OKPacket;
import com.lvmama.infrastructure.protocal.message.response.PacketWrapper;
import com.lvmama.infrastructure.protocal.message.response.client.HandshakeResponse;
import com.lvmama.infrastructure.protocal.message.response.client.MysqlTextPacket;
import com.protocal.codec.handshake.BaseTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/4 14:33
 * @Description:
 */
public class TestCommRequest extends BaseTest {

    @Test
    public void testHandComRequest1() throws Exception{
//        File file = new File("./handshakeServerRes.txt");
//        file.input

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("textprotocal/com_q1.txt"));
        PacketWrapper<MysqlTextPacket> packets = PacketBuilder.getInstance().buildMysqlTextProtocalPackt(byteBuf);
        System.out.println(packets.getPackets().toString());

    }


    @Test
    public void testHandComRequest1Res() throws Exception{
//        File file = new File("./handshakeServerRes.txt");
//        file.input

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("textprotocal/com_q1_res.txt"));

        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));;
        PacketWrapper<HandshakeResponse> wrapper = PacketBuilder.getInstance().buildOkPacket(byteBuf, MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_PROTOCOL_41.code);
//        System.out.println(wrapper.getPayloadLength());
        Assert.assertTrue(wrapper.getPayloadLength()==7);
//        Assert.assertTrue(wrapper.getPackets().);
    }
}
