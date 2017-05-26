package com.it.save.saveit.notas.multimedia;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.it.save.saveit.R;
import com.it.save.saveit.notas.multimedia.operaciones.AgregarNotaMultimedia;
import com.it.save.saveit.notas.multimedia.operaciones.EditarNotaMultimedia;
import com.it.save.saveit.notas.texto.NotasTexto;

import java.io.File;

public class NotasMultimedia extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView txtLabelTitulo;
    NotasMultimediaAdministradora adminNotasMultimedia;
    Button fb;
    String categoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_multimedia);
        fb=(Button) findViewById(R.id.btn_agregar_nota);
        txtLabelTitulo=(TextView)findViewById(R.id.titulo_categoria_multimedia);
        Bundle b = getIntent().getExtras();
        categoria = "Sin categoria"; // or other values
        if(b != null)
            categoria = b.getString("categoria");
        final String subCat=categoria;
        txtLabelTitulo.setText(categoria);
        adminNotasMultimedia= new NotasMultimediaAdministradora(this,categoria);
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view_notas_multimedia);
        recyclerView.setAdapter(adminNotasMultimedia.getAdaptador());
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!categoria.equals("Papelera de reciclaje"))
                {
                    Intent notasMultimedia = new Intent(NotasMultimedia.this, AgregarNotaMultimedia.class);
                    Bundle b = new Bundle();
                    b.putString("categoria", categoria); //Your id
                    notasMultimedia.putExtras(b);
                    startActivity(notasMultimedia);
                    finish();
                }
                else
                    Toast.makeText(NotasMultimedia.this,"No se puede crear nueva nota",Toast.LENGTH_SHORT).show();
            }
        });
        adminNotasMultimedia.getAdaptador().setOnItemLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int id=recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder menu = new AlertDialog.Builder(NotasMultimedia.this);
                CharSequence[] opciones = {"Eliminar nota"};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch(which)
                        {
                            case 0:
                                if(!categoria.equals("Papelera de reciclaje")) {
                                    int idNota = adminNotasMultimedia.CargaNotasMultimedia(categoria).elementAt(id).getId();
                                    if (adminNotasMultimedia.EliminaNotaMultimedia(idNota)) {
                                        Toast.makeText(NotasMultimedia.this, "Enviado a papelera", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                                else
                                {
                                    int idNota = adminNotasMultimedia.CargaNotasMultimedia(categoria).elementAt(id).getId();
                                    if (adminNotasMultimedia.EliminaNotaMultimediaDefinitivo(idNota)) {
                                        String root = Environment.getExternalStorageDirectory().toString();
                                        File imgFile = new  File(root + "/imagenes_multimedia/"+String.valueOf(idNota));
                                        if(imgFile.exists())
                                            imgFile.delete();
                                        Toast.makeText(NotasMultimedia.this, "Eliminado", Toast.LENGTH_SHORT).show();
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
        adminNotasMultimedia.getAdaptador().setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id=recyclerView.getChildAdapterPosition(v);
                Intent intent = new Intent(NotasMultimedia.this,EditarNotaMultimedia.class);
                Bundle b = new Bundle();
                b.putInt("id",adminNotasMultimedia.CargaNotasMultimedia(categoria).elementAt(id).getId()); //Your id
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
    }
}
