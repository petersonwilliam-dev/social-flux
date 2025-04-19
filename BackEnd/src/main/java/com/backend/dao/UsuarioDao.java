package com.backend.dao;

import com.backend.model.entities.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;

public class UsuarioDao {

    private Connection connectionSQL;

    public UsuarioDao(Connection connection) {
        this.connectionSQL = connection;
    }

    public void inserir(Usuario usuario) throws SQLException {

        String sql = "INSERT INTO usuario (username, nome, email, senha, biografia, telefone, foto_perfil, sexo, data_criacao, data_nascimento, privado) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = connectionSQL.prepareStatement(sql);
        preparedStatement.setString(1, usuario.getUsername());
        preparedStatement.setString(2, usuario.getNome());
        preparedStatement.setString(3, usuario.getEmail());
        preparedStatement.setString(4, BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
        preparedStatement.setString(5, usuario.getBiografia());
        preparedStatement.setString(6, usuario.getTelefone());
        preparedStatement.setString(7, usuario.getFoto_perfil());
        preparedStatement.setString(8, String.valueOf(usuario.getSexo()));
        preparedStatement.setDate(9, Date.valueOf(LocalDate.now()));
        preparedStatement.setDate(10, Date.valueOf(usuario.getData_nascimento()));
        preparedStatement.setBoolean(11, false);

        preparedStatement.execute();
        preparedStatement.close();
    }

    public ResultSet buscarPorUsername(String username) throws SQLException {

        String sql = "SELECT * FROM usuario WHERE username = ?";

        PreparedStatement preparedStatement = connectionSQL.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public ResultSet buscarPorEmail(String email) throws SQLException {

        String sql = "SELECT * FROM usuario WHERE email = ?";

        PreparedStatement preparedStatement = connectionSQL.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public ResultSet buscarPorId(Integer id) throws SQLException{
        String sql = "SELECT * FROM usuario WHERE id = ?";

        PreparedStatement preparedStatement = connectionSQL.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public void atualizarFotoPerfil(Integer id, String foto_perfil) throws SQLException {
        String sql = "UPDATE usuario SET foto_perfil = ? WHERE id = ?";

        PreparedStatement preparedStatement = connectionSQL.prepareStatement(sql);
        preparedStatement.setString(1, foto_perfil);
        preparedStatement.setInt(2, id);

        preparedStatement.execute();
        preparedStatement.close();
    }

    public void atualizarDadosPerfil(Usuario usuario) throws SQLException {

        String sql = "UPDATE usuario SET username = ?, nome = ?, biografia = ?, privado = ? WHERE id = ?";

        PreparedStatement preparedStatement = connectionSQL.prepareStatement(sql);
        preparedStatement.setString(1, usuario.getUsername());
        preparedStatement.setString(2, usuario.getNome());
        preparedStatement.setString(3, usuario.getBiografia());
        preparedStatement.setBoolean(4, usuario.getPrivado());
        preparedStatement.setInt(5, usuario.getId());

        preparedStatement.execute();
        preparedStatement.close();
    }

    public ResultSet buscarUsuariosPesquisados(String search) throws SQLException{

        String sql = "SELECT * FROM usuario WHERE username LIKE ? OR nome LIKE ? LIMIT 10";

        PreparedStatement preparedStatement = connectionSQL.prepareStatement(sql);
        preparedStatement.setString(1, "%"+search+"%");
        preparedStatement.setString(2, "%"+search+"%");

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public ResultSet buscarRelacoesEmComum(Integer id, Integer id_usuario_perfil) throws SQLException {

        String sql = "SELECT u.id, u.username, u.nome, u.foto_perfil, u.privado, u.biografia " +
                "FROM usuario u " +
                "JOIN relacao relacao_usuario ON u.id = relacao_usuario.id_seguido " +
                "JOIN relacao relacao_usuario_perfil ON u.id = relacao_usuario_perfil.id_seguidor " +
                "WHERE relacao_usuario.aceito = 1 AND relacao_usuario_perfil.aceito = 1 " +
                "AND relacao_usuario.id_seguidor = ? " +
                "AND relacao_usuario_perfil.id_seguido = ? " +
                "AND u.id != ?";

        PreparedStatement preparedStatement = connectionSQL.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, id_usuario_perfil);
        preparedStatement.setInt(3, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet;
        return null;
    }

    public void excluirUsuario(Integer id) throws SQLException {

        String sql = "DELETE FROM usuario WHERE id = ?";

        PreparedStatement preparedStatement = connectionSQL.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        preparedStatement.execute();
        preparedStatement.close();
    }
}
