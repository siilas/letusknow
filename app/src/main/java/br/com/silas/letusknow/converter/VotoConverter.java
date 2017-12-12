package br.com.silas.letusknow.converter;

import java.util.List;

import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.model.Resposta;
import br.com.silas.letusknow.model.Voto;
import br.com.silas.letusknow.model.Votos;

public class VotoConverter {

    public static Votos converter(List<Questao> questoes) {
        Votos votos = new Votos();
        for (Questao questao : questoes) {
            for (Resposta resposta : questao.getRespostas()) {
                Voto voto = new Voto();
                voto.setIdQuestao(questao.getId());
                voto.setIdResposta(resposta.getId());
                votos.getVotos().add(voto);
            }
        }
        return votos;
    }

}
