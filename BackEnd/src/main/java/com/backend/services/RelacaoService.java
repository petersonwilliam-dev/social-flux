package com.backend.services;

import com.backend.dao.RelacaoDao;
import com.backend.model.entities.Relacao;
import com.backend.model.entities.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelacaoService {

    private static final Logger logger = LogManager.getLogger();
    private final RelacaoDao relacaoDao;

    public RelacaoService(Connection connection) {
        this.relacaoDao = new RelacaoDao(connection);
    }

    public Integer criarRelacao(Relacao relacao) {
        try {
            if (RelacaoExiste(relacao.getSeguidor().getId(), relacao.getSeguido().getId())) throw new IllegalArgumentException("Amizade já existe");
            return relacaoDao.criarRelacao(relacao);
        } catch (SQLException e) {
            logger.error("Erro ao criar amizade: " + e);
            throw new RuntimeException(e);
        }
    }

    public Relacao buscarRelacao(Integer idSeguidor, Integer idSeguido) {
        try (ResultSet resultSet = relacaoDao.buscarRelacao(idSeguidor, idSeguido)) {
            if (resultSet == null) return null;
            return resultSetToRelacao(resultSet);
        } catch (SQLException e) {
            logger.error("Erro ao buscar usuário: " + e);
            throw new RuntimeException(e);
        }
    }

    public List<Relacao> RelacoesNaoAceitas(Integer id) {
        List<Relacao> listaRelacao = new ArrayList<>();
        try (ResultSet resultSet = relacaoDao.RelacoesNaoAceitas(id)) {
            if (resultSet == null) return listaRelacao;
            do {
                listaRelacao.add(resultSetToRelacao(resultSet));
            } while(resultSet.next());
            return listaRelacao;
        } catch (SQLException e) {
            logger.error("Erro ao buscar amizades pendentes: " + e);
            throw new RuntimeException(e);
        }
    }

    public List<Relacao> seguidoresDoUsuario(Integer id) {
        List<Relacao> listaRelacao = new ArrayList<>();
        try (ResultSet resultSet = relacaoDao.seguidoresDoUsuario(id)) {
            if (resultSet == null) return listaRelacao;
            do {
                listaRelacao.add(resultSetToRelacao(resultSet));
            } while(resultSet.next());
            return listaRelacao;
        } catch (SQLException e) {
            logger.error("Erro ao buscar amizades pendentes: " + e);
            throw new RuntimeException(e);
        }
    }

    public List<Relacao> seguidosDoUsuario(Integer id) {
        List<Relacao> listaRelacao = new ArrayList<>();
        try (ResultSet resultSet = relacaoDao.seguidosDoUsuario(id)) {
            if (resultSet == null) return listaRelacao;
            do {
                listaRelacao.add(resultSetToRelacao(resultSet));
            } while(resultSet.next());
            return listaRelacao;
        } catch (SQLException e) {
            logger.error("Erro ao buscar amizades pendentes: " + e);
            throw new RuntimeException(e);
        }
    }

    public Integer numeroSeguidores(Integer id) {
        try {
            return relacaoDao.numeroSeguidores(id);
        } catch (SQLException e) {
            logger.error("Erro ao pegar quantidade de seguidores: " + e);
            throw new RuntimeException(e);
        }
    }

    public Integer numeroSeguidos(Integer id) {
        try {
            return relacaoDao.numeroSeguidos(id);
        } catch (SQLException e) {
            logger.error("Erro ao pegar quantidade de seguidos: " + e);
            throw new RuntimeException(e);
        }
    }

    public boolean RelacaoExiste(Integer idSeguidor, Integer idSeguido) {
        try (ResultSet resultSet = relacaoDao.buscarRelacao(idSeguidor, idSeguido)) {
            return resultSet != null;
        } catch (SQLException e) {
            logger.error("Não foi possível buscar amizade: " + e);
            throw new RuntimeException(e);
        }
    }

    public void removerRelacao(Integer id) {
        try {
            relacaoDao.removerRelacao(id);
        } catch (SQLException e) {
            logger.error("Erro ao remover amizade: " + e);
            throw new RuntimeException(e);
        }
    }

    public void aceitarRelacao(Integer id) {
        try {
            relacaoDao.aceitarRelacao(id);
        } catch (SQLException e) {
            logger.error("Erro ao aceitar amizade : " + e);
            throw new RuntimeException();
        }
    }

    private Relacao resultSetToRelacao(ResultSet resultSet) throws SQLException {
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

        return new Relacao(
                resultSet.getInt("id"),
                seguidor,
                seguido,
                resultSet.getBoolean("aceito")
        );
    }

}
