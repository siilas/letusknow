package br.com.silas.letusknow.service;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.silas.letusknow.converter.QuestaoConverter;
import br.com.silas.letusknow.dao.QuestionarioDao;
import br.com.silas.letusknow.dao.RespostaDao;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.model.Resposta;
import br.com.silas.letusknow.ws.LetUsKnowWs;

public class BuscarService {

    public void buscar(SQLiteDatabase db) {
        try {
            BuscarAPI buscarAPI = new BuscarAPI();
            buscarAPI.execute(db).get();
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao buscar questões", e);
        }
    }

    class BuscarAPI extends AsyncTask<SQLiteDatabase, Void, Void> {

        @Override
        protected Void doInBackground(SQLiteDatabase... parameters) {
            String response = LetUsKnowWs.criar()
                    .rootUrl("http://letusknow.herokuapp.com")
                    .autenticar("ws", "123")
                    .get("/ws/questao/buscar");

            List<Questao> questoes = QuestaoConverter.converter(response);

            for (Questao questao : questoes) {
                QuestionarioDao.inserirQuestao(parameters[0], questao);
                for (Resposta resposta : questao.getRespostas()) {
                    resposta.setQuestao(questao.getId());
                    RespostaDao.inserirResposta(parameters[0], resposta);
                }
            }

            return null;
        }

    }

}
