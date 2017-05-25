package com.it.save.saveit.notas.texto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.it.save.saveit.R;

import java.util.Vector;

/**
 * Created by Jorge on 24/05/2017.
 */

public class AdaptadorNotasDeTexto extends RecyclerView.Adapter<AdaptadorNotasDeTexto.ViewHolder>
{
    private LayoutInflater inflador;
    protected Vector<NotasDeTextoClass> vectorNotasDeTexto;
    private Context contexto;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    public AdaptadorNotasDeTexto(Context contexto,Vector<NotasDeTextoClass> vectorNotasDeTexto)
    {
        inflador=(LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vectorNotasDeTexto=vectorNotasDeTexto;
        this.contexto=contexto;
    }
    public  static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView cuerpoNota;
        public TextView tituloNota;
        public ViewHolder(View itemView)
        {
            super(itemView);
            cuerpoNota=(TextView) itemView.findViewById(R.id.txt_cuerpo_nota);
            tituloNota=(TextView)itemView.findViewById(R.id.txt_titulo_nota_texto);
        }
    }

    @Override
    public AdaptadorNotasDeTexto.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = inflador.inflate(R.layout.notas_de_texto_base,null);
        v.setOnClickListener(onClickListener);
        v.setOnLongClickListener(onLongClickListener);
        return new AdaptadorNotasDeTexto.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorNotasDeTexto.ViewHolder holder, int posicion)
    {
        NotasDeTextoClass notaDeTexto= vectorNotasDeTexto.elementAt(posicion);
        holder.tituloNota.setText(notaDeTexto.getTitulo());
        holder.cuerpoNota.setText(notaDeTexto.getTexto());
    }
    @Override
    public int getItemCount()
    {
        return vectorNotasDeTexto.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener)
    {
        this.onClickListener=onClickListener;
    }
    public void setOnItemLongClickListener(View.OnLongClickListener onLongClickListener)
    {
        this.onLongClickListener=onLongClickListener;
    }
}
