package br.com.memorygame.myfirstgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.memorygame.myfirstgame.Dao.JogadorDao;
import br.com.memorygame.myfirstgame.Entidades.Jogador;

public class CadastroJogador extends AppCompatActivity {
    private static EditText txtNome;
    private static EditText txtEmail;
    private static Button btnOk;
    private static Button btnLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jogador);
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnLimpar = (Button) findViewById(R.id.btnLimpar);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String nome = txtNome.getText().toString();
                String email = txtEmail.getText().toString();
                if (nome!="" && email!= "") {
                    Jogador jogador = new Jogador();
                    jogador.setNome(nome);
                    jogador.setEmail(email);

                    //salvar no banco
                    JogadorDao jogadorDao = JogadorDao.getInstance(getBaseContext());
                    jogadorDao.insertJogador(jogador);
                    MainActivity.jogador = jogador;
                    finish();

                }else{
                    //mostrar mensagem
                    Toast.makeText(getApplicationContext(),
                            "Dados inválidos, Informe o Nome e o Email corretamente.", Toast.LENGTH_LONG)
                            .show();
                }

            }
        }
        );


        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LimparCampos();

            }
        }
        );


    }

    public void LimparCampos(){
        txtNome.setText("");
        txtEmail.setText("");
    }
}
