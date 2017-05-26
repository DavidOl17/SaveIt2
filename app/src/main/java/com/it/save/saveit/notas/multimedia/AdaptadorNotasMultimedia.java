package com.it.save.saveit.notas.multimedia;

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

import com.it.save.saveit.R;

import java.io.File;
import java.util.Vector;

/**
 * Created by Jorge on 25/05/2017.
 */

public class AdaptadorNotasMultimedia  extends RecyclerView.Adapter<AdaptadorNotasMultimedia.ViewHolder>
{
    private LayoutInflater inflador;
    protected Vector<NotasMultimediaClass> vectorNotasMultimedia;
    private Context contexto;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    public AdaptadorNotasMultimedia(Context contexto, Vector<NotasMultimediaClass> vectorNotasMultimedia)
    {
        inflador=(LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vectorNotasMultimedia=vectorNotasMultimedia;
        this.contexto=contexto;
    }
    public  static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tituloNota;
        public ImageView imagen;
        public ViewHolder(View itemView)
        {
            super(itemView);
            imagen=(ImageView) itemView.findViewById(R.id.imgv_archivo_multimedia);
            tituloNota=(TextView)itemView.findViewById(R.id.txt_titulo_nota_multimedia);
        }
    }

    @Override
    public AdaptadorNotasMultimedia.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = inflador.inflate(R.layout.notas_multimedia_base,null);
        v.setOnClickListener(onClickListener);
        v.setOnLongClickListener(onLongClickListener);
        return new AdaptadorNotasMultimedia.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorNotasMultimedia.ViewHolder holder, int posicion)
    {
        NotasMultimediaClass notasMultimediaClass= vectorNotasMultimedia.elementAt(posicion);
        holder.tituloNota.setText(notasMultimediaClass.getTitulo());
        String root = Environment.getExternalStorageDirectory().toString();
        File imgFile = new  File(root + "/imagenes_multimedia/"+String.valueOf(notasMultimediaClass.getId()));
        if(imgFile.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imagen.setImageBitmap(myBitmap);
        }
        else
            holder.imagen.setImageResource(R.mipmap.no_image);
    }
    @Override
    public int getItemCount()
    {
        return vectorNotasMultimedia.size();
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
