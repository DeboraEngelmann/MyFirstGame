package br.com.memorygame.myfirstgame.Entidades;

/**
 * Created by debo_ on 13/04/2017.
 */

public class Imagem {
    private Integer imagem;
    private boolean virada;
    private boolean encontrada;

    public Imagem(Integer imagem, boolean virada) {
        this.imagem = imagem;
        this.virada = virada;
    }

    public Integer getImagem() {
        return imagem;
    }

    public void setImagem(Integer imagem) {
        this.imagem = imagem;
    }

    public boolean isVirada() {
        return virada;
    }

    public void setVirada(boolean virada) {
        this.virada = virada;
    }

    public boolean isEncontrada() {
        return encontrada;
    }

    public void setEncontrada(boolean encontrada) {
        this.encontrada = encontrada;
    }
}
