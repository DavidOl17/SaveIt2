package com.it.save.saveit.notas.texto.operaciones;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.it.save.saveit.R;
import com.it.save.saveit.notas.texto.NotasDeTextoAdministradora;
import com.it.save.saveit.notas.texto.NotasDeTextoClass;
import com.it.save.saveit.notas.texto.NotasTexto;

/**
 * Created by Jorge on 25/05/2017.
 */

public class EditarNota extends AppCompatActivity
{
    NotasDeTextoAdministradora notas;
    NotasDeTextoClass notasDeTextoClass;
    EditText titulo,cuerpo;
    FloatingActionButton ftb;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nueva_nota);
        titulo=(EditText)findViewById(R.id.txt_titulo_nueva_nota);
        cuerpo=(EditText)findViewById(R.id.txt_cuerpo_nueva_nota);
        ftb=(FloatingActionButton)findViewById(R.id.fab_notas_texto);
        ftb.setVisibility(View.INVISIBLE);
        Bundle b = getIntent().getExtras();
        id = -1; // or other values
        if(b != null)
            id = b.getInt("id");
        if(id!=-1)
        {
            notas=new NotasDeTextoAdministradora(EditarNota.this);
            notasDeTextoClass=notas.getNotaDeTexto(id);
            titulo.setText(notasDeTextoClass.getTitulo());
            cuerpo.setText(notasDeTextoClass.getTexto());
        }
        else
            Toast.makeText(EditarNota.this,"Error al consultar",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed()
    {
        boolean update=false;
        if(!titulo.getText().equals(notasDeTextoClass.getTitulo())) {
            notasDeTextoClass.setTitulo(titulo.getText().toString());
            update=true;
        }
        if(!cuerpo.getText().equals(notasDeTextoClass.getTexto())) {
            notasDeTextoClass.setTexto(cuerpo.getText().toString());
            update=true;
        }
        if(update)
        {
            if(!notas.modificarNota(notasDeTextoClass))
                Toast.makeText(EditarNota.this,"Error al guardar",Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(EditarNota.this,NotasTexto.class);
        Bundle b = new Bundle();
        b.putString("categoria",notasDeTextoClass.getCategoria()); //Your id
        intent.putExtras(b);
        startActivity(intent);
        finish();

    }
}
