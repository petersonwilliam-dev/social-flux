package com.backend.services;

import com.backend.dao.AmizadeDao;
import com.backend.model.entities.Amizade;
import com.backend.model.entities.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AmizadeService {

    private static final Logger logger = LogManager.getLogger();
    private final AmizadeDao amizadeDao;

    public AmizadeService(Connection connection) {
        this.amizadeDao = new AmizadeDao(connection);
    }

    public Integer criarAmizade(Amizade amizade) {
        try {
            if (amizadeExiste(amizade.getSeguidor().getId(), amizade.getSeguido().getId())) throw new IllegalArgumentException("Amizade já existe");
            return amizadeDao.criarRelacao(amizade);
        } catch (SQLException e) {
            logger.error("Erro ao criar amizade: " + e);
            throw new RuntimeException(e);
        }
    }

    public Amizade buscarAmizade(Integer idSeguidor, Integer idSeguido) {
        try (ResultSet resultSet = amizadeDao.buscarRelacao(idSeguidor, idSeguido)) {
            if (resultSet == null) return null;
            return resultSetToAmizade(resultSet);
        } catch (SQLException e) {
            logger.error("Erro ao buscar usuário: " + e);
            throw new RuntimeException(e);
        }
    }

    public List<Amizade> amizadesNaoAceitas(Integer id) {
        List<Amizade> listaAmizade = new ArrayList<>();
        try (ResultSet resultSet = amizadeDao.amizadesNaoAceitas(id)) {
            if (resultSet == null) return listaAmizade;
            do {
                listaAmizade.add(resultSetToAmizade(resultSet));
            } while(resultSet.next());
            return listaAmizade;
        } catch (SQLException e) {
            logger.error("Erro ao buscar amizades pendentes: " + e);
            throw new RuntimeException(e);
        }
    }

    public List<Amizade> seguidoresDoUsuario(Integer id) {
        List<Amizade> listaAmizade = new ArrayList<>();
        try (ResultSet resultSet = amizadeDao.seguidoresDoUsuario(id)) {
            if (resultSet == null) return listaAmizade;
            do {
                listaAmizade.add(resultSetToAmizade(resultSet));
            } while(resultSet.next());
            return listaAmizade;
        } catch (SQLException e) {
            logger.error("Erro ao buscar amizades pendentes: " + e);
            throw new RuntimeException(e);
        }
    }

    public List<Amizade> seguidosDoUsuario(Integer id) {
        List<Amizade> listaAmizade = new ArrayList<>();
        try (ResultSet resultSet = amizadeDao.seguidosDoUsuario(id)) {
            if (resultSet == null) return listaAmizade;
            do {
                listaAmizade.add(resultSetToAmizade(resultSet));
            } while(resultSet.next());
            return listaAmizade;
        } catch (SQLException e) {
            logger.error("Erro ao buscar amizades pendentes: " + e);
            throw new RuntimeException(e);
        }
    }

    public Integer numeroSeguidores(Integer id) {
        try {
            return amizadeDao.numeroSeguidores(id);
        } catch (SQLException e) {
            logger.error("Erro ao pegar quantidade de seguidores: " + e);
            throw new RuntimeException(e);
        }
    }

    public Integer numeroSeguidos(Integer id) {
        try {
            return amizadeDao.numeroSeguidos(id);
        } catch (SQLException e) {
            logger.error("Erro ao pegar quantidade de seguidos: " + e);
            throw new RuntimeException(e);
        }
    }

    public boolean amizadeExiste(Integer idSeguidor, Integer idSeguido) {
        try (ResultSet resultSet = amizadeDao.buscarRelacao(idSeguidor, idSeguido)) {
            return resultSet != null;
        } catch (SQLException e) {
            logger.error("Não foi possível buscar amizade: " + e);
            throw new RuntimeException(e);
        }
    }

    public void removerAmizade(Integer id) {
        try {
            amizadeDao.removerAmizade(id);
        } catch (SQLException e) {
            logger.error("Erro ao remover amizade: " + e);
            throw new RuntimeException(e);
        }
    }

    public void aceitarAmizade(Integer id) {
        try {
            amizadeDao.aceitarAmizade(id);
        } catch (SQLException e) {
            logger.error("Erro ao aceitar amizade : " + e);
            throw new RuntimeException();
        }
    }

    private Amizade resultSetToAmizade(ResultSet resultSet) throws SQLException {
        Usuario seguidor = new Usuario();
        seguidor.setId(resultSet.getInt("id_seguidor"));
        seguidor.setUsername(resultSet.getString("username_seguidor"));
        seguidor.setNome(resultSet.getString("nome_seguidor"));
        seguidor.setPrivado(resultSet.getBoolean("privado_seguidor"));
        seguidor.setFoto_perfil(resultSet.getString("foto_seguidor"));

        Usuario seguido = new Usuario();
        seguido.setId(resultSet.getInt("id_seguido"));
        seguido.setUsername(resultSet.getString("username_seguido"));
        seguido.setNome(resultSet.getString("nome_seguido"));
        seguido.setPrivado(resultSet.getBoolean("privado_seguido"));
        seguido.setFoto_perfil(resultSet.getString("foto_seguido"));

        return new Amizade(
                resultSet.getInt("id"),
                seguidor,
                seguido,
                resultSet.getBoolean("aceito")
        );
    }

}
