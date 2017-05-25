package com.it.save.saveit.notas.texto;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.it.save.saveit.R;
import com.it.save.saveit.notas.texto.operaciones.AgregarNuevaNota;

public class NotasTexto extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView txtLabelTitulo;
    NotasDeTextoAdministradora adminNotasTexto;
    FloatingActionButton floatingActionButton;
    String categoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_texto);
        txtLabelTitulo=(TextView)findViewById(R.id.titulo_categoria);
        Bundle b = getIntent().getExtras();
        categoria = "Sin categoria"; // or other values
        if(b != null)
         categoria = b.getString("categoria");
        final String subCat=categoria;
        txtLabelTitulo.setText(categoria);
        adminNotasTexto= new NotasDeTextoAdministradora(this,categoria);
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view_notas_texto);
        recyclerView.setAdapter(adminNotasTexto.getAdaptador());
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingButtonAddNota);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!categoria.equals("Papelera de reciclaje"))
                {
                    Intent intent = new Intent(NotasTexto.this,AgregarNuevaNota.class);
                    Bundle b = new Bundle();
                    b.putString("categoria",subCat); //Your id
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(NotasTexto.this,"No se pueden añadir notas a la papelera", Toast.LENGTH_SHORT).show();

            }
        });
        adminNotasTexto.getAdaptador().setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(NotasTexto.this,"En proceso", Toast.LENGTH_SHORT).show();
            }
        });
        adminNotasTexto.getAdaptador().setOnItemLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                final int id=recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder menu = new AlertDialog.Builder(NotasTexto.this);
                CharSequence[] opciones = {"Eliminar nota"};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch(which)
                        {
                            case 0:
                                if(!categoria.equals("Papelera de reciclaje")) {
                                    int idNota = adminNotasTexto.CargaNotasDeTexto(categoria).elementAt(id).getId();
                                    if (adminNotasTexto.EliminaNotaTexto(idNota)) {
                                        Toast.makeText(NotasTexto.this, "Enviado a papelera", Toast.LENGTH_SHORT).show();
                                        adminNotasTexto.getAdaptador().notifyDataSetChanged();
                                        finish();
                                    }
                                }
                                else
                                {
                                    int idNota = adminNotasTexto.CargaNotasDeTexto(categoria).elementAt(id).getId();
                                    if (adminNotasTexto.EliminaNotaTextoDefinitivo(idNota)) {
                                        Toast.makeText(NotasTexto.this, "Eliminado", Toast.LENGTH_SHORT).show();
                                        adminNotasTexto.getAdaptador().notifyDataSetChanged();
                                        finish();
                                    }
                                }
                                break;
                        }
                    }
                });
                menu.create().show();
                return false;
            }
        });
    }
}
