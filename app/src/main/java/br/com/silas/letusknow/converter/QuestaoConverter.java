package br.com.silas.letusknow.converter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.silas.letusknow.model.Questao;

public class QuestaoConverter {

    public static List<Questao> converter(String json) {
        List<Questao> questoes = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Questao questao = new Questao();
                questao.setId(object.getInt("id"));
                questao.setDescricao(object.getString("descricao"));
                questao.getRespostas().addAll(RespostaConverter.converter(object.getString("respostas")));
                questoes.add(questao);
            }
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao converter questões", e);
        }
        return questoes;
    }

}
