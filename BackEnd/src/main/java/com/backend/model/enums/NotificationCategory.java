package com.backend.model.enums;

public enum NotificationCategory {

    LIKE(1, "LIKE"),
    COMENT(2, "COMENT"),
    FOLLOW(3, "FOLLOW"),
    GENERAL(4, "GENERAL");

    private Integer id;
    private String category;

    NotificationCategory(Integer id, String category) {
        this.id = id;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public static NotificationCategory fromId(Integer id) {
        for (NotificationCategory category : NotificationCategory.values()) {
            if (category.getId() == id) {
                return category;
            }
        }
        throw new IllegalArgumentException("ID Inválido");
    }
}
