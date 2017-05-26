package com.it.save.saveit.notas.multimedia;

import android.content.Context;

import com.it.save.saveit.sqlite.helper.DatabaseHelper;

import java.util.Vector;

/**
 * Created by Jorge on 25/05/2017.
 */

public class NotasMultimediaAdministradora
{
    private Vector<NotasMultimediaClass> notasMultimedia;
    private AdaptadorNotasMultimedia adaptador;
    public DatabaseHelper db;
    Context aux;
    public NotasMultimediaAdministradora(Context context,String categoria)
    {
        db = new DatabaseHelper(context);
        aux=context;
        notasMultimedia=CargaNotasMultimedia(categoria);
        adaptador=new AdaptadorNotasMultimedia(context,notasMultimedia);
    }
    public NotasMultimediaAdministradora(Context context)
    {
        db = new DatabaseHelper(context);
        aux=context;
        adaptador=new AdaptadorNotasMultimedia(context,notasMultimedia);
    }
    public Vector<NotasMultimediaClass> CargaNotasMultimedia(String categoria)
    {
        Vector<NotasMultimediaClass> notasMultimediaClasses;
        notasMultimediaClasses = db.getTodasLasNotasMultimedia(categoria);
        return  notasMultimediaClasses;
    }
    public NotasMultimediaClass getNotaMultimedia(int id)
    {
        NotasMultimediaClass notasMultimediaClass;
        notasMultimediaClass = db.getNotasMultimedia(id);
        return  notasMultimediaClass;
    }
    public void InsertarNotaMultimedia(NotasMultimediaClass notasMultimediaClass)
    {
        db.insertarNotaMultimedia(notasMultimediaClass);
        db.closeDB();
    }
    public boolean EliminaNotaMultimedia(int id)
    {
        try
        {
            db.deleteNotaTextoSinCategoria(id);
            db.closeDB();
        }
        catch (Exception e)
        {return false;}
        return true;
    }
    public boolean EliminaNotaMultimediaDefinitivo(int id)
    {
        try
        {
            db.deleteNotaTexto(id);
            db.closeDB();
        }
        catch (Exception e)
        {return false;}
        return true;
    }
    public boolean modificarNotaMultimedia(NotasMultimediaClass notasMultimediaClass)
    {
        return db.updateNotaMultimedia(notasMultimediaClass);
    }
    public int getLasiID()
    {
        try {
            return db.lastID();
        }catch (Exception e) {

        }
        return 0;
    }
    public Vector<NotasMultimediaClass> getNotasTexto(){return notasMultimedia;}
    public AdaptadorNotasMultimedia getAdaptador(){return adaptador;}
}
