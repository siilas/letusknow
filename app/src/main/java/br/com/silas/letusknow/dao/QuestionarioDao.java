package br.com.silas.letusknow.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.silas.letusknow.db.DatabaseHelper;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.utils.ArrayUtils;

public class QuestionarioDao {

    private static SQLiteDatabase db;

    public QuestionarioDao(Context context) {
        if (db == null) {
            db = new DatabaseHelper(context).getWritableDatabase();
        }
    }

    public static void inserirQuestoes(SQLiteDatabase db, String[] descricoes) {
        if (ArrayUtils.isNotEmpty(descricoes)) {
            db.beginTransactionNonExclusive();
            try {
                for (String descricao : descricoes) {
                    ContentValues content = new ContentValues();
                    content.put("DESCRICAO", descricao);
                    content.put("RESPOSTA", "");
                    db.insert("QUESTOES", null, content);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.e("[LetUsKnow]", "Erro ao inserir questões", e);
            }
            db.endTransaction();
        }
    }

    public List<Questao> buscarQuestoes() {
        db.beginTransactionNonExclusive();
        Cursor cursor = db.query("QUESTOES",
                new String[] { "CODIGO", "DESCRICAO", "RESPOSTA" },
                null,
                null,
                null,
                null,
                "CODIGO ASC");
        List<Questao> questoes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Questao questao = new Questao();
                questao.setId(cursor.getInt(0));
                questao.setDescricao(cursor.getString(1));
                questao.setResposta(cursor.getString(2));
                questoes.add(questao);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.endTransaction();
        return questoes;
    }

    public void salvarResposta(Questao questao) {
        db.beginTransactionNonExclusive();
        ContentValues values = new ContentValues();
        values.put("RESPOSTA", questao.getResposta());
        db.update("QUESTOES", values, "CODIGO = ?", new String[] { String.valueOf(questao.getId()) });
        db.setTransactionSuccessful();
        db.endTransaction();
    }

}
