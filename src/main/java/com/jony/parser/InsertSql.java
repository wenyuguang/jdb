package com.jony.parser;

import java.util.List;

public class InsertSql implements Sql {
    @Override
    public StatementType getType() {
        return StatementType.INSERT;
    }

    String tableName;
    List<String> attributes;
    List<List<String>> values;
    SelectSql selectStatement;

    public InsertSql(String tableName, List<String> attributes, List<List<String>> values) {
        this.tableName = tableName;
        this.attributes = attributes;
        this.values = values;
    }

    public InsertSql(String tableName, List<String> attributes, SelectSql selectStatement) {
        this.tableName = tableName;
        this.attributes = attributes;
        this.selectStatement = selectStatement;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public List<List<String>> getValues() {
        return values;
    }

    public void setValues(List<List<String>> values) {
        this.values = values;
    }

    public SelectSql getSelectStatement() {
        return selectStatement;
    }

    public void setSelectStatement(SelectSql selectStatement) {
        this.selectStatement = selectStatement;
    }

    @Override
    public String toString() {
        return "Insert Statement :" + "\r\n" +
                "tableName = '" + tableName + '\'' + "\r\n" +
                "attributes = " + attributes + "\r\n" +
                "values = " + values + "\r\n" +
                "selectStatement = " + selectStatement + "\r\n";
    }
}
