package com.backend.controllers;

import com.backend.Keys;
import com.backend.Mensagem;
import com.backend.model.entities.Postagem;
import com.backend.model.entities.Usuario;
import com.backend.services.PostagemService;
import com.backend.util.ImagemUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class PostagemController {

    private static final Logger logger = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void criarPostagem(Context ctx) {

        PostagemService postagemService = ctx.appData(Keys.POSTAGEM_SERVICE.key());

        try {
            UploadedFile uploadedFile = ctx.uploadedFile("imagem");
            Usuario usuario = mapper.readValue(ctx.formParam("usuario"), Usuario.class);
            String legenda = ctx.formParam("legenda");
            String id_postagem_form = ctx.formParam("id_postagem");
            Integer id_postagem = (id_postagem_form != null) ? Integer.parseInt(id_postagem_form) : null;



            if (uploadedFile != null && legenda != null && !legenda.trim().isEmpty() && usuario != null) {
                try {
                    String nomeArquivo = ImagemUtil.inserirImagem(uploadedFile, usuario.getId());
                    Postagem postagem = new Postagem();
                    postagem.setLegenda(legenda);
                    postagem.setImagem(nomeArquivo);
                    postagem.setUsuario(usuario);
                    postagem.setData_postagem(LocalDateTime.now());
                    postagemService.criarPostagem(postagem, id_postagem);
                    ctx.status(201);
                } catch (IllegalArgumentException e) {
                    logger.error("Erro ao armazenar imagem: " + e);
                    ctx.status(500).json(new Mensagem(e.getMessage(), false));
                } catch (IOException e) {
                    logger.error("Erro ao armazenar imagem: " + e);
                    ctx.status(500).json(new Mensagem("Erro interno no sistema", false));
                }
            } else if (legenda != null && !legenda.trim().isEmpty() && usuario != null) {
                Postagem postagem = new Postagem();
                postagem.setUsuario(usuario);
                postagem.setLegenda(legenda);
                postagem.setData_postagem(LocalDateTime.now());
                postagemService.criarPostagem(postagem, id_postagem);
                ctx.status(201);
            } else {
                ctx.status(400).json(new Mensagem("Não é possível criar uma postagem vazia, coloque uma imagem, ou um texto", false));
            }
        } catch (Exception e) {
            logger.error("Erro ao criar postagem: " + e);
            ctx.status(500).json(new Mensagem("Erro interno no sistema ao ler dados", false));
        }
    }

    public static void buscarPostagensDoUsuario(Context ctx) {
        PostagemService postagemService = ctx.appData(Keys.POSTAGEM_SERVICE.key());

        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Boolean allPosts = Boolean.parseBoolean(ctx.queryParam("allPosts"));
            Boolean mediaPosts = Boolean.parseBoolean(ctx.queryParam("mediaPosts"));

            if (allPosts != null && allPosts) {
                List<Postagem> listaPostagem = postagemService.buscarPostsInteracoesUsuario(id);
                ctx.status(200).json(listaPostagem);
            } else if (mediaPosts != null && mediaPosts) {
                List<Postagem> listaPostagem = postagemService.buscarPostagemComMidiaUsuario(id);
                ctx.status(200).json(listaPostagem);
            } else {
                List<Postagem> listaPostagem = postagemService.buscarPostagensDoUsuario(id);
                ctx.status(200).json(listaPostagem);
            }

        } catch (NumberFormatException e) {
            logger.error("ID inválido");
            ctx.status(400).json(new Mensagem("Parâmetro inválido, verifique o id", false));
        }
    }

    public static void buscarPostagens(Context ctx) {

        PostagemService postagemService = ctx.appData(Keys.POSTAGEM_SERVICE.key());

        try {
            Integer id_seguidor = Integer.parseInt(ctx.queryParam("id_seguidor"));
            List<Postagem> listaPostagem = postagemService.buscarPostagensSeguidosUsuario(id_seguidor);
            ctx.status(200).json(listaPostagem);
        } catch (NumberFormatException e) {
            logger.info("ID inválido");
            ctx.status(400).json(new Mensagem("ID inválido", false));
        }
    }

    public static void buscarNumeroRespostas(Context ctx) {

        PostagemService postagemService = ctx.appData(Keys.POSTAGEM_SERVICE.key());

        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));

            Integer numeroRespostas = postagemService.buscarNumeroRespostas(id);
            ctx.status(200).json(numeroRespostas);
        } catch (NumberFormatException e) {
            logger.info("Erro: Parâmetro ID inválido: " + e);
            ctx.status(400).json(new Mensagem("ID inválido", false));
        }
    }

    public static void buscarPostagemPorId(Context ctx) {

        PostagemService postagemService = ctx.appData(Keys.POSTAGEM_SERVICE.key());

        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Postagem postagem = postagemService.buscarPostagemPorId(id);
            ctx.status(200).json(postagem);
        } catch (NumberFormatException e) {
            logger.info("Erro: Parâmetro ID inválido: " + e);
            ctx.status(400).json(new Mensagem("ID inválido", false));
        }
    }

    public static void buscarRespostasPostagem(Context ctx) {

        PostagemService postagemService = ctx.appData(Keys.POSTAGEM_SERVICE.key());

        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));

            List<Postagem> listaPostagem = postagemService.buscarRespostasPostagem(id);
            ctx.status(200).json(listaPostagem);
        } catch (NumberFormatException e) {
            logger.info("Erro: Parâmetro ID inválido: " + e);
            ctx.status(400).json(new Mensagem("ID inválido", false));
        }
    }

    public static void excluirPostagem(Context ctx) {
        PostagemService postagemService = ctx.appData(Keys.POSTAGEM_SERVICE.key());

        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            postagemService.excluirPostagem(id);
            ctx.status(200);
        } catch (NumberFormatException e) {
            logger.info("ID inválido");
            ctx.status(400).json(new Mensagem("ID inválido, verifique os dados", false));
        }
    }

}
