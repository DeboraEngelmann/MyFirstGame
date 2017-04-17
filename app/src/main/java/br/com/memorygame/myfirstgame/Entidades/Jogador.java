package br.com.memorygame.myfirstgame.Entidades;

/**
 * Created by debo_ on 04/06/2016.
 */
public class Jogador {
    private int idJogador;
    private String nome;
    private String email;
    private int progresso;


    public Jogador(int idJogador, String nome, String email, int progresso) {
        this.nome = nome;
        this.email = email;
        this.progresso = progresso;
        this.idJogador = idJogador;
    }

    public Jogador() {

    }

    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getProgresso() {
        return progresso;
    }

    public void setProgresso(int progresso) {
        this.progresso += progresso;
    }
}
