package com.it.save.saveit.notas.multimedia.operaciones;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.it.save.saveit.R;
import com.it.save.saveit.notas.multimedia.NotasMultimedia;
import com.it.save.saveit.notas.multimedia.NotasMultimediaAdministradora;
import com.it.save.saveit.notas.multimedia.NotasMultimediaClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AgregarNotaMultimedia extends AppCompatActivity {

    String categoria;
    EditText titulo;
    private static final int CAMERA_REQUEST = 1888;
    ImageView imagen;
    boolean imagenIncertadaCcorrectamente=false;
    NotasMultimediaAdministradora na;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota_multimedia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        na=new NotasMultimediaAdministradora(AgregarNotaMultimedia.this,categoria);
        titulo=(EditText)findViewById(R.id.edit_nombre_nota);//Verificar nombre si falla
        imagen=(ImageView)findViewById(R.id.imgv_seleccion_multimedia);//Verificar si falla
        Bundle b = getIntent().getExtras();
        categoria = "Sin categoria"; // or other values
        if(b != null)
            categoria = b.getString("categoria");
        Button fab = (Button) findViewById(R.id.fab_multimedia);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titulo.getText().length()==0)
                {
                    Toast.makeText(getApplicationContext(), "El campo no puede ir vacio", Toast.LENGTH_SHORT).show();
                    titulo.setText("");
                    titulo.setFocusable(true);
                }
                else if(!imagenIncertadaCcorrectamente)
                    Toast.makeText(getApplicationContext(), "Selecciones imagen", Toast.LENGTH_SHORT).show();
                else
                {
                    Bitmap bitmap = ((BitmapDrawable)imagen.getDrawable()).getBitmap();
                    SaveImage(bitmap);
                    na.InsertarNotaMultimedia(new NotasMultimediaClass(0,titulo.getText().toString(),categoria));
                    Intent i = new Intent(AgregarNotaMultimedia.this,NotasMultimedia.class);
                    Bundle b = new Bundle();
                    b.putString("categoria",categoria);
                    i.putExtras(b);
                    finish();
                    startActivity(i);
                }
            }
        });
        imagen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pidePermisosEscritura();
                pidePermisosLectura();
                AlertDialog.Builder menu = new AlertDialog.Builder(AgregarNotaMultimedia.this);
                CharSequence[] opciones = {"Elegir de galeria","Tomar foto"};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch(which)
                        {
                            case 0:
                                SeleccionaFotoDeGaleria();
                                break;
                            case 1:
                                TomarFoto();
                                break;
                        }
                    }
                });
                menu.create().show();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(AgregarNotaMultimedia.this,NotasMultimedia.class);
        Bundle b = new Bundle();
        b.putString("categoria",categoria);
        i.putExtras(b);
        finish();
        startActivity(i);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 20) {
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    imagen.setImageBitmap(image);
                    imagenIncertadaCcorrectamente=true;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == CAMERA_REQUEST)
            {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imagen.setImageBitmap(photo);
                imagenIncertadaCcorrectamente=true;
            }
        }
    }
    private void SaveImage(Bitmap finalBitmap) {

        NotasMultimediaAdministradora a = new NotasMultimediaAdministradora(AgregarNotaMultimedia.this);
        String root = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/imagenes_multimedia/";
        File myDir = new File(root);
        myDir.mkdirs();
        int data;
        if((a.getLasiID()+1)<0)
            data=0;
        else
            data=(a.getLasiID()+1);
        String fname = String.valueOf(data);
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void pidePermisosEscritura()
    {
        if (ContextCompat.checkSelfPermission(AgregarNotaMultimedia.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AgregarNotaMultimedia.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {

            } else {
                ActivityCompat.requestPermissions(AgregarNotaMultimedia.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
            }
        }
    }
    private void pidePermisosLectura()
    {
        if (ContextCompat.checkSelfPermission(AgregarNotaMultimedia.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AgregarNotaMultimedia.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))
            {

            } else {
                ActivityCompat.requestPermissions(AgregarNotaMultimedia.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
        }
    }
    private void SeleccionaFotoDeGaleria()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        // where do we want to find the data?
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data, "image/*");
        startActivityForResult(photoPickerIntent, 20);
    }
    private void TomarFoto()
    {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
}
