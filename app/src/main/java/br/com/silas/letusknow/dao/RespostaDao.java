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
import br.com.silas.letusknow.model.Resposta;
import br.com.silas.letusknow.utils.BooleanUtils;

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

    public List<Resposta> buscarRespostas(Integer questao) {
        try {
            db.beginTransactionNonExclusive();
            Cursor cursor = db.query("RESPOSTAS",
                    new String[]{"CODIGO", "DESCRICAO", "SELECIONADA", "VOTOS", "QUESTAO"},
                    "QUESTAO = ?",
                    new String[]{String.valueOf(questao)},
                    null,
                    null,
                    "CODIGO ASC");
            List<Resposta> repostas = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    Resposta resposta = new Resposta();
                    resposta.setId(cursor.getInt(0));
                    resposta.setDescricao(cursor.getString(1));
                    resposta.setSelecionada(BooleanUtils.toBoolean(cursor.getInt(2)));
                    resposta.setVotos(cursor.getInt(3));
                    resposta.setQuestao(cursor.getInt(4));
                    repostas.add(resposta);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.endTransaction();
            return repostas;
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao buscar respostas", e);
            throw new ServiceException("Erro ao buscar respostas");
        }
    }

    public void atualizarRespostas(List<Resposta> respostas) {
        try {
            db.beginTransactionNonExclusive();
            for (Resposta resposta : respostas) {
                ContentValues values = new ContentValues();
                values.put("DESCRICAO", resposta.getDescricao());
                values.put("SELECIONADA", resposta.isSelecionada());
                db.update("RESPOSTAS", values, "CODIGO = ?", new String[]{String.valueOf(resposta.getId())});
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao salvar resposta", e);
            throw new ServiceException("Erro ao salvar resposta");
        }
    }

    public static void inserirResposta(SQLiteDatabase db, List<Resposta> respostas) {
        try {
            for (Resposta resposta : respostas) {
                ContentValues content = new ContentValues();
                content.put("CODIGO", resposta.getId());
                content.put("DESCRICAO", resposta.getDescricao());
                content.put("SELECIONADA", resposta.isSelecionada());
                content.put("VOTOS", resposta.getVotos());
                content.put("QUESTAO", resposta.getQuestao());
                db.insert("RESPOSTAS", null, content);
            }
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao inserir resposta", e);
        }
    }

}
