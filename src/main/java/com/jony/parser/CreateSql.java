package com.jony.parser;

import java.util.LinkedHashMap;

public class CreateSql implements Sql {
    @Override
    public StatementType getType() {
        return StatementType.CREATE;
    }

    String tableName;
    // LinkedHashMap of Attribute name and Data StatementType
    LinkedHashMap<String,String> attributes = new LinkedHashMap<>();

    public CreateSql(String tableName, LinkedHashMap<String, String> attributes) {
        this.tableName = tableName;
        this.attributes = attributes;
    }

    public CreateSql(String tableName) {
        this.tableName = tableName;
    }

    public CreateSql() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public LinkedHashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(LinkedHashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Create Sql :" + "\r\n" +
                "tableName = '" + tableName + '\'' + "\r\n" +
                "attributes = " + attributes + "\r\n" ;
    }
}
