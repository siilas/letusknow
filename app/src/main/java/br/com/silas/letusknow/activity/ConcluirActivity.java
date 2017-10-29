package br.com.silas.letusknow.activity;

import android.os.Bundle;

import br.com.silas.letusknow.R;

public class ConcluirActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_concluir);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao concluir questionário", e);
        }
    }

}
