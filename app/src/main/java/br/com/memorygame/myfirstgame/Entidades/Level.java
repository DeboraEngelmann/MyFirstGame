package br.com.memorygame.myfirstgame.Entidades;

/**
 * Created by debo_ on 04/06/2016.
 */
public class Level {
    private int idLevel;
    private int tentativas;
    private int jogadasLevel;
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
        if (concluido == 1){
            this.concluido = true;
        }else{
            this.concluido = false;
        }
    }

    public Level(int idLevel, int tentativas, int jogadasLevel, int concluido) {
        this.idLevel = idLevel;
        this.tentativas = tentativas;
        this.jogadasLevel = jogadasLevel;
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

    public int getJogadasLevel() {
        return jogadasLevel;
    }

    public void setJogadasLevel(int jogadasLevel) {
        this.jogadasLevel = jogadasLevel;
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
