package com.jony.parser;

public class DropSql implements Sql {
    @Override
    public StatementType getType() {
        return StatementType.DROP;
    }

    String tableName;

    public DropSql(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "Drop Statement :" + "\r\n" +
                "tableName = '" + tableName + '\'' + "\r\n" ;
    }
}
