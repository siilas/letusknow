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
        mensagem(mensagem, null);
    }

    protected void mostarMensagemErro(ServiceException erro) {
        mensagem(erro.getMessage(), null);
    }

    protected void mostarMensagem(String message, OnClose onClose) {
        mensagem(message, onClose);
    }

    private void mensagem(String erro, final OnClose onClose) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setMessage(erro);
        builder.setCancelable(false);

        builder.setPositiveButton(
                R.string.error_button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                        if (onClose != null) {
                            onClose.onClose();
                        }
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    interface OnClose {

        void onClose();

    }

}
