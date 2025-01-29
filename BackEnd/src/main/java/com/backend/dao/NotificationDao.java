package com.backend.dao;

import com.backend.model.entities.Notification;

import java.sql.*;

public class NotificationDao {

    private final Connection connection;

    public NotificationDao(Connection connection) {
        this.connection = connection;
    }

    public void insertNotification(Notification notification) throws SQLException {

        String sql = "INSERT INTO notification (title, description, id_sender, id_receiver, id_category, id_post, viewed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, notification.getTitle());

        if (notification.getDescription() != null) preparedStatement.setString(2, notification.getDescription());
        else preparedStatement.setNull(2, Types.VARCHAR);

        preparedStatement.setInt(3, notification.getSender().getId());

        if (notification.getReceiver() != null) preparedStatement.setInt(4, notification.getReceiver().getId());
        else preparedStatement.setNull(4, Types.INTEGER);

        preparedStatement.setInt(5, notification.getCategory().getId());

        if (notification.getId_post() != null) preparedStatement.setInt(6, notification.getId_post());
        else preparedStatement.setNull(6, Types.INTEGER);

        preparedStatement.setBoolean(7, false);

        preparedStatement.execute();
        preparedStatement.close();
    }

    public ResultSet getUserNotifications(Integer userId) throws SQLException {

        String sql = "SELECT notification.id, notification.title, notification.description, notification.id_category, notification.id_post, notification.viewed, " +
                "sender.id AS sender_id, sender.username AS sender_username, sender.nome AS sender_nome, sender.foto_perfil AS sender_foto_perfil " +
                "FROM notification " +
                "JOIN usuario AS sender ON notification.id_sender = sender.id " +
                "WHERE notification.id_receiver = ? " +
                "ORDER BY notification.id DESC";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public Integer notificationsNotSeen(Integer id) throws SQLException {

        String sql = "SELECT COUNT(*) AS total FROM notification WHERE id_receiver = ? AND viewed = 0";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("total");
        }
    }

    public void viewNotifications(Integer id) throws SQLException {

        String sql = "UPDATE notification SET viewed = 1 WHERE id_receiver = ? AND viewed = 0";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }
}
