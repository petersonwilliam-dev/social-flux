package com.backend.controllers;

import com.backend.Mensagem;
import com.backend.util.AuthMiddleware;
import com.backend.util.ImagemUtil;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class ImagemController {

    private static final Logger logger = LogManager.getLogger();

    public static void buscarImagem(Context ctx) {

        String id_usuario = ctx.pathParam("id_usuario");
        String id_imagem = ctx.pathParam("id_imagem");

        File imagem = new File("src/main/resources/imagens/"+id_usuario+"/"+id_imagem);
        try {
            String extensao = ImagemUtil.buscarExtensao(imagem);
            ctx.contentType("image/"+extensao);
            ctx.result(Files.newInputStream(imagem.toPath()));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(new Mensagem(e.getMessage(), false));
        } catch (IOException e) {
            logger.error("Erro ao retornar imagem: " + e);
            ctx.status(500).json(new Mensagem("Erro ao retornar imagem", false));
        }
    }


}
