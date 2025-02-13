package com.backend.controllers;

import com.backend.Mensagem;
import com.backend.Keys;
import com.backend.model.entities.Relacao;
import com.backend.services.RelacaoService;
import com.backend.model.entities.Usuario;
import com.backend.services.UsuarioService;
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
        Boolean pending = Boolean.parseBoolean(ctx.queryParam("pending"));
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
                if (pending) {
                    List<Relacao> listaRelacoesNaoAceitas = relacaoService.RelacoesNaoAceitas(idSeguido);
                    ctx.status(200).json(listaRelacoesNaoAceitas);
                } else {
                    List<Relacao> listaRelacao = relacaoService.seguidoresDoUsuario(idSeguido);
                    ctx.status(200).json(listaRelacao);
                }
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

    public static void buscarRelacoesEmComum(Context ctx) {
        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());

        try {
            Integer id = Integer.parseInt(ctx.queryParam("id_usuario"));
            Integer id_usuario_perfil = Integer.parseInt(ctx.queryParam("id_usuario_perfil"));

            List<Usuario> listaUsuarios = usuarioService.buscarRelacoesEmComum(id, id_usuario_perfil);
            ctx.status(200).json(listaUsuarios);
        } catch (NumberFormatException e) {
            logger.info("ID inválido ao buscar relações em comum:" + e);
            ctx.status(400).json(new Mensagem("ID inválido" ,false));
        }
    }


}
