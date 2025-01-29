package com.backend.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Curtida {

    private Integer id;
    private Integer id_usuario;
    private Integer id_conteudo;

    public Curtida() {}

    public Curtida(Integer id, Integer id_usuario, Integer id_conteudo) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_conteudo = id_conteudo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_conteudo() {
        return id_conteudo;
    }

    public void setId_conteudo(Integer id_conteudo) {
        this.id_conteudo = id_conteudo;
    }

    @Override
    public String toString() {
        return "Curtida{" +
                "id=" + id +
                ", id_usuario=" + id_usuario +
                ", id_conteudo=" + id_conteudo +
                '}';
    }
}
