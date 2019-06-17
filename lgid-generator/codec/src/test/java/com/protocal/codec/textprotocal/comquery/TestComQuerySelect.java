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
import org.springframework.util.CollectionUtils;

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
        Assert.assertTrue(packets.getPackets().getStatement() != null);
    }

    @Test
    public void testComQueryRep(){
        int cap = 0xffffc3ff;
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/comquery/com_query_select_rep.txt"));
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
        ComQueryResponse comQueryResponse = PacketBuilder.getInstance().buildSelectResponse(byteBuf, cap);
        Assert.assertTrue(!CollectionUtils.isEmpty(comQueryResponse.getResultsetRowPacketList()));
    }


    /**
     * select * from employee
     */
    @Test
    public void testRepSelectFromEmploy(){
        int cap = 0xffffc3ff;
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/comquery/rep_select_from_employee.txt"));
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
        ComQueryResponse comQueryResponse = PacketBuilder.getInstance().buildSelectResponse(byteBuf, cap);
        Assert.assertTrue(!CollectionUtils.isEmpty(comQueryResponse.getResultsetRowPacketList()));
    }

    /**
     * select id from employee
     */
    @Test
    public void testRepSelectIdFromEmploy(){
        int cap = 0xffffc3ff;
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/comquery/rep_select_id_from_employee.txt"));
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
        ComQueryResponse comQueryResponse = PacketBuilder.getInstance().buildSelectResponse(byteBuf, cap);
        Assert.assertTrue(!CollectionUtils.isEmpty(comQueryResponse.getResultsetRowPacketList()));
    }

    /**
     * select sharding_id from employee
     */
    @Test
    public void testRepSelectShardingIdFromEmploy(){
        int cap = 0xffffc3ff;
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(this.StringToBytes("mysql5.6.x/textprotocal/comquery/rep_select_shardingid_from_employee.txt"));
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
        ComQueryResponse comQueryResponse = PacketBuilder.getInstance().buildSelectResponse(byteBuf, cap);
        Assert.assertTrue(!CollectionUtils.isEmpty(comQueryResponse.getResultsetRowPacketList()));
    }
}
