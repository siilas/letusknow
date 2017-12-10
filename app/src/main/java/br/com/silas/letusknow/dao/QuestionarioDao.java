package br.com.silas.letusknow.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.silas.letusknow.db.DatabaseHelper;
import br.com.silas.letusknow.exception.ServiceException;
import br.com.silas.letusknow.model.Questao;
import br.com.silas.letusknow.service.BuscarService;

public class QuestionarioDao {

    private static SQLiteDatabase db;

    public QuestionarioDao(Context context) {
        try {
            if (db == null) {
                db = new DatabaseHelper(context).getWritableDatabase();
            }
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao criar conexão com o banco de dados", e);
            throw new ServiceException("Erro ao criar conexão com o banco de dados");
        }
    }

    public List<Questao> buscarQuestoes() {
        try {
            db.beginTransactionNonExclusive();
            Cursor cursor = db.query("QUESTOES",
                    new String[]{"CODIGO", "DESCRICAO"},
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
                    questoes.add(questao);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.endTransaction();
            return questoes;
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao buscar questões", e);
            throw new ServiceException("Erro ao buscar questões");
        }
    }

    public void salvarResposta(Questao questao) {
        try {
            db.beginTransactionNonExclusive();
            ContentValues values = new ContentValues();
            values.put("RESPOSTA", questao.getResposta());
            db.update("QUESTOES", values, "CODIGO = ?", new String[]{String.valueOf(questao.getId())});
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao salvar resposta", e);
            throw new ServiceException("Erro ao salvar resposta");
        }
    }

    public static void inserirQuestao(SQLiteDatabase db, Questao questao) {
        try {
            db.beginTransactionNonExclusive();
            ContentValues content = new ContentValues();
            content.put("CODIGO", questao.getId());
            content.put("DESCRICAO", questao.getDescricao());
            db.insert("QUESTOES", null, content);
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao inserir questão", e);
        }
    }

}
