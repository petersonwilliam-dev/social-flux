package com.backend.controllers;

import com.backend.Keys;
import com.backend.Mensagem;
import com.backend.model.entities.Usuario;
import com.backend.services.UsuarioService;
import com.backend.util.JWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class AuthController {

    private static final Logger logger = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void registerUser(Context ctx) {

        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());

        try {
            Usuario usuario = mapper.readValue(ctx.body(), Usuario.class);
            if (verificaDados(usuario)) {
                if (!usuarioService.emailExistente(usuario.getEmail())) {
                    if (!usuarioService.usernameExistenrte(usuario.getUsername())) {
                        usuarioService.inserir(usuario);
                        Usuario usuarioResposta = usuarioService.buscarPorEmail(usuario.getEmail());
                        usuarioResposta.setSenha(null);
                        String userJson = mapper.writeValueAsString(usuarioResposta);
                        String userJwt = JWT.generateJwt(userJson);
                        ctx.status(201).json(userJwt);
                    } else {
                        ctx.status(400).json(new Mensagem("Username já cadastrado, tente outro!", false));
                    }
                } else {
                    ctx.status(400).json(new Mensagem("Email existente, tente outro", false));
                }
            } else {
                ctx.status(400).json(new Mensagem("Todos os dados devem ser preenchidos, verifique se há algum campo em branco!", false));
            }
        } catch (JsonProcessingException e) {
            logger.error("Erro ao processar JSON: " + e);
            ctx.status(500).json(new Mensagem("Erro interno ao processar dados, aguarde para tentar novamente!", false));
        }
    }

    public static void authenticateUser(Context ctx) {
        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());

        try {
            Usuario userData = mapper.readValue(ctx.body(), Usuario.class);
            Usuario usuario = usuarioService.buscarPorEmail(userData.getEmail());
            if (usuario != null) {
                if (BCrypt.checkpw(userData.getSenha(), usuario.getSenha())) {
                    usuario.setSenha(null);
                    String userJson = mapper.writeValueAsString(usuario);
                    String userJwt = JWT.generateJwt(userJson);
                    ctx.status(200).json(userJwt);
                } else {
                    ctx.status(401).json(new Mensagem("Senha incorreta, tente novamente!", false));
                }
            } else {
                ctx.status(404).json(new Mensagem("Usuário não foi encontrado, verifique o email", false));
            }
        } catch (JsonProcessingException e) {
            logger.error("Erro ao processar JSON: " + e);
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
