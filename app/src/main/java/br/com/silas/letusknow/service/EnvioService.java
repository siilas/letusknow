package br.com.silas.letusknow.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
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
            try {
                URL url = new URL("http://www.google.com.br");
                String request = new Gson().toJson(questoes);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
                writer.writeBytes(request);
                writer.flush();
                writer.close();
                if (connection.getResponseCode() != 200) {
                    throw new ServiceException("Não foi possível enviar as questões");
                }
            } catch (ServiceException e) {
                throw e;
            } catch (MalformedURLException e) {
                throw new ServiceException("Verifique a URL de envio");
            } catch (Exception e) {
                Log.e("LetUsKnow", "Erro ao enviar questões", e);
                throw new ServiceException("Erro ao enviar questões");
            }
        } else {
            throw new ServiceException("Sem questões para enviar");
        }
    }

}
