package br.com.silas.letusknow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.silas.letusknow.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            Handler handle = new Handler();
            handle.postDelayed(new Runnable() {

                @Override
                public void run() {
                    irParaMenu();
                }

            }, 2000);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao mostrar tela inicial", e);
        }
    }

    private void irParaMenu() {
        try {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            mostarMensagemErro("Erro ao criar menu", e);
        }
    }

}
