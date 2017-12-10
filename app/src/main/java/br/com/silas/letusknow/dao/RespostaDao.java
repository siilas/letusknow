package br.com.silas.letusknow.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.silas.letusknow.db.DatabaseHelper;
import br.com.silas.letusknow.exception.ServiceException;
import br.com.silas.letusknow.model.Resposta;

public class RespostaDao {

    private static SQLiteDatabase db;

    public RespostaDao(Context context) {
        try {
            if (db == null) {
                db = new DatabaseHelper(context).getWritableDatabase();
            }
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao criar conexão com o banco de dados", e);
            throw new ServiceException("Erro ao criar conexão com o banco de dados");
        }
    }

    public static void inserirResposta(SQLiteDatabase db, Resposta resposta) {
        try {
            db.beginTransactionNonExclusive();
            ContentValues content = new ContentValues();
            content.put("CODIGO", resposta.getId());
            content.put("DESCRICAO", resposta.getDescricao());
            content.put("SELECIONADA", resposta.isSelecionada());
            content.put("VOTOS", resposta.getVotos());
            content.put("QUESTAO", resposta.getQuestao());
            db.insert("RESPOSTAS", null, content);
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao inserir resposta", e);
        }
    }

}
