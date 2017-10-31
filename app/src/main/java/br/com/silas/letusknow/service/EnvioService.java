package br.com.silas.letusknow.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import br.com.silas.letusknow.dao.QuestionarioDao;
import br.com.silas.letusknow.exception.ServiceException;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.utils.ListUtils;

public class EnvioService {

    private QuestionarioDao questionarioDao;

    public EnvioService(Context context) {
        questionarioDao = new QuestionarioDao(context);
    }

    public void enviar() {
        List<Questao> questoes = questionarioDao.buscarQuestoes();
        if (ListUtils.isNotEmpty(questoes)) {
            EnviarAPI request = new EnviarAPI();
            request.execute(questoes);
        } else {
            throw new ServiceException("Sem questões para enviar");
        }
    }

    class EnviarAPI extends AsyncTask<List<Questao>, String, String> {

        @Override
        protected void onPreExecute() {
            //TODO: Criar loading
        }

        @Override
        protected String doInBackground(List<Questao>... parameters) {
            List<Questao> questoes = ListUtils.getFirst(parameters);
            if (ListUtils.isEmpty(questoes)) {
                throw new ServiceException("Erro ao buscar questões");
            }
            try {
                URL url = new URL("URL de envio");
                String request = new Gson().toJson(questoes);
                //TODO: Enviar questões
                return null;
            } catch (MalformedURLException e) {
                throw new ServiceException("Verifique a URL de envio");
            } catch (Exception e) {
                Log.e("LetUsKnow", "Erro ao enviar questões", e);
                throw new ServiceException("Erro ao enviar questões");
            }
        }

        @Override
        protected void onPostExecute(String response) {
            //TODO: Tratar resultado e esconder loading
        }

    }

}
