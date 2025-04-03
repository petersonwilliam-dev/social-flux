package com.backend.controllers;

import com.backend.Keys;
import com.backend.Mensagem;
import com.backend.model.entities.Curtida;
import com.backend.services.CurtidaService;
import com.backend.util.AuthMiddleware;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CurtidaController {

    private static final Logger logger = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void inserirCurtida(Context ctx) {

        CurtidaService curtidaService = ctx.appData(Keys.CURTIDA_SERVICE.key());

        AuthMiddleware.AuthValidate(ctx);

        try {
            Curtida curtida = mapper.readValue(ctx.body(), Curtida.class);
            Integer id = curtidaService.inserirCurtida(curtida);
            curtida.setId(id);
            ctx.status(201).json(curtida);
        } catch (JsonProcessingException e) {
            logger.error("Erro ao inserir curtida: " + e);
            ctx.status(500).json(new Mensagem("Erro interno ao curtir postagem", false));
        }
    }

    public static void buscarCurtida(Context ctx) {

        CurtidaService curtidaService = ctx.appData(Keys.CURTIDA_SERVICE.key());

        AuthMiddleware.AuthValidate(ctx);

        try {
            if (ctx.queryParam("id_usuario") != null && ctx.queryParam("id_conteudo") != null) {
                Integer id_usuario = Integer.parseInt(ctx.queryParam("id_usuario"));
                Integer id_conteudo = Integer.parseInt(ctx.queryParam("id_conteudo"));

                Curtida curtida = curtidaService.buscarCurtida(id_usuario, id_conteudo);
                if (curtida != null) {
                    ctx.status(200).json(curtida);
                }
            } else if (ctx.queryParam("id_usuario") != null) {
                ctx.status(200);
            } else if (ctx.queryParam("id_conteudo") != null) {
                ctx.status(200);
            }
        } catch (NumberFormatException e) {
            logger.info("Parâmetros inválidos ao buscar curtidas: " + e);
            ctx.status(400).json(new Mensagem("Parâmetros inválidos", false));
        }
    }

    public static void removerCurtida(Context ctx) {

        CurtidaService curtidaService = ctx.appData(Keys.CURTIDA_SERVICE.key());

        AuthMiddleware.AuthValidate(ctx);

        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));

            curtidaService.removerCurtida(id);
            ctx.status(200);
        } catch (NumberFormatException e) {
            logger.info("ID Inválido: " + e);
            ctx.status(400).json(new Mensagem("ID inválido", false));
        }
    }

    public static void numeroCurtidasPostagem(Context ctx) {

        CurtidaService curtidaService = ctx.appData(Keys.CURTIDA_SERVICE.key());

        AuthMiddleware.AuthValidate(ctx);

        try {
            Integer id_conteudo = Integer.parseInt(ctx.pathParam("id_conteudo"));
            Integer numeroCurtidas = curtidaService.numeroCurtidasPostagem(id_conteudo);
            ctx.status(200).json(numeroCurtidas);
        } catch (NumberFormatException e) {
            logger.info("ID conteúdo inválido: " + e);
            ctx.status(400).json(new Mensagem("ID do conteúdo inválido", false));
        }
    }
}
