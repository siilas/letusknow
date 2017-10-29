package br.com.silas.letusknow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.utils.SomUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Button startButton = (Button) findViewById(R.id.botao_start);
            startButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    iniciarQuestionario();
                }

            });
        } catch (Exception e) {
            mostarMensagemErro("Erro ao criar tela de menu", e);
        }
    }

    private void iniciarQuestionario() {
        try {
            SomUtils.play(this);
            Intent intent = new Intent(MainActivity.this, QuestionarioActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            mostarMensagemErro("Erro ao iniciar questionário", e);
        }
    }

}
