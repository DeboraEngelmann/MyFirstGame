package br.com.memorygame.myfirstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.memorygame.myfirstgame.Dao.LevelDao;
import br.com.memorygame.myfirstgame.Entidades.Level;

public class Progresso extends Activity {
    private static TextView txtLevel;
    private static TextView txtTentativas;
    private static TextView txtMaior;
    private static Button btnNovamente;
    private static Button btnProxima;
    Level level;
    LevelDao levelDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        levelDao = LevelDao.getInstance(getBaseContext());
        final int idLevel = it.getIntExtra("idLevel",0);

        level = levelDao.getLevel(idLevel);

        setContentView(R.layout.activity_progresso);
        txtLevel = (TextView) findViewById(R.id.txtLevel);
        txtTentativas = (TextView) findViewById(R.id.txtTentativas);
        txtMaior = (TextView) findViewById(R.id.txtMaior);
        btnNovamente = (Button) findViewById(R.id.btnNovamente);
        btnProxima = (Button) findViewById(R.id.btnProxima);
        txtTentativas.setText(String.valueOf(level.getTentativas()));
        txtMaior.setText(String.valueOf(level.getJogadasLevel()));
        txtLevel.setText(String.valueOf("Nível " + idLevel));
        btnNovamente.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent jogoLevel = new Intent(Progresso.this, JogoLevels.class);
                JogoLevels.idLevel = idLevel;
                startActivity(jogoLevel);
                finish();

            }
        });
        btnProxima.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent jogoLevel = new Intent(Progresso.this, JogoLevels.class);
                JogoLevels.idLevel = idLevel + 1;
                startActivity(jogoLevel);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}
