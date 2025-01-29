package com.backend.dao;

import com.backend.model.entities.Curtida;

import java.sql.*;

public class CurtidaDao {

    private final Connection connection;


    public CurtidaDao(Connection connection) {
        this.connection = connection;
    }

    public Integer inserirCurtida(Curtida curtida) throws SQLException {

        String sql = "INSERT INTO curtida (id_usuario, id_conteudo) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, curtida.getId_usuario());
        preparedStatement.setInt(2, curtida.getId_conteudo());

        preparedStatement.execute();

        try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    public ResultSet buscarCurtida(Integer id_usuario, Integer id_conteudo) throws SQLException {

        String sql = "SELECT * FROM curtida WHERE id_usuario = ? AND id_conteudo = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id_usuario);
        preparedStatement.setInt(2, id_conteudo);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public void removerCurtida(Integer id) throws SQLException {
        String sql = "DELETE FROM curtida WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        preparedStatement.close();

    }

    public Integer numeroCurtidaPostagem(Integer id_conteudo) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM curtida WHERE id_conteudo = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id_conteudo);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("total");
        }
    }
}
