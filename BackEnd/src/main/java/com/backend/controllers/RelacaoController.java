package com.backend.controllers;

import com.backend.Mensagem;
import com.backend.Keys;
import com.backend.model.entities.Amizade;
import com.backend.services.AmizadeService;
import com.backend.model.entities.Usuario;
import com.backend.services.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RelacaoController {

    private static final Logger logger = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void criarRelacao(Context ctx) {
        AmizadeService amizadeService = ctx.appData(Keys.AMIZADE_SERVICE.key());

        Map<String, Object> dados = ctx.bodyAsClass(Map.class);
        Map<String, Object> seguidorJson = (Map<String, Object>) dados.get("user");
        Map<String, Object> seguidoJson = (Map<String, Object>) dados.get("profileUser");

        try {
            Usuario seguidor = mapper.convertValue(seguidorJson, Usuario.class);
            Usuario seguido = mapper.convertValue(seguidoJson, Usuario.class);

            Amizade amizade = new Amizade();
            amizade.setSeguidor(seguidor);
            amizade.setSeguido(seguido);
            amizade.setAceito(!seguido.getPrivado());

            if (!amizadeService.amizadeExiste(seguidor.getId(), seguido.getId())) {
                amizade.setId(amizadeService.criarAmizade(amizade));
                ctx.status(201).json(amizade);
            } else {
                ctx.status(409).json(new Mensagem("Você já possui amizade com esse usuário", false));
            }
        } catch (IllegalArgumentException e) {
            logger.error(e);
            ctx.status(500).json(new Mensagem("Erro interno ao processar requisição com JSON", false));
        }
    }

    public static void buscarAmizades(Context ctx) {
        AmizadeService amizadeService = ctx.appData(Keys.AMIZADE_SERVICE.key());

        String id_seguidor = ctx.queryParam("id_seguidor");
        String id_seguido = ctx.queryParam("id_seguido");
        try {
            if (id_seguidor != null && id_seguido != null) {
                Integer idSeguidor = Integer.parseInt(id_seguidor);
                Integer idSeguido = Integer.parseInt(id_seguido);

                Amizade amizade = amizadeService.buscarAmizade(idSeguidor, idSeguido);
                if (amizade != null) {
                    ctx.status(200).json(amizade);
                }
            } else if (id_seguido != null) {
                Integer idSeguido = Integer.parseInt(id_seguido);
                List<Amizade> listaAmizade = amizadeService.seguidoresDoUsuario(idSeguido);
                ctx.status(200).json(listaAmizade);
            } else if (id_seguidor != null) {
                Integer idSeguidor = Integer.parseInt(id_seguidor);
                List<Amizade> listaAmizade = amizadeService.seguidosDoUsuario(idSeguidor);
                ctx.status(200).json(listaAmizade);
            } else {
                ctx.status(400).json(new Mensagem("Parâmetros nulos", false));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensagem("Parâmetros inválidos!", false));
        }
    }

    public static void numeroSeguidores(Context ctx) {
        AmizadeService amizadeService = ctx.appData(Keys.AMIZADE_SERVICE.key());
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Integer numeroSeguidores = amizadeService.numeroSeguidores(id);
            ctx.status(200).json(numeroSeguidores);
        } catch (NumberFormatException e) {
            logger.error("Id inválido");
            ctx.status(400).json(new Mensagem("Id inválido", false));
        }
    }

    public static void numeroSeguidos(Context ctx) {
        AmizadeService amizadeService = ctx.appData(Keys.AMIZADE_SERVICE.key());
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Integer numeroSeguidos = amizadeService.numeroSeguidos(id);
            ctx.status(200).json(numeroSeguidos);
        } catch (NumberFormatException e) {
            logger.error("Id inválido");
            ctx.status(400).json(new Mensagem("Id inválido", false));
        }
    }

    public static void removerAmizade(Context ctx) {
        AmizadeService amizadeService = ctx.appData(Keys.AMIZADE_SERVICE.key());
        String id = ctx.pathParam("id");
        if (id.matches("\\d+")) {
            Integer idAmizade = Integer.parseInt(id);
            amizadeService.removerAmizade(idAmizade);
        } else {
            ctx.status(400).json(new Mensagem("Id não é válido", false));
        }
    }

    public static void aceitarAmizade(Context ctx) {
        AmizadeService amizadeService = ctx.appData(Keys.AMIZADE_SERVICE.key());
        String id = ctx.pathParam("id");
        if (id.matches("\\d+")) {
            Integer idAmizade = Integer.parseInt(id);
            amizadeService.aceitarAmizade(idAmizade);
        } else {
            ctx.status(400).json(new Mensagem("Id não é válido", false));
        }
    }

    public static void buscarAmizadesNaoAceitas(Context ctx) {
        AmizadeService amizadeService = ctx.appData(Keys.AMIZADE_SERVICE.key());
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            List<Amizade> listaAmizade = amizadeService.amizadesNaoAceitas(id);
            ctx.status(200).json(listaAmizade);
        } catch (NumberFormatException e) {
            logger.error("Parâmetro inválido: " + e);
            ctx.status(400).json(new Mensagem("Parâmetro inválido", false));
        }
    }

}
