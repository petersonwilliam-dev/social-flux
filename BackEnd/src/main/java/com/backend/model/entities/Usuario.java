package com.backend.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario {

    private Integer id;
    private String username, nome, senha, biografia, email, telefone, foto_perfil;
    private LocalDate data_criacao, data_nascimento;
    private Boolean privado;
    private Character sexo;

    public Usuario() {}

    public Usuario(Integer id, String username, String nome, String email, String senha, String biografia, String telefone, String foto_perfil, LocalDate data_criacao, LocalDate data_nascimento, Boolean privado, Character sexo) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.biografia = biografia;
        this.telefone = telefone;
        this.foto_perfil = foto_perfil;
        this.data_criacao = data_criacao;
        this.data_nascimento = data_nascimento;
        this.privado = privado;
        this.sexo = sexo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public LocalDate getData_criacao() {
        return data_criacao;
    }

    public Boolean getPrivado() {
        return privado;
    }

    public void setPrivado(Boolean privado) {
        this.privado = privado;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }
}
