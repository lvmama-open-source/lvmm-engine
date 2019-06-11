package com.lvmama.infrastructure.codec.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;


/**
 * @Auther: dengcheng
 * @Date: 2019/5/23 12:24
 * @Description:
 */
public class ByteBufUtils {
    private static Logger log = Logger.getLogger(ByteBufUtils.class);

    private static final byte EMPTY_BYTES = 0;


    /**
     *
     * @param buf
     * @param src
     */
    public static void writeL4B (ByteBuf buf ,Long src){
        Long value = src&0xffffffff;
        buf.writeInt(value.intValue());
    }

    /**
     * 从ByteBuf 读取length 字节的字符串
     * @param buf
     * @param length
     * @return
     */
    public static String readFixedString(ByteBuf buf,int length){
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        return new String(bytes, CharsetUtil.UTF_8);
    }

    /**
     * 将String str写入到 ByteBuf
     * tail 追加0x00
     * @param buf
     * @param src
     */
    public static void writeLengthNull(ByteBuf buf ,String src){
        buf.writeBytes(src.getBytes());
        buf.writeByte((byte)0);
    }


    /**
     * 安全转化一个字节转int，不溢出
     * @param b
     * @return
     */
    public  static  int B1ToInt(byte b){
        return (int)(b &0xff);
    }

    /**
     * 2个字节转int
     * @param bytes
     * @return
     */
    public static  int B2ToInt(byte[] bytes){
        return (int) ((bytes[0]&0xff) | ((bytes[1]&0xff)<<8));

    }

    /**
     * 三个字节转int
     * @param bytes
     * @return
     */
    public static  int B3ToInt(byte[] bytes){
        return (int) ((bytes[0]&0xff) | ((bytes[1]&0xff)<<8) |  ((bytes[1]&0xff)<<16));

    }


    /** 小端法二进制转十进制
     * 2byte
     * @param bytes
     * @return
     */
    public static  int LittleB2ToInt(byte[] bytes){
        return (int) ((bytes[1]&0xff) | ((bytes[0]&0xff)<<8));
    }

    /**
     * 4byte 小端法二进制转十进制
     * @param bytes
     * @return
     */
    public static  int LittleB4ToInt(byte[] bytes){
        return (int) ((bytes[3]&0xff) | ((bytes[2]&0xff)<<8) | ((bytes[1]&0xff)<<16) | ((bytes[0]&0xff)<<24));

    }


    /**
     * 读取 Mysql int<lenenc>类型的数据
     *  To convert a length-encoded integer into its numeric value, check the first byte:
     *
     *     If it is < 0xfb, treat it as a 1-byte integer.
     *
     *     If it is 0xfc, it is followed by a 2-byte integer.
     *
     *     If it is 0xfd, it is followed by a 3-byte integer.
     *
     *     If it is 0xfe, it is followed by a 8-byte integer.
     * @doc https://dev.mysql.com/doc/internals/en/integer.html#packet-Protocol::LengthEncodedInteger
     * @param buf
     * @return
     */
    public  static long lengthEncodedInteger(ByteBuf buf){
        byte numberic = buf.readByte();
        if (numberic<0xfb) {
            return (int)numberic;
        } else if(numberic==0xfc){
            return buf.readUnsignedShort();
        } else if(numberic==0xfd){
            return buf.readMediumLE();
        } else if(numberic==0xfe){
            return buf.readLong();
        }
        return 0;
    }

    /**
     * 读取Mysql 类型 String<lenenc>的数据
     * @param buf
     * @return
     */
    public  static String lengthEncoded2String(ByteBuf buf){
//        byte length = buf.readByte();
        Long length =  lengthEncodedInteger(buf);
        //length.intValue()
        byte[] remains = new byte[length.intValue()];
        buf.readBytes(remains);
        return new String(remains, CharsetUtil.UTF_8);
    }

    public  static byte[] lengthEncoded2B(ByteBuf buf){
        byte length = buf.readByte();
        byte[] remains = new byte[length];
        buf.readBytes(remains);
        return remains;
    }


    public static  String nullToString(ByteBuf byteBuf){
        int index = byteBuf.forEachByte(ByteProcessor.FIND_NUL);
        byte[] bytes = new byte[index-byteBuf.readerIndex()];
        byteBuf.readBytes(bytes);
        byteBuf.skipBytes(1);
        return new String(bytes);
    }

    public static String readEOFString(ByteBuf byteBuf){
        byte[] eofs = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(eofs);
        return new String(eofs, CharsetUtil.UTF_8);
    }


    public static int saftyIntToB1(int i){
        return i&0xff;
    }

    public static int saftyIntToB2(int i){
        return i&0xffff;
    }

    public static int saftyIntToB4(int i){
        return i&0xffffffff;
    }

}
