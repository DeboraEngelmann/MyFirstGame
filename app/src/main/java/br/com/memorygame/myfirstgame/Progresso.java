package br.com.memorygame.myfirstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.memorygame.myfirstgame.Dao.LevelDao;
import br.com.memorygame.myfirstgame.Entidades.Level;

/**
 * Responsável pela tela activity_progresso
 */
public class Progresso extends Activity {
    private static TextView txtLevel;
    private static TextView txtTentativas;
    private static TextView txtMaior;
    private static TextView txtAtual;
    private static Button btnNovamente;
    private static Button btnProxima;
    private static Button btnVoltarAoMenu;
    Level level;
    LevelDao levelDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        levelDao = LevelDao.getInstance(getBaseContext());
        //recebe os parametros da outra tela
        final int idLevel = it.getIntExtra("idLevel",0);
        final int cliques = it.getIntExtra("cliques",0);

        level = levelDao.getLevel(idLevel);

        //Vincula TextViews da tela
        setContentView(R.layout.activity_progresso);
        txtLevel = (TextView) findViewById(R.id.txtLevel);
        txtTentativas = (TextView) findViewById(R.id.txtTentativas);
        txtMaior = (TextView) findViewById(R.id.txtMaior);
        txtAtual = (TextView) findViewById(R.id.txtAtual);

        //Vincula botoes da tela
        Animation teste = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animacao);
        findViewById(R.id.tentativas).startAnimation(teste);
        findViewById(R.id.pontuacao_atual).startAnimation(teste);
        findViewById(R.id.maior_pontuacao).startAnimation(teste);
        btnNovamente = (Button) findViewById(R.id.btnNovamente);
        btnProxima = (Button) findViewById(R.id.btnProxima);
        btnVoltarAoMenu = (Button) findViewById(R.id.btnVoltarAoMenu);

        //Passa os respectivos valores para os TextViews da tela
        txtTentativas.setText(String.valueOf(level.getTentativas()));
        txtAtual.setText(String.valueOf(cliques));
        txtLevel.setText(String.valueOf("Nível " + idLevel));
        txtMaior.setText(String.valueOf(level.getCliques()));

        if (idLevel==10){
            btnProxima.setVisibility(View.INVISIBLE);
        }

        //Botão Novamente chama o mesmo nível outra vez
        btnNovamente.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent jogoLevel = new Intent(Progresso.this, JogoLevels.class);
                JogoLevels.idLevel = idLevel;
                startActivity(jogoLevel);
                finish();

            }
        });
        //Botão proxima chama a proxima fase
        btnProxima.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (levelDao.getLevel(idLevel+1).isConcluido()==1){
                    Intent jogoLevel = new Intent(Progresso.this, JogoLevels.class);
                    JogoLevels.idLevel = idLevel + 1;
                    startActivity(jogoLevel);
                    finish();
                }else {
                    //mostrar mensagem
                    Toast.makeText(getApplicationContext(),
                            "Sinto Muito! Para acessar o próximo nível é necessário primeiro concluir este!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });
        //Botão home, volta ao menu principal
        btnVoltarAoMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //Botão voltar do Android, volta ao menu principal
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}
