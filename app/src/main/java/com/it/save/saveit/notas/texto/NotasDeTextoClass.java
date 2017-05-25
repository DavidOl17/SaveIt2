package com.it.save.saveit.notas.texto;

/**
 * Created by Jorge on 24/05/2017.
 */

public class NotasDeTextoClass
{
    private int id;
    private String titulo;
    private String texto;
    private String categoria;
    public  NotasDeTextoClass(){}
    public  NotasDeTextoClass(int id, String titulo, String texto)
    {
        this.id=id;
        this.titulo=titulo;
        this.texto=texto;
        this.categoria="Sin categoria";
    }
    public  NotasDeTextoClass(int id, String titulo, String texto, String categoria)
    {
        this.id=id;
        this.titulo=titulo;
        this.texto=texto;
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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
