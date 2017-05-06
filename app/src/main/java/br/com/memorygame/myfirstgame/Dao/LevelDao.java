package br.com.memorygame.myfirstgame.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import br.com.memorygame.myfirstgame.Entidades.Level;

/**
 * Created by debo_ on 06/04/2017.
 */
public class LevelDao {

    public static final String NOME_TABELA = "Level";
    public static final String COLUNA_ID = "id_level";
    public static final String COLUNA_TENTATIVAS = "tentativas";
    public static final String COLUNA_CLIQUES = "cliques";
    public static final String COLUNA_CONCLUIDO = "concluido";

    public static final String CRIAR_TABELA_LEVEL(){
        String query =  "CREATE TABLE " + NOME_TABELA;
        query += " (";
        query += COLUNA_ID + " " + DataModel.TIPO_INTEIRO_PK + ", ";
        query += COLUNA_TENTATIVAS + " " + DataModel.TIPO_INTEIRO + ", ";
        query += COLUNA_CLIQUES + " " + DataModel.TIPO_INTEIRO + ", ";
        query += COLUNA_CONCLUIDO + " " + DataModel.TIPO_NUMERIC + " ";
        query += ")";
        return query;
    }
    public static final String SCRIPT_DELECAO_TABELA =  "DROP TABLE IF EXISTS " + NOME_TABELA;

    private SQLiteDatabase dataBase = null;

    private static LevelDao instance;

    public static LevelDao getInstance(Context context) {
        if(instance == null)
            instance = new LevelDao(context);
        return instance;
    }

    private LevelDao(Context context) {
        DbHelper dbHelper = DbHelper.getInstance(context);
        dataBase = dbHelper.getWritableDatabase();
    }
    //inserir
    public void insertLevel(Level level) {
        ContentValues values = gerarContentValuesLevel(level);
        dataBase.insert(NOME_TABELA, null, values);
    }
    //atualizar
    public void updateLevel(Level level){
        ContentValues valores = gerarContentValuesLevel(level);

        String[] valoresParaSubstituir = {
                String.valueOf(level.getIdLevel())
        };
        dataBase.update(NOME_TABELA, valores, COLUNA_ID + " = ?", valoresParaSubstituir);
    }
    //deletar
    public void deleteLevel(Level level){
        String[] valoresParaSubstituir = {
                String.valueOf(level.getIdLevel())
        };
        dataBase.delete(NOME_TABELA, COLUNA_ID + " =  ?", valoresParaSubstituir);
    }
    //retornar um Level
    public Level getLevel (int id) {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA +" WHERE "+ COLUNA_ID +"=" + id;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Level> levels = construirLevelPorCursor(cursor);
        Level level;
        if (levels.size()>0){
            level = new Level(levels.get(0).getIdLevel(),levels.get(0).getTentativas(),levels.get(0).getCliques(),levels.get(0).isConcluido());
        }else {
            level=null;
        }

        return level;
    }
    //retorna todos os levels
    public List<Level> selectTodosOsLevels() {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Level> levels = construirLevelPorCursor(cursor);

        return levels;
    }

    private List<Level> construirLevelPorCursor(Cursor cursor) {
        List<Level> levels = new ArrayList<Level>();
        if(cursor == null)
            return levels;
        try {

            if (cursor.moveToFirst()) {
                do {

                    int indexIDLevel = cursor.getColumnIndex(COLUNA_ID);
                    int indexTentativas = cursor.getColumnIndex(COLUNA_TENTATIVAS);
                    int indexCliques = cursor.getColumnIndex(COLUNA_CLIQUES);
                    int indexConcluido = cursor.getColumnIndex(COLUNA_CONCLUIDO);

                    int idLevel = cursor.getInt(indexIDLevel);
                    int tentativas = cursor.getInt(indexTentativas);
                    int cliques = cursor.getInt(indexCliques);
                    int concluido = cursor.getInt(indexConcluido);

                    Level level = new Level(idLevel, tentativas, cliques, concluido);

                    levels.add(level);

                } while (cursor.moveToNext());
            }

        } finally {
            cursor.close();
        }
        return levels;
    }

    private ContentValues gerarContentValuesLevel(Level level) {
        ContentValues values = new ContentValues();
        values.put(COLUNA_TENTATIVAS, level.getTentativas());
        values.put(COLUNA_CLIQUES, level.getCliques());
        values.put(COLUNA_CONCLUIDO, level.isConcluido());
        return values;
    }

}
