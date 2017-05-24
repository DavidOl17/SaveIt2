package com.it.save.saveit;

import android.app.Application;
import android.media.Image;
import android.os.Bundle;

import com.it.save.saveit.sqlite.model.CategoriaAdministradora;

import java.util.Vector;

/**
 * Created by Jorge on 22/05/2017.
 */

public class Categoria extends Application
{
    private Vector<CategoriaClass> categorias;
    private AdaptadorCategorias adaptador;
    @Override
    public void onCreate()
    {
        super.onCreate();
        CategoriaAdministradora aux = new CategoriaAdministradora(this);
        categorias = aux.CargaLibros();
        adaptador=new AdaptadorCategorias(this,categorias);
    }
    public Vector<CategoriaClass> getCategorias() {
        return categorias;
    }
    public AdaptadorCategorias getAdaptador()
    {
        return adaptador;
    }
}
