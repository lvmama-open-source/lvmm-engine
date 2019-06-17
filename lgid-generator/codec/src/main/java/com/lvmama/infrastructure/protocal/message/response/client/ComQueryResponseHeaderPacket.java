package com.lvmama.infrastructure.protocal.message.response.client;

import com.lvmama.infrastructure.protocal.message.MySQLPackets;

public class ComQueryResponseHeaderPacket extends MySQLPackets {

    private int fieldCount;


    public int getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }
}
