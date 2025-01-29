package com.backend;

import com.backend.services.*;
import io.javalin.config.Key;

import java.sql.Connection;

public enum Keys {
    SQL_DB(new Key<Connection>("sql_db")),
    USUARIO_SERVICE(new Key<UsuarioService>("usuario-service")),
    AMIZADE_SERVICE(new Key<AmizadeService>("amizade-service")),
    POSTAGEM_SERVICE(new Key<PostagemService>("postagem-service")),
    CURTIDA_SERVICE(new Key<CurtidaService>("curtida-service")),
    NOTIFICATION_SERVICE(new Key<NotificationService>("notification-service"));


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
