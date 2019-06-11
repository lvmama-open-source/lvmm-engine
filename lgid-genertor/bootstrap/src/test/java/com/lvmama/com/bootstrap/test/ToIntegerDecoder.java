package com.lvmama.com.bootstrap.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/31 15:08
 * @Description:
 */
public class ToIntegerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes()>=4) {
            out.add(in.readInt());
        }
    }
}
