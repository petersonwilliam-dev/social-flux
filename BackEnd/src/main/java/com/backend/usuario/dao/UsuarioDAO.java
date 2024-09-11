package com.backend.usuario.dao;

import com.backend.usuario.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    private Connection connectionSQL;

    public UsuarioDAO(Connection connection) {
        this.connectionSQL = connection;
    }

    public void insert(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios  (nome_usuario, senha, email, nome, sobrenome, telefone, data_nascimento, sexo) VALUES (?,?,?,?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connectionSQL.prepareStatement(sql)) {

            preparedStatement.setString(1, usuario.getNome_usuario());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setString(3, usuario.getEmail());
            preparedStatement.setString(4, usuario.getNome());
            preparedStatement.setString(5, usuario.getSobrenome());
            preparedStatement.setString(6, usuario.getTelefone());
            preparedStatement.setDate(7, Date.valueOf(usuario.getData_nascimento()));
            preparedStatement.setString(8, String.valueOf(usuario.getSexo()));

            preparedStatement.execute();

        } catch (SQLException exception) {
            throw exception;
        }
    }
}
