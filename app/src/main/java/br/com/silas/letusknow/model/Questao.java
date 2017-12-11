package br.com.silas.letusknow.model;

import java.util.ArrayList;
import java.util.List;

public class Questao {

    private Integer id;
    private String descricao;
    private List<Resposta> respostas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Resposta> getRespostas() {
        if (respostas == null) {
            respostas = new ArrayList<>();
        }
        return respostas;
    }

    public void setRespostas(List<Resposta> resposta) {
        this.respostas = resposta;
    }

}
