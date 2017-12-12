package br.com.silas.letusknow.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.silas.letusknow.converter.QuestaoConverter;
import br.com.silas.letusknow.dao.QuestionarioDao;
import br.com.silas.letusknow.dao.RespostaDao;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.model.Resposta;
import br.com.silas.letusknow.utils.PropertiesUtils;
import br.com.silas.letusknow.ws.LetUsKnowWs;

public class BuscarService {

    private PropertiesUtils properties;

    public BuscarService(Context context) {
        properties = new PropertiesUtils(context);
    }

    public void buscar(SQLiteDatabase db) {
        try {
            BuscarAPI buscarAPI = new BuscarAPI();
            String response = buscarAPI.execute().get();

            List<Questao> questoes = QuestaoConverter.converter(response);

            for (Questao questao : questoes) {
                QuestionarioDao.inserirQuestao(db, questao);
                for (Resposta resposta : questao.getRespostas()) {
                    resposta.setQuestao(questao.getId());
                }
                RespostaDao.inserirResposta(db, questao.getRespostas());
            }
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao buscar questões", e);
        }
    }

    class BuscarAPI extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... parameters) {
            try {
                return LetUsKnowWs.criar()
                        .rootUrl(properties.get("root.url"))
                        .autenticar(properties.get("ws.user"), properties.get("ws.pass"))
                        .get("/ws/questao/buscar");
            } catch (Exception e) {
                Log.e("LetUsKnow", "Erro a buscar informações", e);
            }
            return null;
        }

    }

}
