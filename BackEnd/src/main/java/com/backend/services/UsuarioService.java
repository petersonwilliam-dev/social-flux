package com.backend.services;

import com.backend.dao.UsuarioDao;
import com.backend.model.entities.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private static final Logger logger = LogManager.getLogger();
    private final UsuarioDao usuarioDao;

    public UsuarioService(Connection connection) {
        this.usuarioDao = new UsuarioDao(connection);
    }

    public void inserir(Usuario usuario) {
        try {
            if (usernameExistenrte(usuario.getUsername())) throw new IllegalArgumentException("Já existe um usuário com esse usuário");
            if (emailExistente(usuario.getEmail())) throw new IllegalArgumentException("Já existe um usuário com esse email");
            usuarioDao.inserir(usuario);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public Usuario buscarPorUsername(String username) {
        try (ResultSet resultSet = usuarioDao.buscarPorUsername(username)) {
            if (resultSet == null) return null;
            return resultSetToUsuario(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public Usuario buscarPorEmail(String email) {
        try (ResultSet resultSet = usuarioDao.buscarPorEmail(email)) {
            if (resultSet == null) return null;
            return resultSetToUsuarioComplete(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public Usuario buscarPorId(Integer id) {
        try (ResultSet resultSet = usuarioDao.buscarPorId(id)) {
            if (resultSet == null) return null;
            return resultSetToUsuarioComplete(resultSet);
        } catch (SQLException e) {
            logger.error("Erro ao recuperar usuário por id: " + e);
            throw new RuntimeException(e);
        }
    }

    public boolean usernameExistenrte(String username) {
        try (ResultSet perfil =  usuarioDao.buscarPorUsername(username)) {
            return perfil != null;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException();
        }
    }

    public boolean emailExistente(String email) {
        try (ResultSet perfil =  usuarioDao.buscarPorEmail(email)) {
            return perfil != null;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException();
        }
    }

    public void atualizarFotoPerfil(Integer id, String foto_perfil) {
        try {
            usuarioDao.atualizarFotoPerfil(id, foto_perfil);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public void atualizarDadosPerfil(Usuario usuario) {
        try {
            usuarioDao.atualizarDadosPerfil(usuario);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public List<Usuario> buscarUsuariosPesquisados(String search) {
        List<Usuario> usuarios = new ArrayList<>();

        try (ResultSet resultSet = usuarioDao.buscarUsuariosPesquisados(search)) {
            if(resultSet == null) return usuarios;
            do {
                usuarios.add(resultSetToUsuario(resultSet));
            } while (resultSet.next());
            return usuarios;
        } catch (SQLException e) {
            logger.error("Erro ao pesquisar usuários: " + e);
            throw new RuntimeException(e);
        }
    }

    public List<Usuario> buscarRelacoesEmComum(Integer id, Integer id_usuario_perfil) {
        List<Usuario> listaUsuarios = new ArrayList<>();
        try (ResultSet resultSet = usuarioDao.buscarRelacoesEmComum(id, id_usuario_perfil)) {
            if (resultSet == null) return listaUsuarios;
            do {
                listaUsuarios.add(resultSetToUsuario(resultSet));
            } while (resultSet.next());

            return listaUsuarios;
        } catch (SQLException e) {
            logger.error("Erro ao buscar relações em comum: " + e);
            throw new RuntimeException(e);
        }
    }

    private Usuario resultSetToUsuarioComplete(ResultSet resultSet) throws SQLException {
        LocalDate data_criacao = resultSet.getDate("data_criacao").toLocalDate();
        LocalDate data_nascimento = resultSet.getDate("data_nascimento").toLocalDate();
        return new Usuario(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("nome"),
                resultSet.getString("email"),
                resultSet.getString("senha"),
                resultSet.getString("biografia"),
                resultSet.getString("telefone"),
                resultSet.getString("foto_perfil"),
                data_criacao,
                data_nascimento,
                resultSet.getBoolean("privado"),
                resultSet.getString("sexo").charAt(0)
        );
    }

    private Usuario resultSetToUsuario(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(resultSet.getInt("id"));
        usuario.setUsername(resultSet.getString("username"));
        usuario.setNome(resultSet.getString("nome"));
        usuario.setPrivado(resultSet.getBoolean("privado"));
        usuario.setBiografia(resultSet.getString("biografia"));
        usuario.setFoto_perfil(resultSet.getString("foto_perfil"));

        return usuario;
    }
}
