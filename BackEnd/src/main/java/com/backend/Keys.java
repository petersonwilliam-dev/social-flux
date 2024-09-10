package com.backend;

import com.backend.database.MongoDBRepository;
import io.javalin.config.Key;

public enum Keys {
    MONGO_DB(new Key<MongoDBRepository>("mongo_db"));

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
