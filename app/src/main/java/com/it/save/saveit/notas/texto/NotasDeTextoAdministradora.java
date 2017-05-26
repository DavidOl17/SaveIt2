package com.it.save.saveit.notas.texto;

import android.content.Context;

import com.it.save.saveit.sqlite.helper.DatabaseHelper;

import java.util.Vector;

/**
 * Created by Jorge on 24/05/2017.
 */

public class NotasDeTextoAdministradora
{
    private Vector<NotasDeTextoClass> notasTexto;
    private AdaptadorNotasDeTexto adaptador;
    public DatabaseHelper db;
    Context aux;
    public NotasDeTextoAdministradora(Context context,String categoria)
    {
        db = new DatabaseHelper(context);
        aux=context;
        notasTexto=CargaNotasDeTexto(categoria);
        adaptador=new AdaptadorNotasDeTexto(context,notasTexto);
    }
    public NotasDeTextoAdministradora(Context context)
    {
        db = new DatabaseHelper(context);
        aux=context;
        adaptador=new AdaptadorNotasDeTexto(context,notasTexto);
    }
    public Vector<NotasDeTextoClass> CargaNotasDeTexto(String categoria)
    {
        Vector<NotasDeTextoClass> notasDeTextoClasses;
        notasDeTextoClasses = db.getTodasLasNotasDeTexto(categoria);
        return  notasDeTextoClasses;
    }
    public NotasDeTextoClass getNotaDeTexto(int id)
    {
        NotasDeTextoClass notasDeTextoClasses;
        notasDeTextoClasses = db.getNotasDeTextoClass(id);
        return  notasDeTextoClasses;
    }
    public void InsertarNotaTexto(NotasDeTextoClass notasDeTextoClass)
    {
        db.insertarNotaDeTexto(notasDeTextoClass);
        db.closeDB();
    }
    public boolean EliminaNotaTexto(int id)
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
    public boolean EliminaNotaTextoDefinitivo(int id)
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
    public boolean modificarNota(NotasDeTextoClass notasDeTextoClass)
    {
        return db.updateNotaDeTexto(notasDeTextoClass);
    }
    public Vector<NotasDeTextoClass> getNotasTexto(){return notasTexto;}
    public AdaptadorNotasDeTexto getAdaptador(){return adaptador;}
}
