package com.it.save.saveit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Vector;

/**
 * Created by Jorge on 22/05/2017.
 */

public class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolder>
{
    private LayoutInflater inflador;
    protected Vector<CategoriaClass> vectorCategorias;
    private Context contexto;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    public AdaptadorCategorias(Context contexto,Vector<CategoriaClass> vectorCategorias)
    {
        inflador=(LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vectorCategorias=vectorCategorias;
        this.contexto=contexto;
    }
    public  static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView fotoCategoria;
        public TextView tituloCategoria;
        public ViewHolder(View itemView)
        {
            super(itemView);
            fotoCategoria=(ImageView) itemView.findViewById(R.id.portada_categoria);
            fotoCategoria.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            tituloCategoria=(TextView)itemView.findViewById(R.id.titulo);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = inflador.inflate(R.layout.seleccion_nota,null);
        v.setOnClickListener(onClickListener);
        v.setOnLongClickListener(onLongClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion)
    {
        CategoriaClass categoria= vectorCategorias.elementAt(posicion);
        holder.tituloCategoria.setText(categoria.getCategoria());
        if(categoria.getImage()!=-1)
            holder.fotoCategoria.setImageResource(categoria.getImage());
        else
        {
            String root = Environment.getExternalStorageDirectory().toString();
            File imgFile = new  File(root + "/imagenes_categorias/"+categoria.getCategoria());
            if(imgFile.exists())
            {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.fotoCategoria.setImageBitmap(myBitmap);
            }
            else
                holder.fotoCategoria.setImageResource(R.mipmap.no_image);
        }
    }
    @Override
    public int getItemCount()
    {
        return vectorCategorias.size();
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
