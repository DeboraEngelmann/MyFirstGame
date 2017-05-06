package br.com.memorygame.myfirstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

/**
 * Responsável pela tela activity_main
 */
public class MainActivity extends Activity {
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
        //se não tiver jogadores abre a activity para cadastrar jogador
        if (jogadores.size()<1){
            Intent cadastroJogador = new Intent(this, CadastroJogador.class);
            startActivity(cadastroJogador);
        }
        //se não, pega o jogador cadastrado
        else {
            jogador = jogadores.get(0);
        }
        //monta o menu
        setContentView(R.layout.activity_main);
        gridViewImageMenu = (GridView) findViewById(R.id.gridViewImageMenu);

        //Limpa os dados e carrega ao iniciar
        if(savedInstanceState==null){
            limparArrays();
            popularArrays();
            atualizaAdapter();
        }
        //se já existe uma instancia salva, seta ela.
        else{
            gridViewImageMenu.setAdapter(mAdapter);
        }

        gridViewImageMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //se conclui um nível, libera o proximo
                if(levels.get(position).isConcluido()==1) {
                    Intent jogoLevel = new Intent(MainActivity.this, JogoLevels.class);
                    JogoLevels.idLevel = position + 1;
                    startActivity(jogoLevel);
                }
                //se tentar abrir um nível bloqueado, mostra a mensagem
                else{
                    //mostrar mensagem
                    Toast.makeText(getApplicationContext(),
                            "Errroooooou.", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });
    }
    //Classe responsável por atualizar o adapter de imagens do menu
    public void atualizaAdapter(){
        mAdapter = new AdapterMenu(MainActivity.this, arrayAdapter);
        gridViewImageMenu.setAdapter(mAdapter);
    }
    //Classe responsável por limpar os arrays
    private void limparArrays(){
        arrayAdapter.clear();
    }
    //Classe responsável por popular os Arrays
    private void popularArrays(){

        levels = leveldao.selectTodosOsLevels();

        if (levels.size()==0){
            Level level = new Level();
            level.setTentativas(0);
            level.setCliques(0);
            level.setConcluido(0);

            Level level1 = new Level();
            level1.setTentativas(0);
            level1.setCliques(0);
            level1.setConcluido(1);

            for (int i=0; i<=9; i++){
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
    //Classe responsável por mostrar a imagem de nível não liberado
    public static void setImagemNaoConcluido(int level) {
        switch (level) {
            case 0:
                arrayAdapter.add(R.drawable.level1block);
                break;
            case 1:
                arrayAdapter.add(R.drawable.level2block);
                break;
            case 2:
                arrayAdapter.add(R.drawable.level3block);
                break;
            case 3:
                arrayAdapter.add(R.drawable.level4block);
                break;
            case 4:
                arrayAdapter.add(R.drawable.level5block);
                break;
            case 5:
                arrayAdapter.add(R.drawable.level6block);
                break;
            case 6:
                arrayAdapter.add(R.drawable.level7block);
                break;
            case 7:
                arrayAdapter.add(R.drawable.level8block);
                break;
            case 8:
                arrayAdapter.add(R.drawable.level9block);
                break;
            case 9:
                arrayAdapter.add(R.drawable.level10block);
            default:
                break;
        }
    }
    //Classe responsável por mostrar a imagem de nível liberado
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
                break;
            case 5:
                arrayAdapter.add(R.drawable.level6);
                break;
            case 6:
                arrayAdapter.add(R.drawable.level7);
                break;
            case 7:
                arrayAdapter.add(R.drawable.level8);
                break;
            case 8:
                arrayAdapter.add(R.drawable.level9);
                break;
            case 9:
                arrayAdapter.add(R.drawable.level10);
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

