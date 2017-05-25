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
    public Vector<NotasDeTextoClass> CargaNotasDeTexto(String categoria)
    {
        Vector<NotasDeTextoClass> notasDeTextoClasses;
        notasDeTextoClasses = db.getTodasLasNotasDeTexto(categoria);
        return  notasDeTextoClasses;
    }
    public void InsertarCategoria(NotasDeTextoClass notasDeTextoClass)
    {
        db.insertarNotaDeTexto(notasDeTextoClass);
        db.closeDB();
    }
    public void EliminaCategoria(int id)
    {
        db.deleteNotaTexto(id);
        db.closeDB();
    }

    public Vector<NotasDeTextoClass> getNotasTexto(){return notasTexto;}
    public AdaptadorNotasDeTexto getAdaptador(){return adaptador;}
}
