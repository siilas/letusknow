package br.com.silas.letusknow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.exception.ServiceException;
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
            envioService.enviar();
            Toast.makeText(this, R.string.enviado, Toast.LENGTH_LONG).show();
            Handler handle = new Handler();
            handle.postDelayed(new Runnable() {

                @Override
                public void run() {
                    irParaHome();
                }

            }, Toast.LENGTH_LONG);
        } catch (ServiceException e) {
            mostarMensagemErro(e);
        } catch (Exception e) {
            mostarMensagemErro("Erro ao sair", e);
        }
    }

}
