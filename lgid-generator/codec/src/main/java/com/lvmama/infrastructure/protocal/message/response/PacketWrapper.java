package com.lvmama.infrastructure.protocal.message.response;

import com.lvmama.infrastructure.protocal.message.MySQLPackets;

/**
 * @Auther: dengcheng
 * @Date: 2019/6/3 17:32
 * @Description:
 */
public class PacketWrapper<M extends MySQLPackets> {


    int payloadLength;
    int sequenceId;
    M packets;
    public PacketWrapper(){

    }
    public PacketWrapper(M packets){
        this.packets =  packets;
    }

    public int getPayloadLength() {
        return payloadLength;
    }

    public void setPayloadLength(int payloadLength) {
        this.payloadLength = payloadLength;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public M getPackets() {
        return packets;
    }

    public void setPackets(M packets) {
        this.packets = packets;
    }

}
