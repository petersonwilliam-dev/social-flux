package com.backend.services;

import com.backend.dao.CurtidaDao;
import com.backend.model.entities.Curtida;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurtidaService {

    private static final Logger logger = LogManager.getLogger();
    private final CurtidaDao curtidaDao;

    public CurtidaService(Connection connection) {
        this.curtidaDao = new CurtidaDao(connection);
    }

    public Integer inserirCurtida(Curtida curtida) {
        try {
            return curtidaDao.inserirCurtida(curtida);
        } catch (SQLException e) {
            logger.error("Erro ao curtir: " + e);
            throw new RuntimeException(e);
        }
    }

    public Curtida buscarCurtida(Integer id_usuario, Integer id_conteudo) {
        try (ResultSet resultSet = curtidaDao.buscarCurtida(id_usuario, id_conteudo)) {
            if (resultSet == null) return null;
            return new Curtida(
                    resultSet.getInt("id"),
                    resultSet.getInt("id_usuario"),
                    resultSet.getInt("id_conteudo")
            );
        } catch (SQLException e) {
            logger.error("Erro ao buscar curtida: " + e);
            throw new RuntimeException(e);
        }
    }

    public void removerCurtida(Integer id) {
        try {
            curtidaDao.removerCurtida(id);
        } catch (SQLException e) {
            logger.error("Erro ao remover curtida: " + e);
            throw new RuntimeException(e);
        }
    }

    public Integer numeroCurtidasPostagem(Integer id_conteudo) {
        try {
            return curtidaDao.numeroCurtidaPostagem(id_conteudo);
        } catch (SQLException e) {
            logger.error("Erro ao recuperar número de curtidas: " + e);
            throw new RuntimeException(e);
        }
    }
}
