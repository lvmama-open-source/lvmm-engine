package com.lvmama.infrastructure.protocal.message;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/22 15:16
 * @Description:
 */
public class EOFPacket extends MySQLPackets{
    int header;
    int warnings;
    int statusFlags;

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public int getWarnings() {
        return warnings;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }

    public int getStatusFlags() {
        return statusFlags;
    }

    public void setStatusFlags(int statusFlags) {
        this.statusFlags = statusFlags;
    }
}
