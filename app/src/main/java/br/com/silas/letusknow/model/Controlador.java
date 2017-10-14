package br.com.silas.letusknow.model;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class Controlador {

    private Integer questaoAtual;
    private List<Questao> questoes;

    public Controlador() {
        questaoAtual = 0;
    }

    public Questao getQuestaoAtual() {
        return questoes.get(questaoAtual);
    }

    public List<Questao> getQuestoes() {
        if (questoes == null) {
            questoes = new ArrayList<>();
        }
        return questoes;
    }

    public boolean deveConcluir() {
        return (questaoAtual + 1) >= questoes.size();
    }

    public Questao getProximaQuestao() {
        questaoAtual++;
        return getQuestaoAtual();
    }

    public Questao setResposta(View view) {
        Questao questao = getQuestaoAtual();
        EditText resposta = (EditText) view;
        if (resposta != null) {
            questao.setResposta(String.valueOf(resposta.getText()));
        }
        return questao;
    }

}
