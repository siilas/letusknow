package br.com.silas.letusknow.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.dao.QuestionarioDao;
import br.com.silas.letusknow.dao.RespostaDao;
import br.com.silas.letusknow.exception.ServiceException;
import br.com.silas.letusknow.model.Enviar;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.service.EnvioService;
import br.com.silas.letusknow.utils.SomUtils;

public class ConcluirActivity extends BaseActivity {

    private RespostaDao respostaDao;
    private EnvioService envioService;
    private QuestionarioDao questionarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_concluir);

            respostaDao = new RespostaDao(this);
            envioService = new EnvioService(this);
            questionarioDao = new QuestionarioDao(this);

            Button enviarButton = (Button) findViewById(R.id.botao_enviar);
            enviarButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    enviar();
                }

            });
            Button sairButton = (Button) findViewById(R.id.botao_voltar);
            sairButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    sair();
                }

            });
        } catch (Exception e) {
            mostarMensagemErro("Erro ao concluir questionário", e);
        }
    }

    private void sair() {
        try {
            SomUtils.play(this);
            irParaHome();
        } catch (Exception e) {
            mostarMensagemErro("Erro ao sair", e);
        }
    }

    private void irParaHome() {
        Intent intent = new Intent(ConcluirActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void enviar() {
        try {
            SomUtils.play(this);
            List<Questao> questoes = questionarioDao.buscarQuestoes();
            for (Questao questao : questoes) {
                questao.getRespostas().addAll(respostaDao.buscarRespostas(questao.getId()));
            }
            EnviarAPI request = new EnviarAPI();
            request.execute(questoes);
        } catch (ServiceException e) {
            mostarMensagemErro(e);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao sair", e);
        }
    }

    class EnviarAPI extends AsyncTask<List<Questao>, Void, Enviar> {

        private ProgressBar progress;

        @Override
        protected void onPreExecute() {
            progress = new ProgressBar(ConcluirActivity.this);
            progress.setIndeterminate(true);
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Enviar doInBackground(List<Questao>... parameters) {
            Enviar enviar = new Enviar();
            try {
                envioService.enviar(parameters[0]);
                enviar.sucesso();
            } catch (ServiceException e) {
                enviar.erro(e.getMessage());
            } catch (Exception e) {
                Log.e("LetUsKnow", "Erro ao enviar questões", e);
                enviar.erro("Erro ao enviar questões");
            }
            return enviar;
        }

        @Override
        protected void onPostExecute(Enviar enviar) {
            String message;
            if (enviar.isSucesso()) {
                message = getString(R.string.enviado);
            } else {
                message = enviar.getMessage();
            }
            progress.setVisibility(View.INVISIBLE);

            mostarMensagem(message, new OnClose() {

                @Override
                public void onClose() {
                    irParaHome();
                }

            });
        }

    }

}
