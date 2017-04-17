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
 * Utiliza o arquivo de layout "item_list"
 */

public class AdapterListView extends RecyclerView.Adapter {
    private LayoutInflater mInflater;
    private List<Imagem> imagemList = new ArrayList<Imagem>();
    Context mContext;
    private int cont=0,contGeral=0,tentativas=0;
    private Imagem imagem1,imagem2;
    private ComunicadorComActivity mComunicador;



    public AdapterListView(Context context, List<Imagem> imagemList, Activity mActivity,int tentativas) {
        this.tentativas = tentativas;
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

            if (mImagem.isClicada() || mImagem.isEncontrada()) {
                Glide.with(mContext).load(mImagem.getImagem()).centerCrop().into(mHolder.mImageView);
            } else {
                Glide.with(mContext).load(R.drawable.logo).centerCrop().into(mHolder.mImageView);
            }
        }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return imagemList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mImageView;
        public Holder(View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.coluna);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            tentativas--;
            if (tentativas==0){
                mComunicador.onMetodoCallBack(false);
            }
            if (cont == 2){
                cont=0;
                for (Imagem item:imagemList) {
                    if (!item.isEncontrada()){
                        item.setClicada(false);
                    }
                }
            }
            if (cont==0){
                imagem1=imagemList.get(getAdapterPosition());
            }
            if (cont==1){
                imagem2=imagemList.get(getAdapterPosition());
                if (imagem1.getImagem()==imagem2.getImagem()){
                    int p=imagemList.indexOf(imagem1);
                    imagemList.get(p).setEncontrada(true);
                    int p2=imagemList.indexOf(imagem2);
                    imagemList.get(p2).setEncontrada(true);
                    contGeral=contGeral+2;
                }
            }
            if (contGeral==imagemList.size()){
                mComunicador.onMetodoCallBack(true);
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
    public static interface ComunicadorComActivity{
        void onMetodoCallBack(boolean ganhou);
    }

}
