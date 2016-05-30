package br.com.memorygame.myfirstgame;

import android.app.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by debo_ on 15/05/2016.
 */
public class MyFirstGame extends Application{

    public static ArrayList<Integer> imagemList = new ArrayList<Integer>();
    public static ArrayList<Integer> arrayAdapter = new ArrayList<Integer>();
    public static ArrayList<Integer> clicou = new ArrayList<Integer>();
    public static ArrayList<Integer> imagemList2 = new ArrayList<Integer>();

    public MyFirstGame() {

        //Popular imagemList
        imagemList.add(R.drawable.i1);
        imagemList.add(R.drawable.i2);
        imagemList.add(R.drawable.i3);
        imagemList.add(R.drawable.i4);
        imagemList.add(R.drawable.i5);
        imagemList.add(R.drawable.i6);
        imagemList.add(R.drawable.i7);
        imagemList.add(R.drawable.i9);
        imagemList.add(R.drawable.i10);
        imagemList.add(R.drawable.i11);
        imagemList.add(R.drawable.i12);
        imagemList.add(R.drawable.i13);
        imagemList.add(R.drawable.i14);
        imagemList.add(R.drawable.i15);
        imagemList.add(R.drawable.i16);
        imagemList.add(R.drawable.i17);
        imagemList.add(R.drawable.i18);
        imagemList.add(R.drawable.i19);
        imagemList.add(R.drawable.i20);
        imagemList.add(R.drawable.i21);
        imagemList.add(R.drawable.i22);
        imagemList.add(R.drawable.i23);
        imagemList.add(R.drawable.i24);
        imagemList.add(R.drawable.i25);
        imagemList.add(R.drawable.i26);
        imagemList.add(R.drawable.i27);
    }

    //Verificar nível de jogo e passar número de Imagens.
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
