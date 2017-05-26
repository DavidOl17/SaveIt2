package com.it.save.saveit.sqlite.model;

import android.content.Context;
import android.os.Environment;

import com.it.save.saveit.AdaptadorCategorias;
import com.it.save.saveit.CategoriaClass;
import com.it.save.saveit.R;
import com.it.save.saveit.sqlite.helper.DatabaseHelper;

import java.io.File;
import java.util.Vector;

/**
 * Created by Jorge on 23/05/2017.
 */

public class CategoriaAdministradora
{
    private Vector<CategoriaClass> categorias;
    private AdaptadorCategorias adaptador;
    public DatabaseHelper db;
    Context aux;
    public CategoriaAdministradora(Context context)
    {
        db = new DatabaseHelper(context);
        aux=context;
        categorias=CargaLibros();
        adaptador=new AdaptadorCategorias(context,categorias);
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
    public String EliminaCategoria(int pos)
    {
        String categ =CargaLibros().get(pos).getCategoria();
        if(!categ.equals("Sin categoria") && !categ.equals("Papelera de reciclaje"))
        {
            String root = Environment.getExternalStorageDirectory().toString();
            File imgFile = new  File(root + "/imagenes_categorias/"+categ);
            if(imgFile.exists())
            {
                imgFile.delete();
            }
            db.updateCategoriaNota(categ);
            db.deleteCategoria(CargaLibros().get(pos).getCategoria());
            db.closeDB();
        }
        else
            return "La categoria no se puede eliminar";
        return "Eliminado exitosamente";
    }
    public String getCategoria(int pos)
    {
        return CargaLibros().get(pos).getCategoria();
    }

    public Vector<CategoriaClass> getCategorias() {
        return categorias;
    }
    public AdaptadorCategorias getAdaptador()
    {
        return adaptador;
    }
}
