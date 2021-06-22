package com.jony.parser;

import java.util.LinkedHashMap;
import java.util.List;

public class UpdateSql implements Sql {
    @Override
    public StatementType getType() {
        return Sql.StatementType.UPDATE;
    }

    private String tableName;
    private LinkedHashMap set;
    private List<List<Expression>> condition;

    public UpdateSql(String tableName, LinkedHashMap set, List<List<Expression>> condition) {
        this.tableName = tableName;
        this.set = set;
        this.condition = condition;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public LinkedHashMap getSet() {
        return set;
    }

    public void setSet(LinkedHashMap set) {
        this.set = set;
    }

    public List<List<Expression>> getCondition() {
        return condition;
    }

    public void setCondition(List<List<Expression>> condition) {
        this.condition = condition;
    }
}
