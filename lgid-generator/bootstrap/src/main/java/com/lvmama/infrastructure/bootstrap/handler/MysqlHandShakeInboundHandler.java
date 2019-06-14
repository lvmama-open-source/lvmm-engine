package com.lvmama.infrastructure.bootstrap.handler;

import com.lvmama.infrastructure.bootstrap.LgidBootstrap;
import com.lvmama.infrastructure.protocal.message.HandShakeOk;
import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import com.lvmama.infrastructure.protocal.message.response.PacketWrapper;
import com.lvmama.infrastructure.protocal.message.response.server.ServerHandshakeV10Packet;
import io.netty.channel.*;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/28 21:33
 * @Description:
 */
public class MysqlHandShakeInboundHandler extends ChannelInboundHandlerAdapter  {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * tcp三次握手成功后
         * 执行inithandshake
         */
        MySQLSession mySQLSession = new MySQLSession(ctx.channel());
        LgidBootstrap.sessions.put(ctx.channel(),mySQLSession);

        ServerHandshakeV10Packet handshakeV10Packet = new ServerHandshakeV10Packet();
//        ByteBuf buf = handshakeV10Packet.writeBuf();
//        handshakeV10Packet.setPrev_sequence_id(1);
        mySQLSession.setCapabilityFlags(handshakeV10Packet.getCapabilityFlags());
//        ctx.writeAndFlush(buf);
        ctx.write(handshakeV10Packet);

    }




    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ctx.fireChannelRead(msg);
        PacketWrapper wr  = (PacketWrapper) msg;

        MySQLPackets basePacket  = wr.getPackets();

        MySQLSession mySQLSession =  LgidBootstrap.sessions.get(ctx.channel());

        if (mySQLSession.isAuthed()) {
            ctx.fireChannelRead(msg);
            return;
        }


        HandShakeOk okPacket = new HandShakeOk(wr.getSequenceId(),MySQLPackets.SERVER_STATUS.SERVER_STATUS_AUTOCOMMIT.code);
        ctx.write(okPacket);

//        if (basePacket instanceof HandshakeResponse &&((HandshakeResponse)(basePacket)).isClientProtocal41()) {
//            OKPacket okPacket = new OKPacket();
//            ByteBuf pack = okPacket.writeForProtocal41(((HandshakeResponse)(basePacket)).getSequence_id()+1, MySQLPackets.SERVER_STATUS.SERVER_STATUS_AUTOCOMMIT.code);
//            ChannelFuture future =  ctx.writeAndFlush(pack);
//            future.addListener((ChannelFutureListener)->{
//                mySQLSession.setAuthed(true);
//            });
//        }

    }


}
