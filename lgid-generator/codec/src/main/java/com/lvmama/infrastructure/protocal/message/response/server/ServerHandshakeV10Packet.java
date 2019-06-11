package com.lvmama.infrastructure.protocal.message.response.server;

import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import com.lvmama.infrastructure.codec.utils.ByteBufUtils;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/22 16:08
 * @Description: client------connect()------>server
 *               client<-----handshake()---server
 *               client-----handshake response Package()--->server
 *               client<-----ok package()---server
 * @see
 * @Document
 * https://dev.mysql.com/doc/internals/en/connection-phase-packets.html#packet-Protocol::Handshake
 */
public class ServerHandshakeV10Packet extends MySQLPackets {


    public byte getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(byte protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getAuthPluginData() {
        return authPluginData;
    }

    public void setAuthPluginData(String authPluginData) {
        this.authPluginData = authPluginData;
    }

    public String getAuthPluginDataPart2() {
        return authPluginDataPart2;
    }

    public void setAuthPluginDataPart2(String authPluginDataPart2) {
        this.authPluginDataPart2 = authPluginDataPart2;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public byte[] getSalt1() {
        return salt1;
    }

    public void setSalt1(byte[] salt1) {
        this.salt1 = salt1;
    }

    public byte getFiller1() {
        return filler1;
    }

    public void setFiller1(byte filler1) {
        this.filler1 = filler1;
    }

    public byte getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(byte characterSet) {
        this.characterSet = characterSet;
    }

    public byte[] getStatusFlags() {
        return statusFlags;
    }

    public void setStatusFlags(byte[] statusFlags) {
        this.statusFlags = statusFlags;
    }

    public byte[] getCapabilityFlags2() {
        return capabilityFlags2;
    }

    public void setCapabilityFlags2(byte[] capabilityFlags2) {
        this.capabilityFlags2 = capabilityFlags2;
    }

    public String getAuthPluginName() {
        return authPluginName;
    }

    public void setAuthPluginName(String authPluginName) {
        this.authPluginName = authPluginName;
    }

    public int getAuthPluginDataLen() {
        return authPluginDataLen;
    }

    public void setAuthPluginDataLen(int authPluginDataLen) {
        this.authPluginDataLen = authPluginDataLen;
    }

    //1byte
    private byte protocolVersion =10;
//    serverVersion (string.NUL) -- human-readable server version
    private String serverVersion = "5.6.25";
    private String authPluginData ="GyQe,IhP";
    String authPluginDataPart2 =":m;{zsS&^,[i";
//    = "5.6.25";
    //4byte connection_id (4) -- connection id
    private  int threadId =120;
    //8byte 先给个空的
    private byte[] salt1 =new byte[8];
    //1byte
    private byte filler1 =0;

    public byte[] getCapabilityFlag1() {
        return capabilityFlag1;
    }

    public void setCapabilityFlag1(byte[] capabilityFlag1) {
        this.capabilityFlag1 = capabilityFlag1;
    }

    //2byte -- lower 2 bytes of the Protocol::CapabilityFlags (optional)
    //bit 表示 {1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1}
    private  byte[] capabilityFlag1 = {(byte)255,(byte)247};
    //1byte utf8_general_ci 写死
    private byte characterSet =8;

    //2byte statusFlags  optional
    //@see https://dev.mysql.com/doc/internals/en/status-flags.html#packet-Protocol::StatusFlags
    //bit 表示 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0};
    private byte[] statusFlags = {(byte)0,(byte)2};

    //2byte upper 2 bytes of the Protocol::CapabilityFlags
    //https://dev.mysql.com/doc/internals/en/capability-flags.html#packet-Protocol::CapabilityFlags
    //bit标示 10000000 01111111
    private byte[] capabilityFlags2 = {(byte) 127,(byte)128};


    //private byte[] salt2;
    // authPluginName (string.NUL) -- name of the auth_method that the authPluginData belongs to
    private String authPluginName = "mysql_native_password";

    //1byte
    private int authPluginDataLen = authPluginName.length() & 0xff;
    /**
     * 这些新需要backend 的master mysql 获取并返回
     * 这里先写死 方便测试
     * @return
     */

    public int getCapabilityFlags(){
       return ByteBufUtils.B2ToInt(getCapabilityFlag1());
    }

}
