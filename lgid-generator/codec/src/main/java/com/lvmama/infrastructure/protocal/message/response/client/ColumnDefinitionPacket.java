package com.lvmama.infrastructure.protocal.message.response.client;

import com.lvmama.infrastructure.protocal.message.MySQLPackets;

public class ColumnDefinitionPacket extends MySQLPackets {

    public static enum TABLE_COLUMN_TYPE{
        MYSQL_TYPE_TINY(0x01),
        MYSQL_TYPE_LONG(0x03),
        MYSQL_TYPE_VAR_STRING(0xfd),

        ;


        public int code;
        TABLE_COLUMN_TYPE(int code) {
            this.code = code;

        }
    }

    private String catalog = "def";

    private String schemal;

    private String table;

    private String orgTable;

    private String name;

    private String orgName;

    private byte nextLength;

    private int characterSet;

    private int columnLength;

    private int columnType;

    private int flags;

    private byte decimals;

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchemal() {
        return schemal;
    }

    public void setSchemal(String schemal) {
        this.schemal = schemal;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getOrgTable() {
        return orgTable;
    }

    public void setOrgTable(String orgTable) {
        this.orgTable = orgTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public byte getNextLength() {
        return nextLength;
    }

    public void setNextLength(byte nextLength) {
        this.nextLength = nextLength;
    }

    public int getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(int characterSet) {
        this.characterSet = characterSet;
    }

    public int getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(int columnLength) {
        this.columnLength = columnLength;
    }

    public int getColumnType() {
        return columnType;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public byte getDecimals() {
        return decimals;
    }

    public void setDecimals(byte decimals) {
        this.decimals = decimals;
    }
}
