package com.backend.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class SQLDBRepository {

    private static final Logger logger = LogManager.getLogger();
    private static Connection connection;

    public static Connection getConnection(String url, String username, String password) {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, username, password);

                String sqlCreateTableUsuario = "CREATE TABLE IF NOT EXISTS usuario (" +
                        "id int NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                        "username varchar(255) NOT NULL UNIQUE, " +
                        "nome varchar(255) NOT NULL, " +
                        "email varchar(255) NOT NULL UNIQUE, " +
                        "senha varchar(255) NOT NULL, " +
                        "telefone varchar(15) NOT NULL, " +
                        "foto_perfil varchar(255), " +
                        "data_nascimento date NOT NULL, " +
                        "data_criacao date NOT NULL, " +
                        "privado boolean NOT NULL, " +
                        "sexo enum('M','F') NOT NULL, " +
                        "biografia varchar(1000)" +
                        ");";

                String sqlCreateTableRelacao = "CREATE TABLE IF NOT EXISTS relacao (" +
                        "id int NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                        "aceito tinyint(1) NOT NULL, " +
                        "id_seguidor int NOT NULL, " +
                        "id_seguido int NOT NULL, " +
                        "CONSTRAINT fk_id_seguidor FOREIGN KEY (id_seguidor) REFERENCES usuario (id) ON DELETE CASCADE, " +
                        "CONSTRAINT fk_id_seguido FOREIGN KEY (id_seguido) REFERENCES usuario (id) ON DELETE CASCADE" +
                        ");";

                String sqlCreateTablePostagem = "CREATE TABLE IF NOT EXISTS postagem (" +
                        "id int NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                        "imagem varchar(255) UNIQUE, " +
                        "legenda varchar(2000), " +
                        "data_postagem datetime NOT NULL, " +
                        "id_usuario int NOT NULL, " +
                        "id_postagem int, " +
                        "CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON DELETE CASCADE, " +
                        "CONSTRAINT fk_id_postagem FOREIGN KEY (id_postagem) REFERENCES postagem (id) ON DELETE CASCADE" +
                        ");";

                String sqlCreateTableCurtida = "CREATE TABLE IF NOT EXISTS curtida (" +
                        "id int NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                        "id_usuario int NOT NULL, " +
                        "id_conteudo int NOT NULL, " +
                        "CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON DELETE CASCADE, " +
                        "CONSTRAINT fk_id_conteudo FOREIGN KEY (id_conteudo) REFERENCES postagem (id) ON DELETE CASCADE" +
                        ");";

                String sqlCreateTableNotification = "CREATE TABLE IF NOT EXISTS notification (" +
                        "id int NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                        "title varchar(255) NOT NULL, " +
                        "description varchar(255) NOT NULL, " +
                        "viewed boolean NOT NULL," +
                        "id_category int NOT NULL, " +
                        "id_sender int NOT NULL," +
                        "id_receiver int," +
                        "id_post int," +
                        "CONSTRAINT fk_id_sender FOREIGN KEY (id_sender) REFERENCES usuario (id) ON DELETE CASCADE, " +
                        "CONSTRAINT fk_id_receiver FOREIGN KEY (id_receiver) REFERENCES usuario (id) ON DELETE SET NULL, " +
                        "CONSTRAINT fk_id_post FOREIGN KEY (id_post) REFERENCES postagem (id) ON DELETE SET NULL " +
                        ");";

                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlCreateTableUsuario);
                        stmt.execute(sqlCreateTableRelacao);
                        stmt.execute(sqlCreateTablePostagem);
                        stmt.execute(sqlCreateTableCurtida);
                        stmt.execute(sqlCreateTableNotification);
                        logger.info("Tabelas criadas com sucesso");
                        stmt.close();
                        
                        return connection;
            } catch (SQLException exception) {
                logger.error(exception);
                return null;
            }
        } else {
            return connection;
        }
    }
}
