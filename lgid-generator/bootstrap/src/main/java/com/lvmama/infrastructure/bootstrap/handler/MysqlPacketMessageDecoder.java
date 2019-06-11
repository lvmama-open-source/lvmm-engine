package com.lvmama.infrastructure.bootstrap.handler;

import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/31 16:34
 * @Description:
 */
public class MysqlPacketMessageDecoder extends MessageToMessageDecoder<MySQLPackets> {

    @Override
    protected void decode(ChannelHandlerContext ctx, MySQLPackets msg, List<Object> out) {

    }
}
