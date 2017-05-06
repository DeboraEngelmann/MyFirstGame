package br.com.memorygame.myfirstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import br.com.memorygame.myfirstgame.Dao.JogadorDao;
import br.com.memorygame.myfirstgame.Dao.LevelDao;
import br.com.memorygame.myfirstgame.Entidades.Imagem;
import br.com.memorygame.myfirstgame.Entidades.Level;

public class JogoLevels extends Activity implements AdapterListView.ComunicadorComActivity {
    private RecyclerView mRecyclerView;
    public static AdapterListView mAdapter;
    static ArrayList<Integer> arrayAdapter = MyFirstGame.arrayAdapter;
    static ArrayList<Imagem> imagemListNivel = new ArrayList<Imagem>();

    static int contJogada = 0;
    static public int idLevel;
    private static int fim;
    private LevelDao levelDao;
    private static Level level;
    private static int tentativas=0;


    @Override
    //Recebe a informação se o nível foi concluido por terminar tentativas ou por ganhar o jogo
    public void onMetodoCallBack(boolean ganhou, int cliques) {
        level.setTentativas(1); //Incrementa tentativas
        if (ganhou) {
            //Se o progresso desse nivel ainda não está resgistrado, registra
            if (MainActivity.jogador.getProgresso() < idLevel) {
                MainActivity.jogador.setProgresso(1); // soma 1 no progresso do nível
                JogadorDao jogadorDao = JogadorDao.getInstance(getBaseContext());
                jogadorDao.updateJogador(MainActivity.jogador); //Atualiza o jogador no banco
                levelDao.updateLevel(level); // Atualiza o level no banco
                Level proximoLevel = new Level();

                //Se tem mais níveis para liberar, Libera o proximo
                if (levelDao.getLevel(MainActivity.jogador.getProgresso()).getIdLevel() < 10) {
                    proximoLevel = levelDao.getLevel(MainActivity.jogador.getProgresso() + 1);
                    proximoLevel.setConcluido(1);
                    levelDao.updateLevel(proximoLevel);
                }
            }
            //Se a quantidade de cliques que restam é maior do que a registrada, atualiza
            //Pense: cliques=pontos
            if (cliques > level.getCliques()) {
                level.setCliques(cliques);
            }
        //Se terminar a quantidade estipulada de cliques o jogador perde
        }else{
            Toast.makeText(this, "Perdeu", Toast.LENGTH_SHORT).show();
        }
        //Abre a activity do ranking
        levelDao.updateLevel(level);
        Intent ranking = new Intent(JogoLevels.this, Progresso.class);
        ranking.putExtra("idLevel", idLevel);
        ranking.putExtra("cliques", cliques);
        startActivity(ranking);
        finish();
    }

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private static final int SPAN_COUNT = 5;

    //Inicia o jogo
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contJogada = 0;
        levelDao = LevelDao.getInstance(getBaseContext());
        level = levelDao.getLevel(idLevel);

        setContentView(R.layout.jogo_levels);
        mRecyclerView = (RecyclerView) findViewById(R.id.recicleImage);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        limparArrays();
        popularArrays();
        sortear();
        atualizaAdapter();
    }

    private void atualizaAdapter() {
        mAdapter = new AdapterListView(JogoLevels.this, imagemListNivel, this,MyFirstGame.getNumImg(idLevel)*2*3);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void limparArrays() {
        arrayAdapter.clear();
        imagemListNivel.clear();
    }

    private void popularArrays() {
        ArrayList<Imagem> imagemList = MyFirstGame.getImagemList();
        //Popular arrayAdapter
        Collections.shuffle(imagemList);//Sorteio de Imagem
        ArrayList<Imagem> listaImagem = new ArrayList<Imagem>();
        imagemListNivel.clear();
        listaImagem.clear();
        //Coloca as imagens dentro do Array imagemListNivel
        for (int i = 0; i < MyFirstGame.getNumImg(idLevel); i++) {
            imagemListNivel.add(imagemList.get(i));
        }
        for (Imagem item : imagemListNivel) {
            Imagem i = new Imagem(item.getImagem(), false);
            listaImagem.add(i);
        }
        imagemListNivel.addAll(listaImagem);
    }

    private void sortear() {
        //Sorteio de Posição
        Collections.shuffle(imagemListNivel);
    }

   public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

// Se um gerenciador de layout já tiver sido definido, obtem a posição de rolagem atual.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getApplicationContext(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }
}
