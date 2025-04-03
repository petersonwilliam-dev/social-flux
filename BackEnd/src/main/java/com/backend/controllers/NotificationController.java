package com.backend.controllers;

import com.backend.Keys;
import com.backend.Mensagem;
import com.backend.model.entities.Notification;
import com.backend.model.entities.Postagem;
import com.backend.model.entities.Usuario;
import com.backend.model.enums.NotificationCategory;
import com.backend.services.NotificationService;
import com.backend.services.PostagemService;
import com.backend.services.UsuarioService;
import com.backend.util.AuthMiddleware;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class NotificationController {

    private static final Logger logger = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void createNotification(Context ctx) {

        AuthMiddleware.AuthValidate(ctx);

        NotificationService notificationService = ctx.appData(Keys.NOTIFICATION_SERVICE.key());

        try {
            Map<String, Object> json = mapper.readValue(ctx.body(), Map.class);
            Usuario sender = mapper.readValue((String) json.get("sender"), Usuario.class);
            Usuario receiver = mapper.readValue((String) json.get("receiver"), Usuario.class);
            Notification notification = new Notification();
            notification.setCategory((String)json.get("category"));
            notification.setTitle((String)json.get("title"));
            notification.setDescription((String)json.get("description"));
            notification.setId_post((Integer) json.get("id_post"));
            notification.setSender(sender);
            notification.setReceiver(receiver);

            notificationService.insertNotification(notification);
        } catch (JsonProcessingException e) {
            logger.error(e);
            ctx.status(500);
        }
    }

    public static void getUserNotifications(Context ctx) {

        AuthMiddleware.AuthValidate(ctx);

        NotificationService notificationService = ctx.appData(Keys.NOTIFICATION_SERVICE.key());

        try {
            Integer userId = Integer.parseInt(ctx.queryParam("userId"));
            boolean count = Boolean.parseBoolean(ctx.queryParam("count"));

            if (count) {
                Integer countNotificationsNotSeen = notificationService.notificationNotSeen(userId);
                ctx.status(200).json(countNotificationsNotSeen);
            } else {
                List<Notification> notificationList = notificationService.getUserNotifications(userId);
                ctx.status(200).json(notificationList);
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensagem("Parâmetro inválido", false));
        }
    }

    public static void viewNotifications(Context ctx) {

        AuthMiddleware.AuthValidate(ctx);

        NotificationService notificationService = ctx.appData(Keys.NOTIFICATION_SERVICE.key());

        try {
            int id_receiver = Integer.parseInt(ctx.pathParam("id_receiver"));

            notificationService.viewNotifications(id_receiver);
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensagem("ID inválido", false));
        }
    }

}
