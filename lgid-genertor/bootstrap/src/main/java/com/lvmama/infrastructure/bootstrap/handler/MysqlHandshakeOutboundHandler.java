package com.lvmama.infrastructure.bootstrap.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/31 21:48
 * @Description:
 */
public class MysqlHandshakeOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//      super.write(ctx, msg, promise);
//      System.out.println("==============  MysqlHandshakeOutboundHandlerMysqlHandshakeOutboundHandlerMysqlHandshakeOutboundHandlerMysqlHandshakeOutboundHandlerMysqlHandshakeOutboundHandler ");
        ctx.write(msg);
        ctx.flush();
        ReferenceCountUtil.release(msg);
        /**
         * 移除握手inbound outbound
         */
        ctx.pipeline().remove(MysqlHandShakeInboundHandler.class);
//        ctx.pipeline().remove(MysqlHandshakeOutboundHandler.class);
    }

}
