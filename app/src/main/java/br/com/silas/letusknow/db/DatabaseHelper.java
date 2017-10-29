package br.com.silas.letusknow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.com.silas.letusknow.dao.QuestionarioDao;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DBVERSION = 1;
    private static final String DBNAME = "LetUsKnow.db";
    private static final String[] QUESTOES = new String[] { "No geral, como é que a sua saude?",
            "Comparando com um ano atrás, como você diria que a sua saúde está hoje?",
            "Quanta dor no corpo você sentiu durante as últimas semanas?",
            "Há limitação de atividades devido ao seu estado de saúde atual?",
            "Qual a interferência da dor durante o seu dia-a-dia?" };

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE QUESTOES ( " +
                    " CODIGO INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " DESCRICAO TEXT," +
                    " RESPOSTA TEXT ); ");
            QuestionarioDao.inserirQuestoes(db, QUESTOES);
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao criar banco de dados", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS QUESTOES");
            onCreate(db);
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao atualizar banco de dados", e);
        }
    }

}
