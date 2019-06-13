package com.protocal.codec.handshake;

import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import com.lvmama.infrastructure.protocal.message.OKPacket;
import com.lvmama.infrastructure.protocal.message.response.PacketWrapper;
import com.lvmama.infrastructure.protocal.message.response.server.ServerHandshakeV10Packet;
import com.lvmama.infrastructure.codec.utils.PacketBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/4 11:12
 * @Description: client------connect()------>server
 *                client<-----handshake()---server
 *                client-----handshake response Package()--->server
 *                client<-----ok package()---server
 *
 * mysql的握手流程 报文解析 V10的版本 V9待实现
 * 参照文档 https://dev.mysql.com/doc/internals/en/connection-phase-packets.html#packet-Protocol::Handshake
 */

public class TestHandshakeV10 extends BaseTest {

    /**
     * 握手协议解析
     * 读取服务端返回的报文信息
     * 服务端响应握手报文
     * @throws Exception
     */
    @Test
    public void testHandshakeServerRes() throws Exception{
//        File file = new File("./handshakeServerRes.txt");
//        file.input

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/handshake/handshakeServerRes.txt"));
        PacketWrapper<ServerHandshakeV10Packet> wrapper = PacketBuilder.getInstance().buildServerHandshakeV10PacketBuf(byteBuf);
//        wrapper.get
        ServerHandshakeV10Packet serverHandshakeV10Packet = (ServerHandshakeV10Packet)wrapper.getPackets();

        Assert.assertTrue(wrapper.getPayloadLength()==74);
        Assert.assertTrue(wrapper.getSequenceId()==0);

        Assert.assertTrue(serverHandshakeV10Packet.getProtocolVersion()==10);
        Assert.assertTrue(serverHandshakeV10Packet.getServerVersion().equals("5.6.25"));
//      System.out.println(ByteBufUtil.prettyHexDump(byteBuf));;

    }

    /**
     * 处理客户端请求报文 HandshakeResponse
     * 这里是客户端发送的认证信息
     * mysql的握手流程是当tcp三次握手成功后，服务端响应   ServerHandshakeV10Packet 报文，然后 客户端发送 HandshakeResponse 经过服务端验证后
     * 服务端返回 OKPackage
     *
     * @throws Exception
     */
    @Test
    public void testHandShakeClientReq() throws Exception{

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/handshake/handshakeClientReq.txt"));

        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));;

    }


    /**
     *
     */
    @Test
    public void testHandOkPackage(){
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/com_q1_res.txt"));

        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));;
        PacketWrapper<OKPacket> wrapper = PacketBuilder.getInstance().buildOkPacket(byteBuf, MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_PROTOCOL_41.code);
//        System.out.println(wrapper.getPayloadLength());
        Assert.assertTrue(wrapper.getPayloadLength()==7);
    }
}
