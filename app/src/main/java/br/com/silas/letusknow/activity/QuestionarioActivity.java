package br.com.silas.letusknow.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.dao.QuestionarioDao;

public class QuestionarioActivity extends AppCompatActivity {

    private QuestionarioDao dao = new QuestionarioDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);
        dao.buscarQuestoes();
    }

}
