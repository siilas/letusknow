package br.com.silas.letusknow.converter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.silas.letusknow.model.Resposta;

public class RespostaConverter {

    public static List<Resposta> converter(String json) {
        List<Resposta> respostas = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Resposta resposta = new Resposta();
                resposta.setId(object.getInt("id"));
                resposta.setDescricao(object.getString("descricao"));
                resposta.setSelecionada(false);
                resposta.setVotos(object.getInt("votos"));
                respostas.add(resposta);
            }
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao converter respostas", e);
        }
        return respostas;
    }

}
