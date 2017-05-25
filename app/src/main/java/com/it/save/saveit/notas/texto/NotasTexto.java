package com.it.save.saveit.notas.texto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.it.save.saveit.R;

public class NotasTexto extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_texto);
        Bundle b = getIntent().getExtras();
        String categoria = "Sin categoria"; // or other values
        if(b != null)
         categoria = b.getString("categoria");
        NotasDeTextoAdministradora adminNotasTexto= new NotasDeTextoAdministradora(this,categoria);
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view_notas_texto);
        recyclerView.setAdapter(adminNotasTexto.getAdaptador());
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
    }
}
