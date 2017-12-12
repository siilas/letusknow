package br.com.silas.letusknow.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import br.com.silas.letusknow.converter.VotoConverter;
import br.com.silas.letusknow.exception.ServiceException;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.utils.ListUtils;
import br.com.silas.letusknow.utils.PropertiesUtils;
import br.com.silas.letusknow.ws.LetUsKnowWs;

public class EnvioService {

    private PropertiesUtils properties;

    public EnvioService(Context context) {
        properties = new PropertiesUtils(context);
    }

    public void enviar(List<Questao> questoes) {
        if (ListUtils.isNotEmpty(questoes)) {
            try {
                Integer response = LetUsKnowWs.criar()
                        .rootUrl(properties.get("root.url"))
                        .autenticar(properties.get("ws.user"), properties.get("ws.pass"))
                        .post("/ws/questao/salvar", VotoConverter.converter(questoes));

                if (response != 200) {
                    throw new ServiceException("Não foi possível enviar as questões");
                }
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                Log.e("LetUsKnow", "Erro ao enviar questões", e);
                throw new ServiceException("Erro ao enviar questões");
            }
        } else {
            throw new ServiceException("Sem questões para enviar");
        }
    }

}
