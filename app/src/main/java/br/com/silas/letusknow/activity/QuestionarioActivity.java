package br.com.silas.letusknow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.dao.QuestionarioDao;
import br.com.silas.letusknow.exception.ServiceException;
import br.com.silas.letusknow.model.Controlador;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.utils.ListUtils;
import br.com.silas.letusknow.utils.SomUtils;

public class QuestionarioActivity extends BaseActivity {

    private QuestionarioDao dao;
    private Controlador controlador;

    public QuestionarioActivity() {
        dao = new QuestionarioDao(this);
        controlador = new Controlador();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_questionario);
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
        } catch (ServiceException e) {
            mostarMensagemErro(e);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao criar questionário", e);
        }
    }

    @Override
    protected void onStart() {
        try {
            super.onStart();
            controlador.getQuestoes().addAll(dao.buscarQuestoes());
            if (ListUtils.isNotEmpty(controlador.getQuestoes())) {
                preencherTela(controlador.getQuestaoAtual());
            }
        } catch (ServiceException e) {
            mostarMensagemErro(e);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao buscar questões", e);
        }
    }

    private void preencherTela(Questao questao) {
        try {
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
        } catch (ServiceException e) {
            mostarMensagemErro(e);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao preencher tela", e);
        }
    }

    private void responder() {
        try {
            SomUtils.play(this);
            dao.salvarResposta(controlador.setResposta(findViewById(R.id.answer)));
            if (controlador.deveConcluir()) {
                concluir();
            } else {
                preencherTela(controlador.getProximaQuestao());
            }
        } catch (ServiceException e) {
            mostarMensagemErro(e);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao responder questão", e);
        }
    }

    private void concluir() {
        try {
            Toast.makeText(this, R.string.conclusao, Toast.LENGTH_LONG).show();
            Handler handle = new Handler();
            handle.postDelayed(new Runnable() {

                @Override
                public void run() {
                    irParaHome();
                }

            }, Toast.LENGTH_LONG);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao concluir questionário", e);
        }
    }

    private void irParaHome() {
        Intent intent = new Intent(QuestionarioActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
