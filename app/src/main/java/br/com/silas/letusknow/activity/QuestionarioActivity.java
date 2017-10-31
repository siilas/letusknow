package br.com.silas.letusknow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.dao.QuestionarioDao;
import br.com.silas.letusknow.exception.ServiceException;
import br.com.silas.letusknow.model.Controlador;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.utils.SomUtils;

public class QuestionarioActivity extends BaseActivity {

    private QuestionarioDao dao;
    private Controlador controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
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
                    sair();
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
            controlador.addQuestoes(dao.buscarQuestoes());
            if (controlador.isNotEmpty()) {
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
            Intent intent = new Intent(QuestionarioActivity.this, ConcluirActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            mostarMensagemErro("Erro ao concluir questionário", e);
        }
    }

    private void irParaHome() {
        Intent intent = new Intent(QuestionarioActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void sair() {
        SomUtils.play(QuestionarioActivity.this);
        irParaHome();
    }

}
