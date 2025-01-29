package com.backend.model.entities;

public class Relacao {

    private Integer id;
    private Usuario seguidor, seguido;
    private Boolean aceito;

    public Relacao() {}

    public Relacao(Integer id, Usuario seguidor, Usuario seguido, Boolean aceito) {
        this.id = id;
        this.seguidor = seguidor;
        this.seguido = seguido;
        this.aceito = aceito;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getSeguidor() {
        return seguidor;
    }

    public void setSeguidor(Usuario seguidor) {
        this.seguidor = seguidor;
    }

    public Usuario getSeguido() {
        return seguido;
    }

    public void setSeguido(Usuario seguido) {
        this.seguido = seguido;
    }

    public Boolean getAceito() {
        return aceito;
    }

    public void setAceito(Boolean aceito) {
        this.aceito = aceito;
    }
}
