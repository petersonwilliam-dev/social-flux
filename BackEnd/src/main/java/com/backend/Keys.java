package com.backend;

import io.javalin.config.Key;

import java.sql.Connection;

public enum Keys {
    MONGO_DB(new Key<MongoDBRepository>("mongo_db")),
    SQL_DB(new Key<Connection>("sql_db"));


    private final Key<?> k;

    <T> Keys(Key<T> key) {
        this.k = key;
    }

    public <T> Key<T> key() {
        @SuppressWarnings("unchecked")
        Key<T> typedKey = (Key<T>) this.k;
        return typedKey;
    }
}
