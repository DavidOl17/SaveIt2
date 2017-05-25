package com.it.save.saveit.notas.texto.operaciones;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.it.save.saveit.R;
import com.it.save.saveit.notas.texto.NotasDeTextoAdministradora;
import com.it.save.saveit.notas.texto.NotasDeTextoClass;
import com.it.save.saveit.notas.texto.NotasTexto;

public class AgregarNuevaNota extends AppCompatActivity {

    NotasDeTextoAdministradora notas;
    EditText titulo,cuerpo;
    String categoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nueva_nota);
        titulo=(EditText)findViewById(R.id.txt_titulo_nueva_nota);
        cuerpo=(com.it.save.saveit.notas.texto.edit.text.EditTextPropio)findViewById(R.id.txt_cuerpo_nueva_nota);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();
        categoria = "Sin categoria"; // or other values
        if(b != null)
            categoria = b.getString("categoria");
        notas=new NotasDeTextoAdministradora(AgregarNuevaNota.this,categoria);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(agregarNota())
                Snackbar.make(view, "Guardado exitosamente", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent=new Intent(AgregarNuevaNota.this, NotasTexto.class);
                Bundle b = new Bundle();
                b.putString("categoria",categoria); //Your id
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean agregarNota()
    {
        if(titulo.getText().length()==0)
        {
            showMessage("Titulo no puede estar vacio");
            titulo.setText("");
            titulo.setFocusable(true);
        }
        else if(cuerpo.getText().length()==0)
        {
            showMessage("El cuerpo de la nota no puede estar vacio");
            cuerpo.setText("");
            cuerpo.setFocusable(true);
        }
        else
        {
            NotasDeTextoClass nuevaNota = new NotasDeTextoClass(0,titulo.getText().toString(),cuerpo.getText().toString(),categoria);
            notas.InsertarNotaTexto(nuevaNota);
            return true;
        }
        return false;
    }
    private void showMessage(String message)
    {
        Toast.makeText(AgregarNuevaNota.this,message,Toast.LENGTH_SHORT).show();
    }
}
