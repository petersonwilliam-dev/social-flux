package com.backend.model.entities;

import java.time.LocalDateTime;

public class Postagem {

    private Integer id;
    private String imagem, legenda;
    private LocalDateTime data_postagem;
    private Usuario usuario;
    private Postagem postagem;

    public Postagem() {}

    public Postagem(Integer id, String imagem, String legenda, LocalDateTime data_postagem, Usuario usuario, Postagem postagem) {
        this.id = id;
        this.imagem = imagem;
        this.legenda = legenda;
        this.data_postagem = data_postagem;
        this.usuario = usuario;
        this.postagem = postagem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public LocalDateTime getData_postagem() {
        return data_postagem;
    }

    public void setData_postagem(LocalDateTime data_postagem) {
        this.data_postagem = data_postagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Postagem getPostagem() {
        return postagem;
    }

    public void setPostagem(Postagem postagem) {
        this.postagem = postagem;
    }
}
