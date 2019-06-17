package com.protocal.codec.textprotocal.comquery;

import com.lvmama.infrastructure.codec.utils.PacketBuilder;
import com.lvmama.infrastructure.protocal.message.response.PacketWrapper;
import com.lvmama.infrastructure.protocal.message.response.client.ComQueryResponse;
import com.lvmama.infrastructure.protocal.message.response.client.MysqlTextPacket;
import com.protocal.codec.handshake.BaseTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/14 09:40
 * @Description:
 */
public class TestComQuerySelect extends BaseTest {

    @Test
    public void testComQuerySelectReq(){
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/comquery/com_query_select_req.txt"));
        PacketWrapper<MysqlTextPacket> packets = PacketBuilder.getInstance().buildMysqlTextProtocalPackt(byteBuf);
        System.out.println(packets.getPackets().toString());
    }

    @Test
    public void testRep2(){
        int cap = 0xffffc3ff;
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/comquery/select_table_resp2.txt"));
//        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/comquery/insert_table_resp.txt"));
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
        ComQueryResponse comQueryResponse = PacketBuilder.getInstance().buildComQueryResponse(byteBuf, cap);
        System.out.println(comQueryResponse.toString());
    }
}
