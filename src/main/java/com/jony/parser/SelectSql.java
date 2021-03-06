package com.jony.parser;

import java.util.List;

public class SelectSql implements Sql {


    @Override
    public StatementType getType() {
        return StatementType.SELECT;
    }

    boolean hasDistinct = false;
    List<String> columns;
    List<String> tables;
    List<List<Expression>> condition; //内部合取，外部析取
    String orderColumn;

    public SelectSql(List<String> columns, List<String> tables) {
        this.columns = columns;
        this.tables = tables;
    }

    public SelectSql(Object distinct, List<String> columns, List<String> tables, List<List<Expression>> condition,
                     String orderColumn) {
        this.hasDistinct = distinct != null;
        this.columns = columns;
        this.tables = tables;
        this.condition = condition;
        if (!orderColumn.isEmpty()) {
            this.orderColumn = orderColumn;
        }
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public boolean isHasDistinct() {
        return hasDistinct;
    }

    public void setHasDistinct(boolean hasDistinct) {
        this.hasDistinct = hasDistinct;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public List<List<Expression>> getCondition() {
        return condition;
    }

    public void setCondition(List<List<Expression>> condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Select Statement :" + "\r\n" +
                "hasDistinct = " + hasDistinct + "\r\n" +
                "columns = " + columns + "\r\n" +
                "tables = " + tables + "\r\n" +
                "condition = " + condition + "\r\n" +
                "orderColumn = '" + orderColumn + '\'' + "\r\n";
    }
}
