package com.lvmama.infrastructure.protocal.message.response.client;

import com.lvmama.infrastructure.protocal.message.EOFPacket;
import com.lvmama.infrastructure.protocal.message.ErrorPacket;
import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import com.lvmama.infrastructure.protocal.message.OKPacket;

import java.util.List;

public class ComQueryResponse {

    private ComQueryResponseHeaderPacket comQueryResponseHeaderPacket;

    private List<ColumnDefinitionPacket> columnDefinitionPacketList;

    private List<EOFPacket> eofPacketList;

    private List<ResultsetRowPacket> resultsetRowPacketList;

    private OKPacket okPacket;

    private ErrorPacket errorPacket;

    public ComQueryResponseHeaderPacket getComQueryResponseHeaderPacket() {
        return comQueryResponseHeaderPacket;
    }

    public void setComQueryResponseHeaderPacket(ComQueryResponseHeaderPacket comQueryResponseHeaderPacket) {
        this.comQueryResponseHeaderPacket = comQueryResponseHeaderPacket;
    }

    public List<EOFPacket> getEofPacketList() {
        return eofPacketList;
    }

    public void setEofPacketList(List<EOFPacket> eofPacketList) {
        this.eofPacketList = eofPacketList;
    }

    public List<ResultsetRowPacket> getResultsetRowPacketList() {
        return resultsetRowPacketList;
    }

    public void setResultsetRowPacketList(List<ResultsetRowPacket> resultsetRowPacketList) {
        this.resultsetRowPacketList = resultsetRowPacketList;
    }

    public List<ColumnDefinitionPacket> getColumnDefinitionPacketList() {
        return columnDefinitionPacketList;
    }

    public void setColumnDefinitionPacketList(List<ColumnDefinitionPacket> columnDefinitionPacketList) {
        this.columnDefinitionPacketList = columnDefinitionPacketList;
    }

    public OKPacket getOkPacket() {
        return okPacket;
    }

    public void setOkPacket(OKPacket okPacket) {
        this.okPacket = okPacket;
    }

    public ErrorPacket getErrorPacket() {
        return errorPacket;
    }

    public void setErrorPacket(ErrorPacket errorPacket) {
        this.errorPacket = errorPacket;
    }
}
