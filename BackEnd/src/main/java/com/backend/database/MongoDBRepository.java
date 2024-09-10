package com.backend.database;

import com.mongodb.MongoClientException;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MongoDBRepository {

    private static final Logger logger = LogManager.getLogger();
    private final MongoClient mongoClient;

    public MongoDBRepository(String connectionString) {
        this.mongoClient = MongoClients.create(connectionString);
    }

    public MongoDatabase getDatabase(String databaseName) {
        try {
            return mongoClient.getDatabase(databaseName);
        } catch (MongoException exception) {
            logger.error(exception);
            return null;
        }
    }

    public boolean conectado(String databaseName) {
        if (mongoClient == null) {
            return false;
        }
        try {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            database.listCollectionNames().first();
            return true;
        } catch (MongoClientException exception) {
            logger.error(exception);
            return false;
        }
    }

    public void close() {
        mongoClient.close();
    }
}
