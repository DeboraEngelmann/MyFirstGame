package br.com.memorygame.myfirstgame.Entidades;

/**
 * Created by debo_ on 04/06/2016.
 */
public class Level {
    private int idLevel;
    private int tentativas;
    private int cliques;
    private boolean concluido;


    public int isConcluido() {
        int retorno;
        if (concluido==true){
            retorno=1;
        }else {
            retorno=0;
        }
        return retorno;
    }

    public void setConcluido(int concluido) {
        this.concluido = concluido == 1;
    }

    public Level(int idLevel, int tentativas, int cliques, int concluido) {
        this.idLevel = idLevel;
        this.tentativas = tentativas;
        this.cliques = cliques;
        setConcluido(concluido);

    }

    public Level() {

    }


    public int getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public int getCliques() {
        return cliques;
    }

    public void setCliques(int cliques) {
        this.cliques = cliques;
    }

    public int getTentativas() {
        return tentativas;
    }

    public void setTentativas(int tentativas) {
        this.tentativas += tentativas;
    }

    @Override
    public String toString() {
        return "Level{" +
                "idLevel=" + idLevel +
                '}';
    }
}
