package com.lvmama.infrastructure.protocal.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/27 16:03
 * @Description:
 */
public class MySQLPackets {

    protected int sequence_id;



    public  static  enum MYSQL_TEXT_PROTOCAL {
        COM_SLEEP(0),
        COM_QUIT(1),
        COM_INIT_DB(2),
        COM_QUERY(3),
        COM_FIELD_LIST(4),
        COM_CREATE_DB(5),
        COM_DROP_DB(6),
        COM_REFRESH(7),
        COM_SHUTDOWN(8),
        COM_STATISTICS(9),
        COM_PROCESS_INFO(10),
        COM_CONNECT(11),
        COM_PROCESS_KILL(12),
        COM_DEBUG(13),
        COM_PING(14),
        COM_TIME(15),
        COM_DELAYED_INSERT(16),
        COM_CHANGE_USER(17),
        COM_BINLOG_DUMP(18),
        COM_TABLE_DUMP(19),
        COM_CONNECT_OUT(20),
        COM_REGISTER_SLAVE(21),
        COM_STMT_PREPARE(22),
        COM_STMT_EXECUTE(23),
        COM_STMT_SEND_LONG_DATA(24),
        COM_STMT_CLOSE(25),
        COM_STMT_RESET(26),
        COM_SET_OPTION(27),
        COM_STMT_FETCH(28),
        COM_HEARTBEAT(29);

        public  int code;
        MYSQL_TEXT_PROTOCAL(int code){
         this.code = code;
        }
//
//        public static MYSQL_TEXT_PROTOCAL getComm(int code){
//
//        }
    }

    public static   enum SERVER_STATUS {
        SERVER_STATUS_IN_TRANS(0x0001),
        SERVER_STATUS_AUTOCOMMIT(0x0002),
        SERVER_MORE_RESULTS_EXISTS(0x0008),
        SERVER_STATUS_NO_GOOD_INDEX_USED(0x0010),
        SERVER_STATUS_NO_INDEX_USED(0x0020),
        SERVER_STATUS_CURSOR_EXISTS(0x0040),
        SERVER_STATUS_LAST_ROW_SENT(0x0080),
        SERVER_STATUS_DB_DROPPED(0x0100),
        SERVER_STATUS_NO_BACKSLASH_ESCAPES(0x0200),
        SERVER_STATUS_METADATA_CHANGED(0x0400),
        SERVER_QUERY_WAS_SLOW(0x0800),
        SERVER_PS_OUT_PARAMS(0x1000),
        SERVER_STATUS_IN_TRANS_READONLY(0x2000),
        SERVER_SESSION_STATE_CHANGED(0x4000);
        public int code;

        SERVER_STATUS(int i) {
            this.code = i;
        }
    }


    public static enum  CAPABILITY_FLAGS_ENUMS  {
        CLIENT_LONG_PASSWORD(0x00000001),
        CLIENT_FOUND_ROWS(0x00000002),
        CLIENT_LONG_FLAG(0x00000004),
        CLIENT_CONNECT_WITH_DB(0x00000008),
        CLIENT_NO_SCHEMA(0x00000010),
        CLIENT_COMPRESS(0x00000020),
        CLIENT_ODBC(0x00000040),
        CLIENT_LOCAL_FILES(0x00000080),
        CLIENT_IGNORE_SPACE(0x00000100),
        CLIENT_PROTOCOL_41(0x00000200),
        CLIENT_INTERACTIVE(0x00000400),
        CLIENT_SSL(0x00000800),
        CLIENT_IGNORE_SIGPIPE(0x00001000),
        CLIENT_TRANSACTIONS(0x00002000),
        CLIENT_RESERVED(0x00004000),//Unused
        CLIENT_SECURE_CONNECTION(0x00008000),
        CLIENT_MULTI_STATEMENTS(0x00010000),
        CLIENT_MULTI_RESULTS(0x00020000),
        CLIENT_PS_MULTI_RESULTS(0x00040000),
        CLIENT_PLUGIN_AUTH(0x00080000),
        CLIENT_CONNECT_ATTRS(0x00100000),
        CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA(0x00200000),
        CLIENT_CAN_HANDLE_EXPIRED_PASSWORDS(0x00400000),
        CLIENT_SESSION_TRACK(0x00800000),
        CLIENT_DEPRECATE_EOF(0x01000000);

        public int code;
        CAPABILITY_FLAGS_ENUMS(int code) {
            this.code = code;

        }
    }

    protected ByteBuf mysqlPackages(ByteBuf sequence_id,ByteBuf payload){
        ByteBuf payload_length = Unpooled.buffer();

        CompositeByteBuf compositeByteBuf =   Unpooled.compositeBuffer(3);
        payload_length.writeMediumLE(payload.writerIndex() &0xffffff);
        compositeByteBuf.addComponent(true,payload_length);
        compositeByteBuf.addComponent(true,sequence_id);
        compositeByteBuf.addComponent(true,payload);
        return compositeByteBuf;
    }

    public int getSequence_id() {
        return sequence_id;
    }

    public void setSequence_id(int sequence_id) {
        this.sequence_id = sequence_id;
    }
}
