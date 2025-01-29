package com.backend.model.entities;

import com.backend.model.enums.NotificationCategory;

public class Notification {

    private Integer id;
    private Usuario sender, receiver;
    private String title, description;
    private NotificationCategory category;
    private Integer id_post;
    private Boolean viewed;

    public Notification() {}
    
    public Notification(Integer id, Usuario sender, Usuario receiver, String title, String description, NotificationCategory category, Integer id_post, Boolean viewed) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.description = description;
        this.category = category;
        this.id_post = id_post;
        this.viewed = viewed;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NotificationCategory getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = NotificationCategory.valueOf(category);
    }

    public Usuario getSender() {
        return sender;
    }

    public void setSender(Usuario sender) {
        this.sender = sender;
    }

    public Usuario getReceiver() {
        return receiver;
    }

    public void setReceiver(Usuario receiver) {
        this.receiver = receiver;
    }

    public Integer getId_post() {
        return id_post;
    }

    public void setId_post(Integer id_post) {
        this.id_post = id_post;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }

    /*private static void checkData(Integer id, Usuario sender, String title, String description, NotificationCategory category) {
        if (id == null) throw new IllegalArgumentException();
        if (sender == null) throw new IllegalArgumentException();
        if (title == null || title.isEmpty()) throw new IllegalArgumentException();
        if (description == null || description.isEmpty()) throw new IllegalArgumentException();
        if (category == null) throw new IllegalArgumentException();
    }*/

}
