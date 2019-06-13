package com.protocal.codec.utils;

import com.lvmama.infrastructure.codec.utils.SecurityUtils;
import com.lvmama.infrastructure.protocal.message.response.client.HandshakeResponse;
import com.lvmama.infrastructure.protocal.message.response.server.ServerHandshakeV10Packet;
import io.netty.buffer.ByteBufUtil;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/13 11:39
 * @Description:
 */
public class TestSecureUtils {

    String seek1;

    String seek2;

    String pwd="admin";

    String securePwd = "f462375e580c3ad8fc5a45787381e2ff30dd25d7";

    @Test
    public void testSecurePasswordAuthentication411(){
        ServerHandshakeV10Packet h = new ServerHandshakeV10Packet();

        byte[] seeds = new byte[h.getAuthPluginData().length()+h.getAuthPluginDataPart2().length()];
        System.arraycopy(h.getAuthPluginData().getBytes(), 0, seeds, 0, h.getAuthPluginData().length());
        System.arraycopy(h.getAuthPluginDataPart2().getBytes(), 0, seeds, h.getAuthPluginData().length(), h.getAuthPluginDataPart2().length());
        try {
            byte[] bs =  SecurityUtils.securePasswordAuthentication411(pwd.getBytes(),seeds);
//            System.out.println(new String(bs,"UTF-8"));
            Assert.assertTrue(ByteBufUtil.hexDump(bs).equals(securePwd));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
