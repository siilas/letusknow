package br.com.silas.letusknow.model;

import java.util.ArrayList;
import java.util.List;

import br.com.silas.letusknow.utils.ListUtils;

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

    public void addQuestoes(List<Questao> questoes) {
        getQuestoes().addAll(questoes);
    }

    public boolean isNotEmpty() {
        return ListUtils.isNotEmpty(getQuestoes());
    }

}
