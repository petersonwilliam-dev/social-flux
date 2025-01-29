package com.backend;

public class Mensagem {

    private String mensagem;
    private boolean sucesso;

    public Mensagem(String erro, boolean sucesso) {
        this.mensagem = erro;
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean getSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }
}
