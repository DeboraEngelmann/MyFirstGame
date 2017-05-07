package br.com.memorygame.myfirstgame;

import android.app.Activity;
import android.content.Intent;
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
    private long lastBackPressTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Contrutor da classe pai
        setContentView(R.layout.activity_cadastro_jogador); //pega o xml da activity_cadastro_jogador
        //vincula com os campos da activity
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        btnOk = (Button) findViewById(R.id.btnOk);

        //Clique no botão ok:
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Salva os dados vindos da activity dentro das variaveis nome e email
                String nome = txtNome.getText().toString();
                String email = txtEmail.getText().toString();

                //Se os dados não estiverem em branco, salva no banco
                if (nome.trim().equals("")){ //trim tira os espaços do inicio e do final
                    txtNome.requestFocus();
                    txtNome.setError("O campo nome é obrigatório!");
                    return;
                }
                if (email.trim().equals("")) {
                    txtEmail.requestFocus();
                    txtEmail.setError("O campo e-mail é obrigatório!");
                    return;
                }
                //Instancia um novo jogador
                Jogador jogador = new Jogador();
                jogador.setNome(nome);
                jogador.setEmail(email);

                //salvar no banco
                JogadorDao jogadorDao = JogadorDao.getInstance(getBaseContext());
                jogadorDao.insertJogador(jogador);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
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
