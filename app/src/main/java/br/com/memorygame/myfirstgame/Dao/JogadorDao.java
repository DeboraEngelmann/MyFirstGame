package br.com.memorygame.myfirstgame.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;


import br.com.memorygame.myfirstgame.Entidades.Jogador;

/**
 * Created by debo_ on 06/04/2017.
 */
public class JogadorDao {
    public static final String NOME_TABELA = "Jogador";
    public static final String COLUNA_ID = "id_jogador";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_PROGRESSO= "progresso";

    public static final String CRIAR_TABELA_JOGADOR(){
        String query =  "CREATE TABLE " + NOME_TABELA;
        query += " (";
        query += COLUNA_ID + " " + DataModel.TIPO_INTEIRO_PK + ", ";
        query += COLUNA_NOME + " " + DataModel.TIPO_TEXTO + ", ";
        query += COLUNA_EMAIL + " " + DataModel.TIPO_TEXTO + ", ";
        query += COLUNA_PROGRESSO + " " + DataModel.TIPO_INTEIRO + " ";
        query += ")";
        return query;
    }
    public static final String SCRIPT_DELECAO_TABELA =  "DROP TABLE IF EXISTS " + NOME_TABELA;

    private SQLiteDatabase dataBase = null;

    private static JogadorDao instance;

    public static JogadorDao getInstance(Context context) {
        if(instance == null)
            instance = new JogadorDao(context);
        return instance;
    }

    private JogadorDao(Context context) {
        DbHelper dbHelper = DbHelper.getInstance(context);
        dataBase = dbHelper.getWritableDatabase();
    }
    //inserir
    public void insertJogador(Jogador jogador) {
        ContentValues values = gerarContentValuesJogador(jogador);
        dataBase.insert(NOME_TABELA, null, values);
    }
    //atualizar
    public void updateJogador(Jogador jogador){
        ContentValues valores = gerarContentValuesJogador(jogador);

        String[] valoresParaSubstituir = {
                String.valueOf(jogador.getIdJogador())
        };
        dataBase.update(NOME_TABELA, valores, COLUNA_ID + " = ?", valoresParaSubstituir);
    }
    //deletar
    public void deleteJogador(Jogador jogador){
        String[] valoresParaSubstituir = {
                String.valueOf(jogador.getIdJogador())
        };
        dataBase.delete(NOME_TABELA, COLUNA_ID + " =  ?", valoresParaSubstituir);
    }
    //retornar um jogador
    public Jogador getJogador (int id) {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA +" WHERE "+ COLUNA_ID +"=" + id;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Jogador> jogadores = construirJogadorPorCursor(cursor);
        Jogador jogador = new Jogador(jogadores.get(0).getIdJogador(),jogadores.get(0).getNome(),jogadores.get(0).getEmail(),jogadores.get(0).getProgresso());
        return jogador;
    }
    //retorna todos os jogadores
    public List<Jogador> selectTodosOsJogadores() {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Jogador> jogadores = construirJogadorPorCursor(cursor);

        return jogadores;
    }

    private List<Jogador> construirJogadorPorCursor(Cursor cursor) {
        List<Jogador> jogadores = new ArrayList<Jogador>();
        if(cursor == null)
            return jogadores;
        try {

            if (cursor.moveToFirst()) {
                do {

                    int indexIDJogador = cursor.getColumnIndex(COLUNA_ID);
                    int indexNome = cursor.getColumnIndex(COLUNA_NOME);
                    int indexEmail = cursor.getColumnIndex(COLUNA_EMAIL);
                    int indexProgresso = cursor.getColumnIndex(COLUNA_PROGRESSO);

                    int idJogador = cursor.getInt(indexIDJogador);
                    String nome = cursor.getString(indexNome);
                    String email = cursor.getString(indexEmail);
                    int progresso = cursor.getInt(indexProgresso);

                    Jogador jogador = new Jogador(idJogador, nome, email, progresso);

                    jogadores.add(jogador);

                } while (cursor.moveToNext());
            }

        } finally {
            cursor.close();
        }
        return jogadores;
    }

    private ContentValues gerarContentValuesJogador(Jogador jogador) {
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, jogador.getNome());
        values.put(COLUNA_EMAIL, jogador.getEmail());
        values.put(COLUNA_PROGRESSO, jogador.getProgresso());
        return values;
    }

    public void fecharConexao() {
        if(dataBase != null && dataBase.isOpen())
            dataBase.close();
    }
}
