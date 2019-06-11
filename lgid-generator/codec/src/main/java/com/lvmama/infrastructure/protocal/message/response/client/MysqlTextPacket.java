package com.lvmama.infrastructure.protocal.message.response.client;

import com.lvmama.infrastructure.protocal.message.MySQLPackets;
import com.lvmama.infrastructure.codec.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/31 10:44
 * @Description:
 */
public class MysqlTextPacket extends MySQLPackets {
    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    private int command;


    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    private String statement;

    public MysqlTextPacket() {
        super();

    }

    @Override
    public String toString() {
        return "command:"+command+" statement:"+statement;
    }
}
