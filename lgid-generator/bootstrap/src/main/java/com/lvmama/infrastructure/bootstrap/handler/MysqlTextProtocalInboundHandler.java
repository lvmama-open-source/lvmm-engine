package com.lvmama.infrastructure.bootstrap.handler;

import com.lvmama.infrastructure.protocal.message.HandShakeOk;
import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import com.lvmama.infrastructure.protocal.message.OKPacket;
import com.lvmama.infrastructure.protocal.message.response.PacketWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/29 14:44
 * @Description:
 */
public class MysqlTextProtocalInboundHandler extends SimpleChannelInboundHandler<PacketWrapper> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketWrapper packets) throws Exception {


        OKPacket okPacket = new OKPacket(packets.getSequenceId(), MySQLPackets.SERVER_STATUS.SERVER_STATUS_AUTOCOMMIT.code);
        ctx.write(okPacket);

//        MysqlTextPacket packets = new MysqlTextPacket(in.getPayload());

//        switch (packets.getCommand()) {
//            case MySQLPackets.MYSQL_TEXT_PROTOCAL.COM_SLEEP.code:
//                break;
//
//            case MySQLPackets.MYSQL_TEXT_PROTOCAL.COM_QUIT.code:
//                break;
//
//        }
//
//        System.out.println("textType:"+packets.getCommand() +" payload lenght:");
//        System.out.println(packets.getStatement());

//        OKPacket ok = new OKPacket();
//        ctx.writeAndFlush(ok.writeForProtocal41(packets.getSequence_id()+1,MySQLPackets.SERVER_STATUS.SERVER_STATUS_AUTOCOMMIT.code));
    }

    /**
     * Notifies the handler that the last call made to channelRead() was last message int the current
     * channel
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
//        System.out.println("xxxxxxxxxxx channelReadComplete");
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 处理异常
     * Called if an Exception is thrown during the read operation
     * 在read期间如果发生Exception 会调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
