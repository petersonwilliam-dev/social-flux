package com.backend.controllers;

import com.backend.Mensagem;
import com.backend.Keys;
import com.backend.model.entities.Relacao;
import com.backend.services.RelacaoService;
import com.backend.model.entities.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class RelacaoController {

    private static final Logger logger = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void criarRelacao(Context ctx) {
        RelacaoService relacaoService = ctx.appData(Keys.AMIZADE_SERVICE.key());

        Map<String, Object> dados = ctx.bodyAsClass(Map.class);
        Map<String, Object> seguidorJson = (Map<String, Object>) dados.get("user");
        Map<String, Object> seguidoJson = (Map<String, Object>) dados.get("profileUser");

        try {
            Usuario seguidor = mapper.convertValue(seguidorJson, Usuario.class);
            Usuario seguido = mapper.convertValue(seguidoJson, Usuario.class);

            Relacao relacao = new Relacao();
            relacao.setSeguidor(seguidor);
            relacao.setSeguido(seguido);
            relacao.setAceito(!seguido.getPrivado());

            if (!relacaoService.RelacaoExiste(seguidor.getId(), seguido.getId())) {
                relacao.setId(relacaoService.criarRelacao(relacao));
                ctx.status(201).json(relacao);
            } else {
                ctx.status(409).json(new Mensagem("Você já possui amizade com esse usuário", false));
            }
        } catch (IllegalArgumentException e) {
            logger.error(e);
            ctx.status(500).json(new Mensagem("Erro interno ao processar requisição com JSON", false));
        }
    }

    public static void buscarRelacao(Context ctx) {
        RelacaoService relacaoService = ctx.appData(Keys.AMIZADE_SERVICE.key());

        String id_seguidor = ctx.queryParam("id_seguidor");
        String id_seguido = ctx.queryParam("id_seguido");
        try {
            if (id_seguidor != null && id_seguido != null) {
                Integer idSeguidor = Integer.parseInt(id_seguidor);
                Integer idSeguido = Integer.parseInt(id_seguido);

                Relacao relacao = relacaoService.buscarRelacao(idSeguidor, idSeguido);
                if (relacao != null) {
                    ctx.status(200).json(relacao);
                }
            } else if (id_seguido != null) {
                Integer idSeguido = Integer.parseInt(id_seguido);
                List<Relacao> listaRelacao = relacaoService.seguidoresDoUsuario(idSeguido);
                ctx.status(200).json(listaRelacao);
            } else if (id_seguidor != null) {
                Integer idSeguidor = Integer.parseInt(id_seguidor);
                List<Relacao> listaRelacao = relacaoService.seguidosDoUsuario(idSeguidor);
                ctx.status(200).json(listaRelacao);
            } else {
                ctx.status(400).json(new Mensagem("Parâmetros nulos", false));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensagem("Parâmetros inválidos!", false));
        }
    }

    public static void numeroSeguidores(Context ctx) {
        RelacaoService relacaoService = ctx.appData(Keys.AMIZADE_SERVICE.key());
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Integer numeroSeguidores = relacaoService.numeroSeguidores(id);
            ctx.status(200).json(numeroSeguidores);
        } catch (NumberFormatException e) {
            logger.error("Id inválido");
            ctx.status(400).json(new Mensagem("Id inválido", false));
        }
    }

    public static void numeroSeguidos(Context ctx) {
        RelacaoService relacaoService = ctx.appData(Keys.AMIZADE_SERVICE.key());
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Integer numeroSeguidos = relacaoService.numeroSeguidos(id);
            ctx.status(200).json(numeroSeguidos);
        } catch (NumberFormatException e) {
            logger.error("Id inválido");
            ctx.status(400).json(new Mensagem("Id inválido", false));
        }
    }

    public static void removerRelacao(Context ctx) {
        RelacaoService relacaoService = ctx.appData(Keys.AMIZADE_SERVICE.key());
        String id = ctx.pathParam("id");
        if (id.matches("\\d+")) {
            Integer idAmizade = Integer.parseInt(id);
            relacaoService.removerRelacao(idAmizade);
        } else {
            ctx.status(400).json(new Mensagem("Id não é válido", false));
        }
    }

    public static void aceitarRelacao(Context ctx) {
        RelacaoService relacaoService = ctx.appData(Keys.AMIZADE_SERVICE.key());
        String id = ctx.pathParam("id");
        if (id.matches("\\d+")) {
            Integer idAmizade = Integer.parseInt(id);
            relacaoService.aceitarRelacao(idAmizade);
        } else {
            ctx.status(400).json(new Mensagem("Id não é válido", false));
        }
    }

    public static void buscarRelacoessNaoAceitas(Context ctx) {
        RelacaoService relacaoService = ctx.appData(Keys.AMIZADE_SERVICE.key());
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            List<Relacao> listaRelacao = relacaoService.RelacoesNaoAceitas(id);
            ctx.status(200).json(listaRelacao);
        } catch (NumberFormatException e) {
            logger.error("Parâmetro inválido: " + e);
            ctx.status(400).json(new Mensagem("Parâmetro inválido", false));
        }
    }

}
