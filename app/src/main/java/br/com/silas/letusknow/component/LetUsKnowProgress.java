package br.com.silas.letusknow.component;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import br.com.silas.letusknow.R;

public class LetUsKnowProgress extends ProgressDialog {

    public LetUsKnowProgress(Context context) {
        super(context, R.style.AlertDialogStyle);
        setIndeterminate(true);
        setCancelable(false);
        setMessage("Carregando...");
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }

}
