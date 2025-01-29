package com.backend.services;

import com.backend.dao.NotificationDao;
import com.backend.model.entities.Notification;
import com.backend.model.entities.Usuario;
import com.backend.model.enums.NotificationCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private static final Logger logger = LogManager.getLogger(NotificationService.class);
    private final NotificationDao notificationDao;

    public NotificationService(Connection connection) {
        this.notificationDao = new NotificationDao(connection);
    }

    public void insertNotification(Notification notification) {
        try {
            if (notification.getSender().getId() != notification.getReceiver().getId()) {
                notificationDao.insertNotification(notification);
            }
        } catch (SQLException e) {
            logger.error("Error to insert notification", e);
            throw new RuntimeException(e);
        }
    }

    public List<Notification> getUserNotifications(Integer id) {
        List<Notification> notificationList = new ArrayList<>();

        try (ResultSet resultSet = notificationDao.getUserNotifications(id)) {
            if (resultSet == null) return notificationList;
            do {
                notificationList.add(resultSetToNotification(resultSet));
            } while (resultSet.next());

            return notificationList;
        } catch (SQLException e) {
            logger.error("Error to get user notifications: " + e);
            throw new RuntimeException(e);
        }
    }

    public Integer notificationNotSeen(Integer id) {

        try {
            return notificationDao.notificationsNotSeen(id);
        } catch (SQLException e) {
            logger.error("Error returning number of unseen notifications: " + e);
            throw new RuntimeException(e);
        }
    }

    public void viewNotifications(Integer id) {
        try {
            notificationDao.viewNotifications(id);
        } catch (SQLException e) {
            logger.error("Error when viewing notifications: " + e);
            throw new RuntimeException(e);
        }
    }

    private Notification resultSetToNotification(ResultSet resultSet) throws SQLException {

        Usuario sender = new Usuario();
        sender.setId(resultSet.getInt("sender_id"));
        sender.setUsername(resultSet.getString("sender_username"));
        sender.setNome(resultSet.getString("sender_nome"));
        sender.setFoto_perfil(resultSet.getString("sender_foto_perfil"));

        return new Notification(
                resultSet.getInt("id"),
                sender,
                null,
                resultSet.getString("title"),
                resultSet.getString("description"),
                NotificationCategory.fromId(resultSet.getInt("id_category")),
                resultSet.getInt("id_post"),
                resultSet.getBoolean("viewed")
        );
    }
}
