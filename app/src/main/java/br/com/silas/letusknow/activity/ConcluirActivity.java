package br.com.silas.letusknow.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.exception.ServiceException;
import br.com.silas.letusknow.model.Enviar;
import br.com.silas.letusknow.service.EnvioService;
import br.com.silas.letusknow.utils.SomUtils;

public class ConcluirActivity extends BaseActivity {

    private EnvioService envioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_concluir);

            envioService = new EnvioService(this);

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
            EnviarAPI request = new EnviarAPI();
            request.execute();
        } catch (ServiceException e) {
            mostarMensagemErro(e);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao sair", e);
        }
    }

    class EnviarAPI extends AsyncTask<Void, Void, Enviar> {

        private ProgressBar progress;

        @Override
        protected void onPreExecute() {
            progress = new ProgressBar(ConcluirActivity.this);
            progress.setIndeterminate(true);
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Enviar doInBackground(Void... parameters) {
            Enviar enviar = new Enviar();
            try {
                envioService.enviar();
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
