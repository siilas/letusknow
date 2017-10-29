package br.com.silas.letusknow.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import br.com.silas.letusknow.exception.ServiceException;

public abstract class BaseActivity extends AppCompatActivity {

    protected void mostarMensagemErro(String mensagem, Exception erro) {
        Log.e("LetUsKnow", mensagem, erro);
    }

    protected void mostarMensagemErro(ServiceException erro) {

    }

}
