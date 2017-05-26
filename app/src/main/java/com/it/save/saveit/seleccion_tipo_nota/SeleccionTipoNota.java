package com.it.save.saveit.seleccion_tipo_nota;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.it.save.saveit.R;
import com.it.save.saveit.notas.multimedia.NotasMultimedia;
import com.it.save.saveit.notas.texto.NotasTexto;

public class SeleccionTipoNota extends AppCompatActivity {

    private ImageView imgTexto,imgMultimedia;
    String categoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_tipo_nota);
        imgTexto=(ImageView)findViewById(R.id.seleccion_texto);
        imgMultimedia=(ImageView)findViewById(R.id.seleccion_multimedia);
        imgTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notasTexto = new Intent(SeleccionTipoNota.this, NotasTexto.class);
                Bundle b = getIntent().getExtras();
                categoria = "Sin categoria"; // or other values
                if(b != null)
                    categoria = b.getString("categoria");
                b = new Bundle();
                b.putString("categoria", categoria); //Your id
                notasTexto.putExtras(b);
                startActivity(notasTexto);
            }
        });
        imgMultimedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notasTexto = new Intent(SeleccionTipoNota.this, NotasMultimedia.class);
                Bundle b = getIntent().getExtras();
                categoria = "Sin categoria"; // or other values
                if(b != null)
                    categoria = b.getString("categoria");
                b = new Bundle();
                b.putString("categoria", categoria); //Your id
                notasTexto.putExtras(b);
                startActivity(notasTexto);
            }
        });
    }
}
