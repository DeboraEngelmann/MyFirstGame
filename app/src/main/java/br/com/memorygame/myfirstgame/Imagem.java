package br.com.memorygame.myfirstgame;

import java.net.URI;

/**
 * Created by debo_ on 15/05/2016.
 */
public class Imagem {
    private String src;

    public Imagem(String src) {
        this.src = src;
    }

    public String getUri() {
        return src;
    }

    @Override
    public String toString() {
        return "file://" + src;
    }
}
