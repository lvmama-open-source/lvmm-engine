package com.lvmama.infrastructure.protocal.message.response.client;

import com.lvmama.infrastructure.protocal.message.MySQLPackets;

import java.util.List;
import java.util.Map;

public class ResultsetRowPacket extends MySQLPackets {
    Map<String, Object> result;

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
