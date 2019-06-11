package com.lvmama.com.bootstrap.test;

import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import com.lvmama.infrastructure.codec.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/3 11:37
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        int hex = 0x85a67f00;
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(hex);
        System.out.println(ByteBufUtil.prettyHexDump(buf));
        System.out.println(buf.readIntLE()& MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_PROTOCOL_41.code);
    }
}
