package br.com.silas.letusknow.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.com.silas.letusknow.R;
import br.com.silas.letusknow.utils.SomUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = (Button) findViewById(R.id.botao_start);
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                iniciarQuestionario();
            }

        });
    }

    private void iniciarQuestionario() {
        SomUtils.play(this);
        Intent intent = new Intent(MainActivity.this, QuestionarioActivity.class);
        startActivity(intent);
        finish();
    }

}
