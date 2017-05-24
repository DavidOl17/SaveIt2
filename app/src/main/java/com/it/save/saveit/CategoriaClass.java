package com.it.save.saveit;

import android.media.Image;

import com.it.save.saveit.sqlite.helper.DatabaseHelper;

import java.util.List;
import java.util.Vector;

/**
 * Created by Jorge on 22/05/2017.
 */

public class CategoriaClass
{

    public CategoriaClass(String categoria, int image, int id)
    {
        this.image=image;
        this.categoria=categoria;
        this.id=id;
        //db = new DatabaseHelper(MainActivity.getmContext());
    }

    public String getCategoria() {
        return categoria;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String categoria;
    private int image;
    private int id;
}
