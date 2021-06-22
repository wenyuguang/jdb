package com.jony.parser;

public interface Sql {

    enum StatementType {
        CREATE,
        DROP,
        SELECT,
        INSERT,
        DELETE,
        UPDATE
    }

    StatementType getType();
}

