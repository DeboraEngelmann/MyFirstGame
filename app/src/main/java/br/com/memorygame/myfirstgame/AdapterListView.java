package br.com.memorygame.myfirstgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by debo_ on 15/05/2016.
 * Classe responsavel por inflar a lista de imagens
 * Utiliza o arquivo de layout "item_list"
 */

public class AdapterListView extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Integer> imagemList = new ArrayList<Integer>();

    public AdapterListView(Context context, List<Integer> imagemList) {
        //seta a lista de imagens
        this.imagemList = imagemList;
        // Objeto responsável por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return imagemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ItemSuporte itemHolder;
        //se a view estiver nula (nunca criada), inflamos o layout nela.
        if (view == null) {

            //infla o layout para podermos pegar as views
            view = mInflater.inflate(R.layout.imagem_item, null);
            //cria um item de suporte para não precisarmos sempre/ inflar as mesmas informacoes
            itemHolder = new ItemSuporte();
            itemHolder.coluna = ((ImageView) view.findViewById(R.id.coluna));


            //define os itens na view;
            view.setTag(itemHolder);
        } else {
            //se a view já existe pega os itens.
            itemHolder = (ItemSuporte) view.getTag();
        }
        Picasso.with(view.getContext())
                .load(imagemList.get(position))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .noFade().resize(500, 500)
                .centerCrop()
                .into(itemHolder.coluna);

        //retorna a view com as imagens
        return view;
    }

    /**
     * Classe de suporte para os itens do layout.
     */
    private class ItemSuporte {
        ImageView coluna;

    }
}
