package com.lvmama.infrastructure.lgid.codec;

import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/3 17:27
 * @Description:
 */
public  abstract  class AbstractMysqlPacketEncoder<M extends ByteBuf> extends MessageToByteEncoder {

    public abstract M encodePacket(MySQLPackets in);
}
