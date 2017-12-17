package br.com.silas.letusknow.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.component.LetUsKnowChart;
import br.com.silas.letusknow.component.LetUsKnowProgress;
import br.com.silas.letusknow.exception.ServiceException;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.model.Resposta;
import br.com.silas.letusknow.service.BuscarService;
import br.com.silas.letusknow.utils.ListUtils;

public class ResultadosActivity extends BaseActivity {

    private BuscarService buscarService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_resultados);

            buscarService = new BuscarService(this);

            Button botaoVoltar = (Button) findViewById(R.id.botao_voltar);
            botaoVoltar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    irParaHome();
                }

            });
        } catch (ServiceException e) {
            mostarMensagemErro(e);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao criar resultados", e);
        }
    }

    @Override
    protected void onStart() {
        try {
            super.onStart();
            ResultadoAPI resultadoAPI = new ResultadoAPI();
            resultadoAPI.execute();
        } catch (ServiceException e) {
            mostarMensagemErro(e);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao buscar resultados", e);
        }
    }

    private void preencherTela(List<Questao> questoes) {
        if (ListUtils.isNotEmpty(questoes)) {
            LinearLayout graficos = (LinearLayout) findViewById(R.id.graficos);
            for (Questao questao : questoes) {
                LetUsKnowChart grafico = new LetUsKnowChart(this);
                grafico.setTitle(questao.getDescricao());
                for (Resposta resposta : questao.getRespostas()) {
                    grafico.addInformacao(resposta.getVotos(), resposta.getDescricao());
                }
                grafico.concluir();
                graficos.addView(grafico);
            }
        } else {
            mostarMensagem("Não foi possível buscar os resultados da pesquisa", new OnClose() {

                @Override
                public void onClose() {
                    irParaHome();
                }

            });
        }
    }

    private void irParaHome() {
        Intent intent = new Intent(ResultadosActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    class ResultadoAPI extends AsyncTask<Void, Void, List<Questao>> {

        private LetUsKnowProgress progress;

        @Override
        protected void onPreExecute() {
            progress = new LetUsKnowProgress(ResultadosActivity.this);
            progress.show();
        }

        @Override
        protected List<Questao> doInBackground(Void... parameters) {
            try {
                return buscarService.buscar();
            } catch (Exception e) {
                Log.e("LetUsKnow", "Erro ao buscar resultados", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Questao> questoes) {
            preencherTela(questoes);
            progress.hide();
        }

    }

}
