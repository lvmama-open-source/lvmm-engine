package com.lvmama.infrastructure.bootstrap.handler;

import com.lvmama.infrastructure.bootstrap.LgidBootstrap;
import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/29 16:48
 * @Description:
 */
public class MySQLSession {
    private Channel networkChannel;
    private int clientFlags;
    private int autoCommit;

//  private boolean isReadOnly;
    /**
     * 对应后端mysql服务
     */
    private Object backendConnections;

    public boolean isAuthed() {
        return authed;
    }

    public void setAuthed(boolean authed) {
        this.authed = authed;
    }

    private boolean authed;

    Channel channel;

    private String userName;
//    private ServerHandshakeV10Packet serverHandshakeV10Packet;

    MySQLSession(Channel networkChannel){
        this.networkChannel = networkChannel;
    }

    public Channel getNetworkChannel() {
        return networkChannel;
    }

    public void setNetworkChannel(Channel networkChannel) {
        this.networkChannel = networkChannel;
    }

    public int getClientFlags() {
        return clientFlags;
    }

    public void setClientFlags(int clientFlags) {
        this.clientFlags = clientFlags;
    }

    public int getAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(int autoCommit) {
        this.autoCommit = autoCommit;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public int getCapabilityFlags() {
        return capabilityFlags;
    }

    public void setCapabilityFlags(int capabilityFlags) {
        this.capabilityFlags = capabilityFlags;
    }

    private int capabilityFlags;


    public boolean isSupportsProtocal41(){
        return (capabilityFlags& MySQLPackets.CAPABILITY_FLAGS_ENUMS.CLIENT_PROTOCOL_41.code)>0;
    }


    /**
     * 干掉前端session
     */
    public  void close(){
        if (channel!=null){
            ChannelFuture future = channel.close();
            future.addListener((ChannelFutureListener)->{
                LgidBootstrap.sessions.remove(channel);
            });
        }
    }

}
