package br.com.silas.letusknow.service;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import br.com.silas.letusknow.dao.QuestionarioDao;
import br.com.silas.letusknow.dao.RespostaDao;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.model.Resposta;

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
            try {
                Authenticator myAuth = new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("ws", "123".toCharArray());
                    }
                };
                Authenticator.setDefault(myAuth);
                URL url = new URL("http://letusknow.herokuapp.com/ws/questao/buscar");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONArray questoes = new JSONArray(response.toString());
                for (int i = 0; i < questoes.length(); i++) {
                    JSONObject questao = questoes.getJSONObject(i);
                    Questao questaoToSave = new Questao();
                    questaoToSave.setId(questao.getInt("id"));
                    questaoToSave.setDescricao(questao.getString("descricao"));
                    QuestionarioDao.inserirQuestao(parameters[0], questaoToSave);
                    JSONArray respostas = questao.getJSONArray("respostas");
                    for (int j = 0; j < respostas.length(); j++) {
                        JSONObject resposta = respostas.getJSONObject(j);
                        Resposta respostaToSave = new Resposta();
                        respostaToSave.setId(resposta.getInt("id"));
                        respostaToSave.setDescricao(resposta.getString("descricao"));
                        respostaToSave.setSelecionada(false);
                        respostaToSave.setVotos(resposta.getInt("votos"));
                        respostaToSave.setQuestao(questaoToSave.getId());
                        RespostaDao.inserirResposta(parameters[0], respostaToSave);
                    }
                }
            } catch (Exception e) {
                Log.e("LetUsKnow", "Erro ao buscar questões", e);
            }
            return null;
        }

    }

}
