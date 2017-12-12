package br.com.silas.letusknow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.component.LetUsKnowRadioButton;
import br.com.silas.letusknow.dao.QuestionarioDao;
import br.com.silas.letusknow.dao.RespostaDao;
import br.com.silas.letusknow.exception.ServiceException;
import br.com.silas.letusknow.model.Controlador;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.model.Resposta;
import br.com.silas.letusknow.utils.ListUtils;
import br.com.silas.letusknow.utils.SomUtils;

public class QuestionarioActivity extends BaseActivity {

    private Controlador controlador;
    private RespostaDao respostaDao;
    private QuestionarioDao questionarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_questionario);

            controlador = new Controlador();
            respostaDao = new RespostaDao(this);
            questionarioDao = new QuestionarioDao(this);

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
            List<Questao> questoes = questionarioDao.buscarQuestoes();
            if (ListUtils.isNotEmpty(questoes)) {
                for (Questao questao : questoes) {
                    questao.getRespostas().addAll(respostaDao.buscarRespostas(questao.getId()));
                }
            }
            controlador.addQuestoes(questoes);
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
            RadioGroup respostas = (RadioGroup) findViewById(R.id.answer);
            if (respostas != null) {
                respostas.removeAllViews();
                for (Resposta resposta : questao.getRespostas()) {
                    RadioButton radio = new LetUsKnowRadioButton(this);
                    radio.setId(resposta.getId());
                    radio.setContentDescription(resposta.getDescricao());
                    radio.setText(resposta.getDescricao());
                    radio.setChecked(resposta.isSelecionada());
                    radio.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            selecionarResposta(view.getId());
                        }

                    });
                    respostas.addView(radio);
                }
            }
        } catch (ServiceException e) {
            mostarMensagemErro(e);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao preencher tela", e);
        }
    }

    private void selecionarResposta(Integer id) {
        for (Resposta resposta : controlador.getQuestaoAtual().getRespostas()) {
            resposta.setSelecionada(resposta.getId().equals(id));
        }
    }

    private void responder() {
        try {
            SomUtils.play(this);
            respostaDao.atualizarRespostas(controlador.getQuestaoAtual().getRespostas());
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
