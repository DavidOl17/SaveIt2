package com.it.save.saveit.notas.multimedia;

/**
 * Created by Jorge on 25/05/2017.
 */

public class NotasMultimediaClass
{
    private int id;
    private int imagen;
    private String titulo;
    private String categoria;
    public  NotasMultimediaClass(){}
    public  NotasMultimediaClass(int id, String titulo)
    {
        this.id=id;
        this.titulo=titulo;
        this.imagen=id;
        this.categoria="Sin categoria";
    }
    public  NotasMultimediaClass(int id, String titulo, String categoria)
    {
        this.id=id;
        this.imagen=id;
        this.titulo=titulo;
        this.categoria=categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getImagen() { return imagen; }

    public void setImagen(int imagen) { this.imagen = imagen; }
}
