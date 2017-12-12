package br.com.silas.letusknow.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;

import java.util.ArrayList;
import java.util.List;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.exception.ServiceException;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.model.Resposta;
import br.com.silas.letusknow.service.BuscarService;
import br.com.silas.letusknow.utils.ColorUtils;
import br.com.silas.letusknow.utils.ListUtils;

public class ResultadosActivity extends BaseActivity {

    private BuscarService buscarService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_resultados);

            buscarService = new BuscarService(this);
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
            RelativeLayout graficos = (RelativeLayout) findViewById(R.id.graficos);
            for (Questao questao : questoes) {
                ArrayList<Bar> bars = new ArrayList<>();
                for (Resposta resposta : questao.getRespostas()) {
                    Bar bar = new Bar();
                    bar.setColor(ColorUtils.getRandomColor());
                    bar.setName(resposta.getDescricao());
                    bar.setValue(resposta.getVotos());
                    bars.add(bar);
                }
                BarGraph graph = new BarGraph(this);
                graph.setBars(bars);
                graph.setUnit(" ");
                graficos.addView(graph);
                return;
            }
        } else {
            mostarMensagem("Não foi possível buscar os resultados da pesquisa", new OnClose() {

                @Override
                public void onClose() {
                    Intent intent = new Intent(ResultadosActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            });
        }
    }

    class ResultadoAPI extends AsyncTask<Void, Void, List<Questao>> {

        private ProgressBar progress;

        @Override
        protected void onPreExecute() {
            progress = new ProgressBar(ResultadosActivity.this);
            progress.setIndeterminate(true);
            progress.setVisibility(View.VISIBLE);
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
            progress.setVisibility(View.INVISIBLE);
        }

    }

}
