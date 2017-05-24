package com.it.save.saveit.sqlite.model;

import android.content.Context;
import android.widget.Toast;

import com.it.save.saveit.CategoriaClass;
import com.it.save.saveit.MainActivity;
import com.it.save.saveit.R;
import com.it.save.saveit.sqlite.helper.DatabaseHelper;

import java.util.Vector;

/**
 * Created by Jorge on 23/05/2017.
 */

public class CategoriaAdministradora
{
    public DatabaseHelper db;
    Context aux;
    public CategoriaAdministradora(Context context)
    {
        db = new DatabaseHelper(context);
        aux=context;
    }
    public Vector<CategoriaClass> CargaLibros()
    {

        Vector<CategoriaClass> categorias;
        ///*
        if(db.getCategoriasCount()>0)
        {
            categorias = db.getTodasLasCategorias();

        }
        else
        {
            db.insertarCategoria(new CategoriaClass("Sin categoria",R.mipmap.sin_categoria_ico,0));
            db.insertarCategoria(new CategoriaClass("Papelera de reciclaje",R.mipmap.recycler_ico,0));
            categorias = db.getTodasLasCategorias();
            db.closeDB();
        }
        return  categorias;
    }
    public boolean VerificaTitulo(String titulo)
    {
        boolean R =db.existeCategoria(titulo);
        db.closeDB();
        return  R;
    }
    public void InsertarCategoria(CategoriaClass categoria)
    {
        db.insertarCategoria(categoria);
        db.closeDB();
    }
    public void EliminaCategoria(int pos)
    {
        db.deleteCategoria(CargaLibros().get(pos).getCategoria());
        db.closeDB();
    }
}
