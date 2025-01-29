package com.backend.dao;

import com.backend.model.entities.Amizade;

import java.sql.*;

public class AmizadeDao {

    private Connection connection;

    public AmizadeDao(Connection connection) {
        this.connection = connection;
    }

    public Integer criarRelacao(Amizade amizade) throws SQLException {
        String sql = "INSERT INTO relacao (id_seguidor, id_seguido, aceito) VALUES (?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, amizade.getSeguidor().getId());
        preparedStatement.setInt(2, amizade.getSeguido().getId());
        preparedStatement.setBoolean(3, amizade.getAceito());

        preparedStatement.execute();
        try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    public ResultSet buscarRelacao(Integer idSeguidor, Integer idSeguido) throws SQLException {

        String sql = "SELECT relacao.id, relacao.aceito, \n" +
                "seguidor.id AS id_seguidor, seguidor.username AS username_seguidor, seguidor.nome AS nome_seguidor, seguidor.privado AS privado_seguidor, seguidor.foto_perfil AS foto_seguidor, " +
                "seguido.id AS id_seguido, seguido.username AS username_seguido, seguido.nome AS nome_seguido, seguido.privado AS privado_seguido, seguido.foto_perfil AS foto_seguido " +
                "FROM relacao " +
                "JOIN usuario AS seguidor ON relacao.id_seguidor = seguidor.id " +
                "JOIN usuario AS seguido ON relacao.id_seguido = seguido.id " +
                "WHERE seguidor.id = ? AND seguido.id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idSeguidor);
        preparedStatement.setInt(2, idSeguido);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public Integer numeroSeguidores(Integer id) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM relacao WHERE id_seguido = ? AND aceito = 1";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("total");
        }
    }

    public Integer numeroSeguidos(Integer id) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM relacao WHERE id_seguidor = ? AND aceito = 1";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("total");
        }
    }

    public ResultSet amizadesNaoAceitas(Integer id) throws SQLException {
        String sql = "SELECT relacao.id, relacao.aceito, \n" +
                "seguidor.id AS id_seguidor, seguidor.username AS username_seguidor, seguidor.nome AS nome_seguidor, seguidor.privado AS privado_seguidor, seguidor.foto_perfil AS foto_seguidor, " +
                "seguido.id AS id_seguido, seguido.username AS username_seguido, seguido.nome AS nome_seguido, seguido.privado AS privado_seguido, seguido.foto_perfil AS foto_seguido " +
                "FROM relacao " +
                "JOIN usuario AS seguidor ON relacao.id_seguidor = seguidor.id " +
                "JOIN usuario AS seguido ON relacao.id_seguido = seguido.id " +
                "WHERE seguido.id = ? AND relacao.aceito = 0";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public ResultSet seguidoresDoUsuario(Integer id) throws SQLException {
        String sql = "SELECT relacao.id, relacao.aceito, \n" +
                "seguidor.id AS id_seguidor, seguidor.username AS username_seguidor, seguidor.nome AS nome_seguidor, seguidor.privado AS privado_seguidor, seguidor.foto_perfil AS foto_seguidor, " +
                "seguido.id AS id_seguido, seguido.username AS username_seguido, seguido.nome AS nome_seguido, seguido.privado AS privado_seguido, seguido.foto_perfil AS foto_seguido " +
                "FROM relacao " +
                "JOIN usuario AS seguidor ON relacao.id_seguidor = seguidor.id " +
                "JOIN usuario AS seguido ON relacao.id_seguido = seguido.id " +
                "WHERE seguido.id = ? AND relacao.aceito = 1";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public ResultSet seguidosDoUsuario(Integer id) throws SQLException {
        String sql = "SELECT relacao.id, relacao.aceito, \n" +
                "seguidor.id AS id_seguidor, seguidor.username AS username_seguidor, seguidor.nome AS nome_seguidor, seguidor.privado AS privado_seguidor, seguidor.foto_perfil AS foto_seguidor, " +
                "seguido.id AS id_seguido, seguido.username AS username_seguido, seguido.nome AS nome_seguido, seguido.privado AS privado_seguido, seguido.foto_perfil AS foto_seguido " +
                "FROM relacao " +
                "JOIN usuario AS seguidor ON relacao.id_seguidor = seguidor.id " +
                "JOIN usuario AS seguido ON relacao.id_seguido = seguido.id " +
                "WHERE seguidor.id = ? AND relacao.aceito = 1";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public void aceitarAmizade(Integer id) throws SQLException {
        String sql = "UPDATE relacao SET aceito = 1 WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void removerAmizade(Integer id) throws SQLException {
        String sql = "DELETE FROM relacao WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }
}
