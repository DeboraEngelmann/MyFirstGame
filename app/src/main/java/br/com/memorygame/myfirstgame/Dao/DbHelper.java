package br.com.memorygame.myfirstgame.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by debo_ on 03/04/2017.
 */
public class DbHelper extends SQLiteOpenHelper{
    private static final String NOME_BASE = "FirstGame";
    private static final int VERSAO_BASE = 6;
    private static DbHelper instance;
    public DbHelper(Context context) {
        super(context, NOME_BASE, null, VERSAO_BASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(JogadorDao.CRIAR_TABELA_JOGADOR());
       db.execSQL(LevelDao.CRIAR_TABELA_LEVEL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(JogadorDao.SCRIPT_DELECAO_TABELA);
        db.execSQL(LevelDao.SCRIPT_DELECAO_TABELA);
        onCreate(db);
    }
    public static DbHelper getInstance(Context context) {
        if(instance == null)
            instance = new DbHelper(context);
        return instance;
    }

    public Cursor select(String query) throws SQLException {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery(query, null);
    }

    public void insert(String table, ContentValues values) throws SQLException {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(table, null, values);
//db.close();
    }

    public int getInsert(String table, ContentValues values) throws SQLException {
        int id ;
        SQLiteDatabase db = getWritableDatabase();
        id=(int)db.insert(table, null, values);
//db.close();
        return id;
    }
    public void delete(String table, String where) throws SQLException {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(table, where, null);
        db.close();
    }

    public void update(String table, ContentValues values, String where) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(table, values, where, null);
//db.close();
    }
    public int getSequencia(String table){
        String query = "select seq from sqlite_sequence where name= " +table;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        return c.getInt(0);
    }
}
