package com.protocal.codec.handshake;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.StringUtil;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/4 11:12
 * @Description:
 */
public class BaseTest {

    private String getClassPath() {
        URL url =  this.getClass().getResource("/");
//        System.out.println(url.getPath());
        return url.getPath();
    }

    public String prettyStringReader(String path) {
        BufferedReader br = null;
        StringBuffer buffer = new StringBuffer();
        StringBuffer returnBuf = new StringBuffer();
        try {
            br = new BufferedReader(new FileReader(path));
            String line;

            while ((line = br.readLine()) != null) {
//                System.out.println(line);
                //line = line;
                if (line==null || line.equals("") ||line.equals("\r\n")) {
                    continue;
                }
                String tmpLine = line.substring(7,line.length());
//                System.out.println(tmpLine);
                buffer.append(tmpLine);
            }

//            String tempString =
            String tmpStr = buffer.toString();

            for (int i =0;i<tmpStr.length();i++) {
                char c = tmpStr.charAt(i);
                if (c!='\n' && c !='\r' && c!='\0' && c!=' ') {
                    returnBuf.append(c);
                }
            }

        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Assert.assertNotNull(br);
            try {
                br.close();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return returnBuf.toString();
    }

    public byte[] StringToBytes(String path) {
        try {
            String absPath = getClassPath()+path;

            String str = this.prettyStringReader(absPath);

            byte[] bytes =  this.hexrawStreamToBytes(str);

            return bytes;
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }


    public byte[] hexrawStreamToBytes(String buffer){
        int bytelenght = buffer.length();
        byte[] bytes = new byte[buffer.length()/2];

        String hex = buffer.toString();
//        System.out.println("lenght:"+hex.length());
        int index = 0;
        for (int i =0;i<hex.length();i+=2) {
//            System.out.println("pos:"+i);
            char p1 = hex.charAt(i);
            char p2=hex.charAt(i+1);
            String byteHex = p1+""+p2;
            //按16进制转换
            byte b = (byte) Integer.parseInt(byteHex,16);
            bytes[index]=b;
            index++;
        }
        return bytes;
    }


}
