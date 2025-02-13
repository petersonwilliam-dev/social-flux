package com.backend.dao;

import com.backend.model.entities.Postagem;

import java.sql.*;

public class PostagemDao {

    private final Connection connection;

    public PostagemDao(Connection connection) {
        this.connection = connection;
    }

    public void criarPostagem(Postagem postagem, Integer id_postagem) throws SQLException {
        String sql = "INSERT INTO postagem (imagem, legenda, data_postagem, id_usuario, id_postagem) VALUES (?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, postagem.getImagem());
        preparedStatement.setString(2, postagem.getLegenda());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(postagem.getData_postagem()));
        preparedStatement.setInt(4, postagem.getUsuario().getId());

        if (id_postagem != null) {
            preparedStatement.setInt(5, id_postagem);
        } else {
            preparedStatement.setNull(5, Types.INTEGER);
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    public ResultSet buscarPostagemId(Integer id) throws SQLException {
        String sql = "SELECT postagem.id, postagem.imagem, postagem.legenda, postagem.data_postagem, postagem.id_postagem, " +
                "usuario.id AS id_usuario, usuario.username AS username_usuario, usuario.nome AS nome_usuario, usuario.foto_perfil AS foto_usuario " +
                "FROM postagem " +
                "JOIN usuario AS usuario ON postagem.id_usuario = usuario.id " +
                "WHERE postagem.id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public ResultSet buscarPostagensDoUsuario(Integer id) throws SQLException{
        String sql = "SELECT postagem.id, postagem.imagem, postagem.legenda, postagem.data_postagem, postagem.id_postagem, " +
                "usuario.id AS id_usuario, usuario.username AS username_usuario, usuario.nome AS nome_usuario, usuario.foto_perfil AS foto_usuario " +
                "FROM postagem " +
                "JOIN usuario AS usuario ON postagem.id_usuario = usuario.id " +
                "WHERE usuario.id = ? AND postagem.id_postagem IS NULL " +
                "ORDER BY postagem.data_postagem DESC";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public ResultSet buscarRespostasPostagem(Integer id) throws SQLException {

        String sql = "SELECT postagem.id, postagem.imagem, postagem.legenda, postagem.data_postagem, postagem.id_postagem, " +
                "usuario.id AS id_usuario, usuario.username AS username_usuario, usuario.nome AS nome_usuario, usuario.foto_perfil AS foto_usuario " +
                "FROM postagem " +
                "JOIN usuario AS usuario ON postagem.id_usuario = usuario.id " +
                "WHERE postagem.id_postagem = ? " +
                "ORDER BY postagem.data_postagem DESC";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public ResultSet buscarPostsInteracoesUsuario(Integer id) throws SQLException {

        String sql = "SELECT postagem.id, postagem.imagem, postagem.legenda, postagem.data_postagem, postagem.id_postagem, " +
                "usuario.id AS id_usuario, usuario.username AS username_usuario, usuario.nome AS nome_usuario, usuario.foto_perfil AS foto_usuario " +
                "FROM postagem " +
                "JOIN usuario AS usuario ON postagem.id_usuario = usuario.id " +
                "WHERE usuario.id = ? " +
                "ORDER BY postagem.data_postagem DESC";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public ResultSet buscarPostagensComMidiaUsuario(Integer id) throws SQLException {

        String sql = "SELECT postagem.id, postagem.imagem, postagem.legenda, postagem.data_postagem, postagem.id_postagem, " +
                "usuario.id AS id_usuario, usuario.username AS username_usuario, usuario.nome AS nome_usuario, usuario.foto_perfil AS foto_usuario " +
                "FROM postagem " +
                "JOIN usuario AS usuario ON postagem.id_usuario = usuario.id " +
                "WHERE usuario.id = ? AND postagem.imagem IS NOT NULL " +
                "ORDER BY postagem.data_postagem DESC";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public ResultSet buscarPostagensSeguidosUsuario(Integer id) throws SQLException{

        String sql = "SELECT postagem.id, postagem.imagem, postagem.legenda, postagem.data_postagem, postagem.id_postagem, " +
                "usuario.id AS id_usuario, usuario.username AS username_usuario, usuario.nome AS nome_usuario, usuario.foto_perfil AS foto_usuario " +
                "FROM postagem " +
                "JOIN usuario ON postagem.id_usuario = usuario.id " +
                "JOIN relacao ON relacao.id_seguido = usuario.id " +
                "WHERE relacao.id_seguidor = ? AND relacao.aceito = 1 AND postagem.id_postagem IS NULL " +
                "ORDER BY postagem.data_postagem DESC";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public void excluirPostagem(Integer id) throws SQLException {

        String sql = "DELETE FROM postagem WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        preparedStatement.execute();
        preparedStatement.close();
    }
}
