package com.backend.usuario.model;

import java.time.LocalDate;

public class Usuario {

    private Integer id;
    private String nome_usuario;
    private String senha;
    private String email;
    private String nome;
    private String sobrenome;
    private String telefone;
    private LocalDate data_nascimento;
    private Character sexo;

    public Usuario() {}

    public Usuario(Integer id, String nome_usuario, String senha, String email, String nome, String sobrenome, String telefone, LocalDate data_nascimento, Character sexo) {
        this.id = id;
        this.nome_usuario = nome_usuario;
        this.senha = senha;
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
    }

    public Integer getId() {
        return id;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
}
