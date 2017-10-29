package br.com.silas.letusknow.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.exception.ServiceException;

public abstract class BaseActivity extends AppCompatActivity {

    protected void mostarMensagemErro(String mensagem, Exception erro) {
        Log.e("LetUsKnow", mensagem, erro);
        mensagemErro(mensagem);
    }

    protected void mostarMensagemErro(ServiceException erro) {
        mensagemErro(erro.getMessage());
    }

    private void mensagemErro(String erro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(erro);
        builder.setCancelable(false);

        builder.setPositiveButton(
                R.string.error_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
