package com.backend.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDBRepository {

    private static final Logger logger = LogManager.getLogger();
    private static Connection connection;

    public static Connection getConnection(String url, String username, String password) {
        if (connection == null) {
            try {
                return connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException exception) {
                logger.error(exception);
                return null;
            }
        } else {
            return connection;
        }
    }
}
