package br.com.memorygame.myfirstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Override
    public void onMetodoCallBack(boolean ganhou) {
        if (ganhou) {
            if (MainActivity.jogador.getProgresso() < idLevel) {
                MainActivity.jogador.setProgresso(1);
                JogadorDao jogadorDao = JogadorDao.getInstance(getBaseContext());
                jogadorDao.updateJogador(MainActivity.jogador);
                level.setTentativas(1);
                level.setJogadasLevel(contJogada / 2);
                levelDao.updateLevel(level);
                Level proximoLevel = new Level();

                if (levelDao.getLevel(MainActivity.jogador.getProgresso()).getIdLevel() < 5) {
                    proximoLevel = levelDao.getLevel(MainActivity.jogador.getProgresso() + 1);
                    proximoLevel.setConcluido(1);
                    levelDao.updateLevel(proximoLevel);
                }
            }
            level.setTentativas(1);
            if ((contJogada / 2) < level.getJogadasLevel()) {
                level.setJogadasLevel(contJogada / 2);
            }
            levelDao.updateLevel(level);

            Intent ranking = new Intent(JogoLevels.this, Progresso.class);
            ranking.putExtra("idLevel", idLevel);
            startActivity(ranking);
            finish();
        }else{
            Toast.makeText(this, "Perdeu", Toast.LENGTH_SHORT).show();
        }
    }

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private static final int SPAN_COUNT = 5;


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

// If a layout manager has already been set, get current scroll position.
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
