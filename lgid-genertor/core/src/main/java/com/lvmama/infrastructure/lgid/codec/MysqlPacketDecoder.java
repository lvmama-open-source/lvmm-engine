package com.lvmama.infrastructure.lgid.codec;

import com.lvmama.infrastructure.codec.utils.PacketBuilder;
import com.lvmama.infrastructure.protocal.message.response.PacketWrapper;
import io.netty.buffer.ByteBuf;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/3 17:30
 * @Description:
 */
public abstract class MysqlPacketDecoder extends AbstractMysqlPacketDecoder {



    @Override
    public PacketWrapper decodeHeader(ByteBuf in) {
        return PacketBuilder.getInstance().buildWrapper(in);
    }
}
