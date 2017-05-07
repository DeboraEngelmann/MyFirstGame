package br.com.memorygame.myfirstgame;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.memorygame.myfirstgame.Entidades.Imagem;

/**
 * Created by debo_ on 06/04/2017.
 * Classe responsavel por inflar a lista de imagens
 */

public class AdapterListView extends RecyclerView.Adapter {
    private List<Imagem> imagemList = new ArrayList<Imagem>();
    Context mContext;
    private ComunicadorComActivity mComunicador;
    private int cliques=0, encontradas=0;
    private int posicaoImagemVirada1=-1;
    private int posicaoImagemVirada2=-1;
    private int posicaoAtual;
    private boolean duasImagensViradas=false;



    public AdapterListView(Context context, List<Imagem> imagemList, Activity mActivity,int cliques) {
        this.cliques = cliques;
        //seta a lista de imagens
        this.imagemList = imagemList;
        this.mContext = context;
        this.mComunicador = (ComunicadorComActivity) mActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(this.mContext).inflate(R.layout.imagem_item,parent,false);
        Holder mHolder = new Holder(mView);

        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder = (Holder) holder;
        final Imagem mImagem = imagemList.get(position);
            //Se a imagem está virada ou já foi encontrada a correspondente mostra imagem
            if (mImagem.isVirada() || mImagem.isEncontrada()) {
                Glide.with(mContext).load(mImagem.getImagem()).into(mHolder.mImageView);
            }
            //Se não, mostra o logo
            else {
                Glide.with(mContext).load(R.drawable.logo).into(mHolder.mImageView);
            }
        }

    @Override //Retorna a posição da imagem
    public long getItemId(int position) {
        return position;
    }

    @Override //Retorna a quantidade de imagens
    public int getItemCount() { return imagemList.size(); }
    //holder é a forma de uma das peças do gridview (uma imagem)
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mImageView;

        public Holder(View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.imagem_nivel);
            mImageView.setOnClickListener(this);
        }
        //Quando clica na imagem:
        @Override
        public void onClick(View v) {
            posicaoAtual=getAdapterPosition();
            //Se clicar em uma imagem que já foi encontrada não faz nada
            if (imagemJaEncontrada(posicaoAtual)){
                return;
            }
            cliques--;
            //Se terminou a quantidade de cliques perde o jogo
            if (cliques == -1) mComunicador.onMetodoCallBack(false, 0);
            //Se tem duas imagens diferentes viradas esconde elas
            if (duasImagensViradas){
                esconderImagem(posicaoImagemVirada1);
                esconderImagem(posicaoImagemVirada2);
                posicaoImagemVirada1=-1;
                posicaoImagemVirada2=-1;
                duasImagensViradas=false;
                atualizaTela();
            }
            //Se não tem nenhuma imagem virada salva a posição do clique na posicaoImagemVirada1 e mostra ela
            if (!temImagemVirada()){
                posicaoImagemVirada1=posicaoAtual;
                mostrarImagem(posicaoAtual);
                atualizaTela();
                return;
            }else {//Se tem alguma imagem virada verifica se foi clicado na mesma, se sim, esconde ela
                if (posicaoImagemVirada1==posicaoAtual){
                    esconderImagem(posicaoAtual);
                    posicaoImagemVirada1=-1;
                    atualizaTela();
                    return;
                }else{ //Se não foi clicado na mesma compara as duas selecionadas pra ver se são iguais
                    if (saoIguais(posicaoImagemVirada1,posicaoAtual)){
                        marcarComoEncontrada(posicaoImagemVirada1,posicaoAtual); //Se são iguais marca como encontrada
                        encontradas++;
                        encontradas++;
                        atualizaTela();
                        posicaoImagemVirada1=-1;
                        //Verifica se todas foram encontradas, se sim, ganhou o jogo.
                        if (encontradas == imagemList.size()){
                            mComunicador.onMetodoCallBack(true, cliques);
                        }
                        return;
                    }else{//Se as imagens não são iguais salva a segunda em posicaoImagemVirada2
                        posicaoImagemVirada2=posicaoAtual;
                        mostrarImagem(posicaoAtual);
                        duasImagensViradas=true; //guarda a informação de que já tem duas imagens diferentes viradas
                        atualizaTela();
                        return;
                    }
                }

            }

        }
    }
    //Classe responsável por enviar informação para a tela de Progresso
    public interface ComunicadorComActivity{
        void onMetodoCallBack(boolean ganhou, int cliques);
    }
    //Classe responsável por esconder imagem
    private void esconderImagem(int posicao){
        imagemList.get(posicao).setVirada(false);
    }
    //Classe responsável por mostrar imagem
    private void mostrarImagem(int posicao){
        imagemList.get(posicao).setVirada(true);
    }
    //Classe responsável por marcar as duas imagens como encontradas
    private void marcarComoEncontrada(int posicao1, int posicao2){
        imagemList.get(posicao1).setEncontrada(true);
        imagemList.get(posicao2).setEncontrada(true);
        esconderImagem(posicao1); //para marcar como virada false.
        esconderImagem(posicao2);
    }
    //Classe responsável por verificar se tem alguma imagem virada
    private boolean temImagemVirada(){
        for (Imagem item: imagemList) {
            if (item.isVirada()){
                return true;
            }
        }
        return false;
    }
    //Classe responsável por atualizar a tela
    private void atualizaTela(){
        ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Atualiza os dados  do recyclerview
                    notifyDataSetChanged();
                }
            });
    }
    //Classe responsável por verificar se duas imagens são iguais
    private boolean saoIguais(int posicao1,int posicao2){
        return imagemList.get(posicao1).getImagem() == (imagemList.get(posicao2).getImagem());
    }
    //Classe responsável por verificar se uma imagem já está marcada como encontrada
    private boolean imagemJaEncontrada(int posicao){
        return (imagemList.get(posicao).isEncontrada());
    }
}
