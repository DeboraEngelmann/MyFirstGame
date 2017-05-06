package br.com.memorygame.myfirstgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.memorygame.myfirstgame.Dao.JogadorDao;
import br.com.memorygame.myfirstgame.Entidades.Jogador;

/**
 * Responsável pela tela activity_cadastro_jogador
 */

public class CadastroJogador extends Activity {
    private static EditText txtNome;
    private static EditText txtEmail;
    private static Button btnOk;
    private static Button btnLimpar;
    private long lastBackPressTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jogador);
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnLimpar = (Button) findViewById(R.id.btnLimpar);

        //Clique no botão ok:
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String nome = txtNome.getText().toString();
                String email = txtEmail.getText().toString();
                //Se os dados não estiverem em branco, salva no banco
                if (nome!="" && email!= "") {
                    Jogador jogador = new Jogador();
                    jogador.setNome(nome);
                    jogador.setEmail(email);

                    //salvar no banco
                    JogadorDao jogadorDao = JogadorDao.getInstance(getBaseContext());
                    jogadorDao.insertJogador(jogador);
                    MainActivity.jogador = jogador;
                    finish();
                 //Se não, mostra a mensagem
                }else{
                    //mostrar mensagem
                    Toast.makeText(getApplicationContext(),
                            "Dados inválidos, Informe o Nome e o Email corretamente.", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });
        //Botão limpar quando clicado limpa os campos
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
    @Override
    public void onBackPressed() {
        //Se clicar no botão voltar pede para pressionar novamente para fechar o app
        if (this.lastBackPressTime < System.currentTimeMillis() - 2000) {
            toast = Toast.makeText(this, "Pressione o Botão Voltar novamente para fechar o Aplicativo.", Toast.LENGTH_SHORT);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            moveTaskToBack(true); //fecha o app
        }
    }
}
