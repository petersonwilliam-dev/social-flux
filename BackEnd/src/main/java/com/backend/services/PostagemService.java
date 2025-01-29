package com.backend.services;

import com.backend.dao.PostagemDao;
import com.backend.model.entities.Postagem;
import com.backend.model.entities.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostagemService {

    private static final Logger logger = LogManager.getLogger();
    private final PostagemDao postagemDao;

    public PostagemService(Connection connection) {
        this.postagemDao = new PostagemDao(connection);
    }

     public void criarPostagem(Postagem postagem, Integer id_postagem) {
        try {
            postagemDao.criarPostagem(postagem, id_postagem);
        } catch (SQLException e) {
            logger.error("Erro ao criar postagem: " + e);
            throw new RuntimeException(e);
        }
     }

     public List<Postagem> buscarPostagensDoUsuario(Integer id_usuario) {
        List<Postagem> listaPostagens = new ArrayList<>();
        try (ResultSet postagens = postagemDao.buscarPostagensDoUsuario(id_usuario)) {
            if (postagens == null) return listaPostagens;
            do {
                listaPostagens.add(resultSetToPostagem(postagens));
            } while (postagens.next());
            return listaPostagens;
        } catch (SQLException e) {
            logger.error("Erro ao recuperar postagns do usuário: " + e);
            throw new RuntimeException(e);
        }
     }

     public Postagem buscarPostagemPorId(Integer id) {
        try (ResultSet resultSet = postagemDao.buscarPostagemId(id)) {
            if (resultSet == null) return null;
            return resultSetToPostagem(resultSet);
        } catch (SQLException e) {
            logger.error("Erro ao recuperar postagem por id: " + e);
            throw new RuntimeException(e);
        }
     }

     public List<Postagem> buscarRespostasPostagem(Integer id) {
         List<Postagem> listaPostagens = new ArrayList<>();

        try (ResultSet resultSet = postagemDao.buscarRespostasPostagem(id)) {
            if (resultSet == null) return listaPostagens;
            do {
                listaPostagens.add(resultSetToPostagem(resultSet));
            } while(resultSet.next());

            return listaPostagens;
        } catch (SQLException e) {
            logger.error("Erro ao recuperar respostas da postagem: " + e);
            throw new RuntimeException(e);
        }
     }

     public List<Postagem> buscarPostagemComMidiaUsuario(Integer id_usuario) {
        List<Postagem> listaPostagens = new ArrayList<>();
        try (ResultSet resultSet = postagemDao.buscarPostagensComMidiaUsuario(id_usuario)) {
            if (resultSet == null) return listaPostagens;
            do {
                listaPostagens.add(resultSetToPostagem(resultSet));
            } while (resultSet.next());
            return listaPostagens;
        } catch (SQLException e) {
            logger.error("Erro ao buscar postagens com mídia do usuario: " + e);
            throw new RuntimeException(e);
        }
     }

     public List<Postagem> buscarPostsInteracoesUsuario(Integer id) {
        List<Postagem> listaPostagems = new ArrayList<>();
        try (ResultSet resultSet = postagemDao.buscarPostsInteracoesUsuario(id)){
            if (resultSet == null) return listaPostagems;
            do {
                listaPostagems.add(resultSetToPostagem(resultSet));
            } while (resultSet.next());

            return listaPostagems;
        } catch (SQLException e) {
            logger.error("Erro ao recuperar postagens e interações do usuário: " + e);
            throw new RuntimeException(e);
        }
     }

     public List<Postagem> buscarPostagensSeguidosUsuario(Integer id_seguidor) {
        List<Postagem> listaPostagens = new ArrayList<>();
        try (ResultSet resultSet = postagemDao.buscarPostagensSeguidosUsuario(id_seguidor)) {
            if (resultSet == null) return listaPostagens;
            do {
                listaPostagens.add(resultSetToPostagem(resultSet));
            } while (resultSet.next());
            return listaPostagens;
        } catch (SQLException e) {
            logger.error("Erro ao buscar postagens dos seguidos: " + e);
            throw new RuntimeException(e);
        }
     }

     public void excluirPostagem(Integer id) {
        try {
            postagemDao.excluirPostagem(id);
        } catch (SQLException e) {
            logger.error("Erro ao excluir postagem: " + e);
            throw new RuntimeException(e);
        }
     }

     public Integer buscarNumeroRespostas(Integer id) {
        try {
            return postagemDao.buscarNumeroRespostas(id);
        } catch (SQLException e) {
            logger.error("Erro ao buscar numero de respostas: " + e);
            throw new RuntimeException(e);
        }
     }

     private Postagem resultSetToPostagem(ResultSet resultSet) throws SQLException {
         LocalDateTime data_postagem = resultSet.getTimestamp("data_postagem").toLocalDateTime();
         Usuario usuario = new Usuario();
         usuario.setId(resultSet.getInt("id_usuario"));
         usuario.setUsername(resultSet.getString("username_usuario"));
         usuario.setNome(resultSet.getString("nome_usuario"));
         usuario.setFoto_perfil(resultSet.getString("foto_usuario"));


         try (ResultSet resultSetPostagem = postagemDao.buscarPostagemId(resultSet.getInt("id_postagem"))) {

             Postagem postagemReferente = null;

             if (resultSetPostagem != null) {
                 postagemReferente = resultSetToPostagem(resultSetPostagem);
             }
             return new Postagem(
                     resultSet.getInt("id"),
                     resultSet.getString("imagem"),
                     resultSet.getString("legenda"),
                     data_postagem,
                     usuario,
                     postagemReferente
             );
         }
     }
}
