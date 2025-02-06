package com.backend.controllers;

import com.backend.Mensagem;
import com.backend.Keys;
import com.backend.model.entities.Usuario;
import com.backend.services.UsuarioService;
import com.backend.util.ImagemUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class UsuarioController {

    private static final Logger logger = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void buscarUsuario(Context ctx) {
        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());
        String username = ctx.queryParam("username");
        if (username != null) {
            Usuario usuario = usuarioService.buscarPorUsername(username);
            if (usuario != null) {
                ctx.json(usuario);
            } else {
                ctx.status(404).json(new Mensagem("Usuário não encontrado", false));
            }
        } else {
            ctx.status(400);
        }
    }

    public static void atualizarFoto(Context ctx) {

        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());

        UploadedFile uploadedFile = ctx.uploadedFile("foto_perfil");
        String foto_antiga = ctx.formParam("foto_antiga");

        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            BufferedImage imagemRedimensionada = ImagemUtil.redimensionarImagem(uploadedFile, 200, 200);
            String extensaoArquivo = ImagemUtil.buscarExtensao(uploadedFile);
            if (ImagemUtil.formatoPermitido(uploadedFile)) {
                String nomeArquivo = ImagemUtil.inserirImagem(imagemRedimensionada, id, extensaoArquivo);
                if (foto_antiga != null) {
                    ImagemUtil.removerImagem(id, foto_antiga);
                }
                usuarioService.atualizarFotoPerfil(id, nomeArquivo);
                Usuario usuario = usuarioService.buscarPorId(id);
                ctx.status(200).json(usuario);
            } else {
                ctx.status(400).json(new Mensagem("Formato de imagem não suportado", false));
            }

        } catch (NumberFormatException e) {
            logger.info("Uma requisição com ID inválido: " + e);
            ctx.status(400).json(new Mensagem("ID inválido, verifique o dado enviado na requisição", false));
        } catch (IllegalArgumentException e) {
            logger.info("Uma requisição com arquivo inválido no sistema: " + e);
            ctx.status(400).json(new Mensagem(e.getMessage(), false));
        } catch (IOException e) {
            logger.error("Erro ao transferir imagem pro diretório: " + e);
            ctx.status(500).json(new Mensagem("Erro ao transferir imagem pro diretório", false));
        } catch (Exception e) {
            logger.error("Erro interno no sistema" + e);
            ctx.status(500).json(new Mensagem("Erro interno no sistema", false));
        }
    }

    public static void atualizarDadosPerfil(Context ctx) {
        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());

        try {
            Usuario usuarioAtualizado = mapper.readValue(ctx.body(), Usuario.class);
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            if (usuarioService.usernameExistenrte(usuarioAtualizado.getUsername())) {
                ctx.status(409).json(new Mensagem("Username já está em uso, tente outro!", false));

            } else {
                usuarioService.atualizarDadosPerfil(usuarioAtualizado);
                Usuario usuario = usuarioService.buscarPorId(id);
                ctx.status(200).json(usuario);
            }
        } catch (Exception e) {
            logger.error(e);
            ctx.status(500).json(new Mensagem("Erro interno no sistema", false));
        }
    }

    public static void buscarUsuariosPesquisados(Context ctx) {
        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());

        String search = ctx.queryParam("search");
        if (search != null) {
            List<Usuario> usuarios = usuarioService.buscarUsuariosPesquisados(search);
            ctx.status(200).json(usuarios);
        }
    }

    public static void buscarRelacoesEmComum(Context ctx) {
        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());

        try {
            Integer id = Integer.parseInt(ctx.queryParam("id"));
            Integer id_usuario_perfil = Integer.parseInt(ctx.queryParam("id_usuario_perfil"));

            List<Usuario> listaUsuarios = usuarioService.buscarRelacoesEmComum(id, id_usuario_perfil);
            ctx.status(200).json(listaUsuarios);
        } catch (NumberFormatException e) {
            logger.info("ID inválido ao buscar relações em comum:" + e);
            ctx.status(400).json(new Mensagem("ID inválido" ,false));
        }
    }

    private static boolean verificaDados(Usuario usuario) {
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) return false;
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) return false;
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) return false;
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) return false;
        if (usuario.getTelefone() == null || usuario.getTelefone().trim().isEmpty()) return false;
        if (usuario.getSexo() == null) return false;
        if (usuario.getData_nascimento() == null) return false;
        return true;
    }
}
