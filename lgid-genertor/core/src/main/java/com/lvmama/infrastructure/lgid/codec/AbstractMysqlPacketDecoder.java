package com.lvmama.infrastructure.lgid.codec;

import com.lvmama.infrastructure.protocal.message.response.PacketWrapper;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/3 16:46
 * @Description:
 */
public abstract  class AbstractMysqlPacketDecoder<M extends PacketWrapper>  extends ByteToMessageDecoder {
    public abstract M decodeHeader(ByteBuf in);
}
