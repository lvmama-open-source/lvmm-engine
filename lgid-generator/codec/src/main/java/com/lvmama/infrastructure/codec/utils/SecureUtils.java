package com.lvmama.infrastructure.codec.utils;


import sun.security.provider.SHA;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.Collections;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/10 17:05
 * @Description:
 */
public class SecureUtils {

    public byte[] securePasswordAuthentication411 (byte[] payload,byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        byte[] pass1 = md.digest(payload);
        md.reset();
        byte[] pass2 = md.digest(pass1);
        md.reset();
        md.update(salt);
        byte[] pass3 = md.digest(pass2);
        for (int i = 0; i < pass3.length; i++) {
            pass3[i] = (byte) (pass3[i] ^ pass1[i]);
        }
        return pass3;
    }

}
