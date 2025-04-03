package com.backend.controllers;

import com.backend.Keys;
import com.backend.Mensagem;
import com.backend.model.entities.Postagem;
import com.backend.model.entities.Usuario;
import com.backend.services.PostagemService;
import com.backend.util.AuthMiddleware;
import com.backend.util.ImagemUtil;
import com.backend.util.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
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

        AuthMiddleware.AuthValidate(ctx);

        try {

            String bearerToken = ctx.header("Authorization");
            String userJson = JWT.getToken(bearerToken);
            Usuario usuario = mapper.readValue(userJson, Usuario.class);

            UploadedFile uploadedFile = ctx.uploadedFile("imagem");
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

    public static void buscarPostagens(Context ctx) {

        PostagemService postagemService = ctx.appData(Keys.POSTAGEM_SERVICE.key());

        AuthMiddleware.AuthValidate(ctx);

        try {

            String id_user = ctx.queryParam("id_user");
            String id_seguidor = ctx.queryParam("id_seguidor");
            String id_postagem = ctx.queryParam("id_postagem");

            if (id_user != null) {
                Integer idUser = Integer.parseInt(id_user);
                Boolean allPosts = Boolean.parseBoolean(ctx.queryParam("allPosts"));
                Boolean mediaPosts = Boolean.parseBoolean(ctx.queryParam("mediaPosts"));
                if (allPosts) {
                    List<Postagem> listaPostagens = postagemService.buscarPostsInteracoesUsuario(idUser);
                    ctx.status(200).json(listaPostagens);
                } else if (mediaPosts) {
                    List<Postagem> listaPostagens = postagemService.buscarPostagemComMidiaUsuario(idUser);
                    ctx.status(200).json(listaPostagens);
                } else {
                    List<Postagem> listaPostagens = postagemService.buscarPostagensDoUsuario(idUser);
                    ctx.status(200).json(listaPostagens);
                }
            } else if (id_postagem != null) {
                Integer idPostagem = Integer.parseInt(id_postagem);
                Boolean responses = Boolean.parseBoolean(ctx.queryParam("responses"));
                if (responses) {

                    List<Postagem> respostasDaPostagem = postagemService.buscarRespostasPostagem(idPostagem);
                    ctx.status(200).json(respostasDaPostagem);
                } else {
                    Postagem postagem = postagemService.buscarPostagemPorId(idPostagem);
                    ctx.status(200).json(postagem);
                }
            } else {
                Integer idSeguidor = Integer.parseInt(id_seguidor);
                List<Postagem> listaPostagem = postagemService.buscarPostagensSeguidosUsuario(idSeguidor);
                ctx.status(200).json(listaPostagem);
            }

        } catch (NumberFormatException e) {
            logger.info("ID inválido: " + e);
            ctx.status(400).json(new Mensagem("ID inválido", false));
        } catch (Exception e) {
            logger.info("Excessão não tratada: " + e);
            ctx.status(500).json(new Mensagem("Excessão não tratada", false));
        }
    }

    public static void buscarPostagemPorId(Context ctx) {

        PostagemService postagemService = ctx.appData(Keys.POSTAGEM_SERVICE.key());

        AuthMiddleware.AuthValidate(ctx);

        try {

            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Postagem postagem = postagemService.buscarPostagemPorId(id);
            ctx.status(200).json(postagem);

        } catch (NumberFormatException e) {
            logger.info("ID inválido: " + e);
            ctx.status(400).json(new Mensagem("ID inválido", false));
        } catch (Exception e) {
            logger.info("Excessão não tratada: " + e);
            ctx.status(500).json(new Mensagem("Excessão não tratada", false));
        }
    }

    public static void excluirPostagem(Context ctx) {
        PostagemService postagemService = ctx.appData(Keys.POSTAGEM_SERVICE.key());

        AuthMiddleware.AuthValidate(ctx);

        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Postagem postagem = postagemService.buscarPostagemPorId(id);
            if (postagem.getImagem() != null) {
                ImagemUtil.removerImagem(postagem.getUsuario().getId(), postagem.getImagem());
            }
            postagemService.excluirPostagem(id);
            ctx.status(200);
        } catch (NumberFormatException e) {
            logger.info("ID inválido: " + e);
            ctx.status(400).json(new Mensagem("ID inválido", false));
        } catch (Exception e) {
            logger.info("Excessão não tratada: " + e);
            ctx.status(500).json(new Mensagem("Excessão não tratada", false));
        }
    }

}
