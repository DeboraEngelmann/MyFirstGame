package br.com.memorygame.myfirstgame.Entidades;

/**
 * Created by debo_ on 13/04/2017.
 */

public class Imagem {
    private Integer imagem;
    private boolean clicada;
    private boolean encontrada;

    public Imagem(Integer imagem, boolean clicada) {
        this.imagem = imagem;
        this.clicada = clicada;
    }

    public Integer getImagem() {
        return imagem;
    }

    public void setImagem(Integer imagem) {
        this.imagem = imagem;
    }

    public boolean isClicada() {
        return clicada;
    }

    public void setClicada(boolean clicada) {
        this.clicada = clicada;
    }

    public boolean isEncontrada() {
        return encontrada;
    }

    public void setEncontrada(boolean encontrada) {
        this.encontrada = encontrada;
    }
}
