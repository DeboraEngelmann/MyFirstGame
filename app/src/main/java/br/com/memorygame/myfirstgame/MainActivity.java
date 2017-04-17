package br.com.memorygame.myfirstgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.memorygame.myfirstgame.Dao.JogadorDao;
import br.com.memorygame.myfirstgame.Dao.LevelDao;
import br.com.memorygame.myfirstgame.Entidades.Jogador;
import br.com.memorygame.myfirstgame.Entidades.Level;


public class MainActivity extends AppCompatActivity {
    public static GridView gridViewImageMenu;
    public static AdapterMenu mAdapter;
    static ArrayList<Integer> arrayAdapter = MyFirstGame.arrayAdapter;
    public static Jogador jogador;
    public JogadorDao jogadorDao;
    public LevelDao leveldao;
    List<Level> levels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leveldao = LevelDao.getInstance(getBaseContext());
        jogador = new Jogador();
        jogadorDao = JogadorDao.getInstance(getApplicationContext());
        List<Jogador> jogadores = jogadorDao.selectTodosOsJogadores();
        if (jogadores.size()<1){
            Intent cadastroJogador = new Intent(this, CadastroJogador.class);
            startActivity(cadastroJogador);
        }
        else {
            jogador = jogadores.get(0);
        }

        setContentView(R.layout.activity_main);
        gridViewImageMenu = (GridView) findViewById(R.id.gridViewImageMenu);


        if(savedInstanceState==null){
            limparArrays();
            popularArrays();
            atualizaAdapter();
        }else{
            gridViewImageMenu.setAdapter(mAdapter);
        }

        gridViewImageMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(levels.get(position).isConcluido()==1) {
                    Intent jogoLevel = new Intent(MainActivity.this, JogoLevels.class);
                    JogoLevels.idLevel = position + 1;
                    startActivity(jogoLevel);
                }else{
                    //mostrar mensagem
                    Toast.makeText(getApplicationContext(),
                            "Errroooooou.", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

       // mAdapter.notifyDataSetChanged();

    }

    public void atualizaAdapter(){
        mAdapter = new AdapterMenu(MainActivity.this, arrayAdapter);
        gridViewImageMenu.setAdapter(mAdapter);
    }

    private void limparArrays(){
        arrayAdapter.clear();
    }

    private void popularArrays(){

        levels = leveldao.selectTodosOsLevels();

        if (levels.size()==0){
            Level level = new Level();
            level.setTentativas(0);
            level.setJogadasLevel(0);
            level.setConcluido(0);

            Level level1 = new Level();
            level1.setTentativas(0);
            level1.setJogadasLevel(0);
            level1.setConcluido(1);

            for (int i=0; i<=4; i++){
                if (i==0){
                    leveldao.insertLevel(level1);
                    setImagemConcluido(i);
                }else {
                    leveldao.insertLevel(level);
                    setImagemNaoConcluido(i);
                }

                levels = leveldao.selectTodosOsLevels();
            }
        } else{
            for (int i=0; i<levels.size();i++){
                if (levels.get(i).isConcluido()==1){
                    setImagemConcluido(i);
                }else {
                    setImagemNaoConcluido(i);
                }
            }
        }
    }
    public static void setImagemNaoConcluido(int level) {
        switch (level) {
            case 0:
                arrayAdapter.add(R.drawable.fundo);
                break;
            case 1:
                arrayAdapter.add(R.drawable.fundo);
                break;
            case 2:
                arrayAdapter.add(R.drawable.fundo);
                break;
            case 3:
                arrayAdapter.add(R.drawable.fundo);
                break;
            case 4:
                arrayAdapter.add(R.drawable.fundo);
            default:
                break;
        }
    }

    public static void setImagemConcluido(int level) {
        switch (level) {
            case 0:
                arrayAdapter.add(R.drawable.level1);
                break;
            case 1:
                arrayAdapter.add(R.drawable.level2);
                break;
            case 2:
                arrayAdapter.add(R.drawable.level3);
                break;
            case 3:
                arrayAdapter.add(R.drawable.level4);
                break;
            case 4:
                arrayAdapter.add(R.drawable.level5);
            default:
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putIntegerArrayList("arrayAdapter", arrayAdapter);
        super.onSaveInstanceState(savedInstanceState);
    }

    //atualiza o menu ao voltar de outra tela
    @Override
    protected void onStart() {
        super.onStart();
        limparArrays();
        popularArrays();
        atualizaAdapter();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        arrayAdapter = savedInstanceState.getIntegerArrayList("arrayAdapter");
    }



}

