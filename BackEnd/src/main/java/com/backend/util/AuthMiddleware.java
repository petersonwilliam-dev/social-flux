package com.backend.util;

import com.backend.Mensagem;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthMiddleware {

    private static final Logger logger = LogManager.getLogger();

    public static void AuthValidate(Context ctx) {

        String bearerToken = ctx.header("Authorization");

        try {
            if (!JWT.validateToken(bearerToken)) {
                ctx.status(401).json(new Mensagem("Token não fornecido", false));
                throw new UnauthorizedResponse();
            }
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedResponse("Token expirado!");
        } catch (SignatureException e) {
            throw new UnauthorizedResponse("Token inválido!");
        } catch (MalformedJwtException e) {
            throw new UnauthorizedResponse("Token mal formado");
        } catch (UnauthorizedResponse e) {
            throw new UnauthorizedResponse("Não autorizado!");
        } catch (Exception e) {
            logger.error("Erro não tratado: " + e);
            ctx.status(500).json(new Mensagem("Erro não tratado!" + e, false));
            throw new RuntimeException();
        }

    }
}
