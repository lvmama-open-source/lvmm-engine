package com.lvmama.infrastructure.protocal.message;

import com.lvmama.infrastructure.codec.utils.ByteBufUtils;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/22 15:10
 * @Description:
 * https://dev.mysql.com/doc/internals/en/packet-OK_Packet.html
 */
public class OKPacket extends MySQLPackets {





    private int header=0x00;
    private long affectedRows;
    private long lastInsertId;

    public int getStatusFlags() {
        return statusFlags;
    }

    public void setStatusFlags(int statusFlags) {
        this.statusFlags = statusFlags;
    }

    private int statusFlags;
    

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public long getAffectedRows() {
        return affectedRows;
    }

    public void setAffectedRows(long affectedRows) {
        this.affectedRows = affectedRows;
    }

    public long getLastInsertId() {
        return lastInsertId;
    }

    public void setLastInsertId(long lastInsertId) {
        this.lastInsertId = lastInsertId;
    }

    public int getWarnings() {
        return warnings;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSessionStateChanges() {
        return sessionStateChanges;
    }

    public void setSessionStateChanges(String sessionStateChanges) {
        this.sessionStateChanges = sessionStateChanges;
    }

    private int warnings;
    private String info;
    private String sessionStateChanges;


    public OKPacket(int sequenceId,int serverStatus){
        this.sequence_id= sequenceId;
        this.statusFlags = ByteBufUtils.saftyIntToB2(serverStatus);
    }


//    public ByteBuf writeForProtocal41(int seqId,int server_status){
//        ByteBuf payload = Unpooled.buffer();
//        payload.writeByte(header);
//        payload.writeByte(0);//affectedRows
//        payload.writeByte(0);//lastinsert_id
//
//        payload.writeShortLE(server_status);
//        payload.writeShortLE(0);
//        ByteBuf sequence_id = Unpooled.buffer();
//        sequence_id.writeByte(ByteBufUtils.saftyIntToB1(seqId));
//        return mysqlPackages(sequence_id,payload);
//
//    }


//    /**
//     *
//     * @param affectRow
//     * @param lastInsertId
//     * @return
//     */
//    public ByteBuf writeForProtocal41(long affectRow,long lastInsertId){
//        ByteBuf payload = Unpooled.buffer();
//        payload.writeByte(header);
//        affectedRows = new byte[]{0x00};
//        lastInsertId = new byte[]{0x00};
//
//        payload.writeBytes(affectedRows);
//        payload.writeBytes(lastInsertId);
//        payload.writeShortLE(SERVER_STATUS.SERVER_STATUS_AUTOCOMMIT.code);
//        payload.writeShort(0);
//
//        ByteBuf sequence_id = Unpooled.buffer();
//        sequence_id.writeByte(0x00);
//        return mysqlPackages(sequence_id,payload);
//
//    }


    public OKPacket(){

    }

}

