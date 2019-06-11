package com.lvmama.infrastructure.bootstrap.handler;

import com.lvmama.infrastructure.bootstrap.LgidBootstrap;
import com.lvmama.infrastructure.lgid.codec.MysqlPacketEncoder;
import com.lvmama.infrastructure.protocal.message.HandShakeOk;
import com.lvmama.infrastructure.protocal.message.OKPacket;
import com.lvmama.infrastructure.protocal.message.response.server.ServerHandshakeV10Packet;
import com.lvmama.infrastructure.codec.utils.PacketBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/3 16:51
 * @Description:
 */
public class MysqlPacketMessageEncoder extends MysqlPacketEncoder {



    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
        if (msg instanceof ServerHandshakeV10Packet) {
//            System.out.println("ServerHandshakeV10Packet:"+msg);
            ServerHandshakeV10Packet serverHandshakeV10Packet = (ServerHandshakeV10Packet)msg;
//            ByteBuf buf =  writeBuf(serverHandshakeV10Packet);
            ByteBuf buf = this.mysqlPackages(0,PacketBuilder.getInstance().buildServerHandshakeV10PacketBuf(serverHandshakeV10Packet));
//            ctx.writeAndFlush(buf);
            out.writeBytes(buf);
        }  else if(msg instanceof HandShakeOk){
//           System.out.println("OKPacket");
            MySQLSession mySQLSession =  LgidBootstrap.sessions.get(ctx.channel());
            HandShakeOk ok = (HandShakeOk)msg;
            ByteBuf buf = this.mysqlPackages(ok.getSequence_id()+1, PacketBuilder.getInstance().buildOkPacket(ok));

            out.writeBytes(buf);
            mySQLSession.setAuthed(true);
        } else if(msg instanceof OKPacket){
//            System.out.println("OKPacket");
            OKPacket ok = (OKPacket)msg;
            ByteBuf buf = this.mysqlPackages(ok.getSequence_id()+1, PacketBuilder.getInstance().buildOkPacket(ok));
//            ctx.writeAndFlush(buf);
            out.writeBytes(buf);
        }
    }




}
