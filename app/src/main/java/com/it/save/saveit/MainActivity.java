package com.it.save.saveit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

//import com.it.save.saveit.notas_de_texto.AplicacionNotasDeTexto;
//import com.it.save.saveit.notas_de_texto.NotasDeTexto;
import com.it.save.saveit.notas.texto.NotasTexto;
import com.it.save.saveit.seleccion_tipo_nota.SeleccionTipoNota;
import com.it.save.saveit.sqlite.model.CategoriaAdministradora;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public static Context getmContext() {
        return mContext;
    }

    private static Context mContext;
    FloatingActionButton myFab;
    CategoriaAdministradora categoriaAdministradora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        recyclerView = new RecyclerView(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        categoriaAdministradora = new CategoriaAdministradora(MainActivity.this);
        //final Categoria app =(Categoria)getApplication();
        recyclerView=(RecyclerView) findViewById(R.id.recyclei_view);
        recyclerView.setAdapter(categoriaAdministradora.getAdaptador());
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        categoriaAdministradora.getAdaptador().setOnItemClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Toast.makeText(MainActivity.this,"Seleccionando el elemento: "+ recyclerView.getChildAdapterPosition(v),Toast.LENGTH_SHORT).show();
                Intent notasTexto = new Intent(MainActivity.this, SeleccionTipoNota.class);
                Bundle b = new Bundle();
                CategoriaAdministradora categoriaAdministradora = new CategoriaAdministradora(MainActivity.this);
                b.putString("categoria", categoriaAdministradora.getCategoria(recyclerView.getChildAdapterPosition(v))); //Your id
                notasTexto.putExtras(b);
                startActivity(notasTexto);
            }
        });
        categoriaAdministradora.getAdaptador().setOnItemLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(final View v)
            {
                final int id=recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder menu = new AlertDialog.Builder(MainActivity.this);
                CharSequence[] opciones = {"Actualizar vista","Eliminar categoria"};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch(which)
                        {
                            case 0:
                                finish();
                                startActivity(getIntent());
                                //categoriaAdministradora.getAdaptador().notifyDataSetChanged();
                                break;
                            case 1:
                                CategoriaAdministradora categoriaAdministradora = new CategoriaAdministradora(MainActivity.this);
                                categoriaAdministradora.EliminaCategoria(id);
                                finish();
                                startActivity(getIntent());
                                //categoriaAdministradora.getAdaptador().notifyDataSetChanged();
                                break;
                        }
                    }
                });
                menu.create().show();
                return true;
            }
        });

        myFab= (FloatingActionButton)  findViewById(R.id.floatingButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NuevaCategoria.class);
                startActivity(intent);

            }
        });
    }
}
