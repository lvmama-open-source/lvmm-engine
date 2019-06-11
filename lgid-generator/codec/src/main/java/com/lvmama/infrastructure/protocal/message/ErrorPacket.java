package com.lvmama.infrastructure.protocal.message;

import io.netty.buffer.ByteBuf;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/22 15:11
 * @Description:
 * https://dev.mysql.com/doc/internals/en/packet-ERR_Packet.html
 */
public class ErrorPacket  extends MySQLPackets{
    int header;
    int errorCode;
    String sqlStateMarker;
    String sqlState;
    String errorMessage;

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getSqlStateMarker() {
        return sqlStateMarker;
    }

    public void setSqlStateMarker(String sqlStateMarker) {
        this.sqlStateMarker = sqlStateMarker;
    }

    public String getSqlState() {
        return sqlState;
    }

    public void setSqlState(String sqlState) {
        this.sqlState = sqlState;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

//    ByteBuf
//    ByteBuf b = new Abst
}
