package br.com.memorygame.myfirstgame;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private LayoutInflater mInflater;
    private List<Imagem> imagemList = new ArrayList<Imagem>();
    Context mContext;
    private int cont=0,contGeral=0,cliques=0;
    private Imagem imagem1,imagem2;
    private ComunicadorComActivity mComunicador;



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
            //Se a imagem está clicada ou já foi encontrada a correspondente mantém ela aberta
            if (mImagem.isClicada() || mImagem.isEncontrada()) {
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
    public int getItemCount() {
        return imagemList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mImageView;

        public Holder(View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.imagem_nivel);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cliques--;
            if (cliques==0){
                mComunicador.onMetodoCallBack(false, cliques);
            }
            //se é a segunda imagem que abre e não encontrou a correspondente, esconde as duas
            if (cont == 2){
                cont=0;
                for (Imagem item:imagemList) {
                    if (!item.isEncontrada()){
                        item.setClicada(false);
                    }
                }
            }
            //abre a primeira imagem clicada
            if (cont==0 && (contGeral!=imagemList.size())){
                imagem1=imagemList.get(getAdapterPosition());
            }
            //Abre a segunda imagem clicada
            if (cont==1){
                imagem2=imagemList.get(getAdapterPosition());
                //se forem correspondentes seta como encontrada
                if (imagem1.getImagem()==imagem2.getImagem()){
                    int p=imagemList.indexOf(imagem1);
                    imagemList.get(p).setEncontrada(true);
                    int p2=imagemList.indexOf(imagem2);
                    imagemList.get(p2).setEncontrada(true);
                    contGeral=contGeral+2;
                }
            }
            //Se todas a imagens foram encontradas seta ganhou como true
            if (contGeral==imagemList.size()){
                mComunicador.onMetodoCallBack(true, cliques);
            }
            cont++;
            imagemList.get(getAdapterPosition()).setClicada(true);
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }
    //Classe responsável por controlar a variavel ganhou
    public static interface ComunicadorComActivity{
        void onMetodoCallBack(boolean ganhou, int cliques);
    }
}
