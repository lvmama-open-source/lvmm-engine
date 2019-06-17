package com.protocal.codec.textprotocal.comquery;

import com.lvmama.infrastructure.codec.utils.PacketBuilder;
import com.lvmama.infrastructure.protocal.message.response.PacketWrapper;
import com.lvmama.infrastructure.protocal.message.response.client.ComQueryResponse;
import com.lvmama.infrastructure.protocal.message.response.client.MysqlTextPacket;
import com.protocal.codec.handshake.BaseTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Auther: xiaoyulin
 * @Date: 2019/6/16 17:30
 * @Description:
 */
public class TestComQueryInsert extends BaseTest {

    @Test
    public void testInsertReq(){
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/comquery/insert_table_req.txt"));
        PacketWrapper<MysqlTextPacket> packets = PacketBuilder.getInstance().buildMysqlTextProtocalPackt(byteBuf);
        Assert.assertTrue(packets.getPackets().getStatement() != null);
    }

    @Test
    public void testInsertRep(){
        int cap = 0xffffc3ff;
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/comquery/insert_table_resp.txt"));
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
        ComQueryResponse comQueryResponse = PacketBuilder.getInstance().buildInsertOrUpdateResponse(byteBuf, cap);
        Assert.assertNotNull(comQueryResponse.getOkPacket());
    }
}
