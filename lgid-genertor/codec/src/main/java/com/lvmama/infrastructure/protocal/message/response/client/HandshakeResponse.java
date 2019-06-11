package com.lvmama.infrastructure.protocal.message.response.client;

import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import io.netty.buffer.ByteBuf;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/27 11:42
 * @Description:
 * @Doc https://dev.mysql.com/doc/internals/en/connection-phase-packets.html#packet-Protocol::HandshakeResponse320
 *
 */
public class HandshakeResponse extends MySQLPackets {


    public int getCapabilityFlags() {
        return capabilityFlags;
    }



    private int maxPacketSize;
    private int characterSet;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    /**
     * 算法：https://dev.mysql.com/doc/internals/en/secure-password-authentication.html
     * SHA1( password ) XOR SHA1( "20-bytes random data from server" <concat> SHA1( SHA1( password ) ) )
     */
    private String password;
    //    private int characterSet;
    private byte[] auth_response;

    public void setDatabase(String database) {
        this.database = database;
    }

    private String database;

    public String getDatabase() {
        return database;
    }

    public void setAuthPluginName(String authPluginName) {
        this.authPluginName = authPluginName;
    }

    private String authPluginName;

    public byte[] getConnectionAttr() {
        return connectionAttr;
    }

    public void setConnectionAttr(byte[] connectionAttr) {
        this.connectionAttr = connectionAttr;
    }

    private byte[] connectionAttr;

    public HandshakeResponse(ByteBuf in) {
//        super(in);
    }


    public void setCapabilityFlags(int capabilityFlags) {
        this.capabilityFlags = capabilityFlags;
    }

    private int capabilityFlags;


    public int getMaxPacketSize() {
        return maxPacketSize;
    }

    public void setMaxPacketSize(int maxPacketSize) {
        this.maxPacketSize = maxPacketSize;
    }

    public int getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(int characterSet) {
        this.characterSet = characterSet;
    }


    public byte[] getAuth_response() {
        return auth_response;
    }

    public void setAuth_response(byte[] auth_response) {
        this.auth_response = auth_response;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void printClientCapability(){

    }

    public boolean isClientProtocal41(){
        System.out.println("xxxxxxxx:"+(capabilityFlags &MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_LONG_PASSWORD.code));

        return (capabilityFlags &MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_PROTOCOL_41.code)>0;
    }

    public boolean isLongPwdSet(){
        return (capabilityFlags &MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_LONG_PASSWORD.code)>0;
    }


    public boolean isHasConnAttr(){
        return (capabilityFlags & CAPABILITY_FLAGS_ENUMS.CLIENT_CONNECT_ATTRS.code)>0;

    }

    public boolean isExsitClientPluginAuth(){
        return (capabilityFlags & CAPABILITY_FLAGS_ENUMS.CLIENT_PLUGIN_AUTH.code)>0;
    }

    public boolean isNeedAuth(){
        return (capabilityFlags & CAPABILITY_FLAGS_ENUMS.CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA.code)>0;
    }

    public boolean isSSLConnection(){
        return (capabilityFlags & CAPABILITY_FLAGS_ENUMS.CLIENT_SECURE_CONNECTION.code)>0;
    }

    public  boolean isWithDb(){
        return (capabilityFlags & CAPABILITY_FLAGS_ENUMS.CLIENT_CONNECT_WITH_DB.code)>0;
    }

    @Override
    public String toString() {
        return  "seq_id:"+getSequence_id()+" maxPacketSize: "
                + maxPacketSize + " characterSet:" + characterSet +" user_name:"+username +" isClientProtocal41:"+isClientProtocal41()
                +" CLIENT_LONG_PASSWORD:"+isLongPwdSet();
    }
}
