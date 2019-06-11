package com.lvmama.infrastructure.lgid.codec;

import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import com.lvmama.infrastructure.codec.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/3 17:43
 * @Description:
 */
public abstract class MysqlPacketEncoder<M extends ByteBuf> extends AbstractMysqlPacketEncoder {

    @Override
    public ByteBuf encodePacket(MySQLPackets in) {
        return null;
    }

    protected ByteBuf mysqlPackages(int sequence_id, ByteBuf payload){
        ByteBuf payload_length = Unpooled.buffer();
        ByteBuf sequence_id_buf = Unpooled.buffer();
        CompositeByteBuf compositeByteBuf =   Unpooled.compositeBuffer(3);
        payload_length.writeMediumLE(payload.writerIndex() &0xffffff);
        compositeByteBuf.addComponent(true,payload_length);
        compositeByteBuf.addComponent(true,sequence_id_buf.writeByte(ByteBufUtils.saftyIntToB1(sequence_id)));
        compositeByteBuf.addComponent(true,payload);
        return compositeByteBuf;
    }
}
