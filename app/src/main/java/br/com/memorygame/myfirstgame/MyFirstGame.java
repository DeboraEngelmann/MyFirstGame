package br.com.memorygame.myfirstgame;

import android.app.Application;
import java.util.ArrayList;
import br.com.memorygame.myfirstgame.Dao.JogadorDao;
import br.com.memorygame.myfirstgame.Entidades.Imagem;
import br.com.memorygame.myfirstgame.Entidades.Jogador;

/**
 * Created by debo_ on 15/05/2016.
 */
public class MyFirstGame extends Application{
    public static Jogador jogador;
    public JogadorDao jogadorDao;


    public static ArrayList<Integer> arrayAdapter = new ArrayList<Integer>();

    public MyFirstGame() {

    }

    public static ArrayList<Imagem> getImagemList(){
        ArrayList<Imagem> imagemList = new ArrayList<Imagem>();
        //Popular imagemList
        imagemList.add(new Imagem(R.drawable.i1,false));
        imagemList.add(new Imagem(R.drawable.i2,false));
        imagemList.add(new Imagem(R.drawable.i3,false));
        imagemList.add(new Imagem(R.drawable.i4,false));
        imagemList.add(new Imagem(R.drawable.i5,false));
        imagemList.add(new Imagem(R.drawable.i6,false));
        imagemList.add(new Imagem(R.drawable.i7,false));
        imagemList.add(new Imagem(R.drawable.i9,false));
        imagemList.add(new Imagem(R.drawable.i10,false));
        imagemList.add(new Imagem(R.drawable.i11,false));
        imagemList.add(new Imagem(R.drawable.i12,false));
        imagemList.add(new Imagem(R.drawable.i13,false));
        imagemList.add(new Imagem(R.drawable.i14,false));
        imagemList.add(new Imagem(R.drawable.i15,false));
        imagemList.add(new Imagem(R.drawable.i16,false));
        imagemList.add(new Imagem(R.drawable.i17,false));
        imagemList.add(new Imagem(R.drawable.i18,false));
        imagemList.add(new Imagem(R.drawable.i19,false));
        imagemList.add(new Imagem(R.drawable.i20,false));
        imagemList.add(new Imagem(R.drawable.i21,false));
        imagemList.add(new Imagem(R.drawable.i22,false));
        imagemList.add(new Imagem(R.drawable.i23,false));
        imagemList.add(new Imagem(R.drawable.i24,false));
        imagemList.add(new Imagem(R.drawable.i25,false));
        imagemList.add(new Imagem(R.drawable.i26,false));
        imagemList.add(new Imagem(R.drawable.i27,false));
        return imagemList;
    }
    //Verificar nível de jogo e passar número de Imagem.
    public static int getNumImg(int level) {
        int retorno = 0;
    switch (level){
        case 1:
            retorno = 4;
            break;
        case 2:
            retorno = 6;
            break;
        case 3:
            retorno = 8;
            break;
        case 4:
            retorno = 10;
            break;
        case 5:
            retorno = 12;
        default:
            break;
    }
    return retorno;
}
}
