package br.com.silas.letusknow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.dao.QuestionarioDao;
import br.com.silas.letusknow.model.Controlador;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.utils.ListUtils;
import br.com.silas.letusknow.utils.SomUtils;

public class QuestionarioActivity extends AppCompatActivity {

    private QuestionarioDao dao;
    private Controlador controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);
        dao = new QuestionarioDao(this);
        controlador = new Controlador();
        Button botaoProximo = (Button) findViewById(R.id.botao_next);
        botaoProximo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                responder();
            }

        });
        Button botaoSair = (Button) findViewById(R.id.botao_exit);
        botaoSair.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SomUtils.play(QuestionarioActivity.this);
                irParaHome();
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        controlador.getQuestoes().addAll(dao.buscarQuestoes());
        if (ListUtils.isNotEmpty(controlador.getQuestoes())) {
            preencherTela(controlador.getQuestaoAtual());
        }
    }

    private void preencherTela(Questao questao) {
        TextView descricao = (TextView) findViewById(R.id.question);
        if (descricao != null) {
            descricao.setContentDescription(questao.getDescricao());
            descricao.setText(questao.getDescricao());
        }
        EditText resposta = (EditText) findViewById(R.id.answer);
        if (resposta != null) {
            resposta.setContentDescription(questao.getResposta());
            resposta.setText(questao.getResposta());
        }
    }

    private void responder() {
        SomUtils.play(this);
        dao.salvarResposta(controlador.setResposta(findViewById(R.id.answer)));
        if (controlador.deveConcluir()) {
            concluir();
        } else {
            preencherTela(controlador.getProximaQuestao());
        }
    }

    private void concluir() {
        Toast.makeText(this, R.string.conclusao, Toast.LENGTH_LONG).show();
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {

            @Override
            public void run() {
                irParaHome();
            }

        }, Toast.LENGTH_LONG);
    }

    private void irParaHome() {
        Intent intent = new Intent(QuestionarioActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
