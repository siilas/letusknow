package br.com.silas.letusknow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.com.silas.letusknow.service.BuscarService;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DBVERSION = 2;
    private static final String DBNAME = "LetUsKnow.db";

    BuscarService buscarService;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
        buscarService = new BuscarService(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE QUESTOES (" +
                    " CODIGO INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " DESCRICAO TEXT NOT NULL);");
            db.execSQL("CREATE TABLE RESPOSTAS (" +
                    " CODIGO INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " DESCRICAO TEXT NOT NULL," +
                    " SELECIONADA INTEGER," +
                    " VOTOS INTEGER," +
                    " QUESTAO INTEGER NOT NULL," +
                    " FOREIGN KEY(QUESTAO) REFERENCES QUESTOES(CODIGO));");
            buscarService.buscar(db);
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao criar banco de dados", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS QUESTOES");
            db.execSQL("DROP TABLE IF EXISTS RESPOSTAS");
            onCreate(db);
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao atualizar banco de dados", e);
        }
    }

}
