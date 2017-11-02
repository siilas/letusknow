package br.com.silas.letusknow.model;

public class Enviar {

    private String message = "";
    private boolean sucesso = false;

    public void sucesso() {
        this.sucesso = true;
    }

    public void erro(String message) {
        this.sucesso = false;
        this.message = message;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public String getMessage() {
        return message;
    }

}
