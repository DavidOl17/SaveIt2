package com.it.save.saveit.notas.multimedia.operaciones;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.it.save.saveit.R;
import com.it.save.saveit.notas.multimedia.NotasMultimedia;
import com.it.save.saveit.notas.multimedia.NotasMultimediaAdministradora;
import com.it.save.saveit.notas.multimedia.NotasMultimediaClass;
import com.it.save.saveit.notas.texto.NotasDeTextoClass;
import com.it.save.saveit.notas.texto.operaciones.EditarNota;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Jorge on 25/05/2017.
 */

public class EditarNotaMultimedia extends AppCompatActivity
{
    NotasMultimediaAdministradora na;
    NotasMultimediaClass nm;
    private final int CAMERA_REQUEST=1888;
    EditText titulo;
    ImageView imagen;
    Button btn;
    int id;
    boolean cambioImagen=false;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota_multimedia);
        titulo=(EditText)findViewById(R.id.edit_nombre_nota);
        imagen=(ImageView)findViewById(R.id.imgv_seleccion_multimedia);
        btn=(Button)findViewById(R.id.fab_multimedia);
        btn.setVisibility(View.INVISIBLE);
        Bundle b = getIntent().getExtras();
        id = -1; // or other values
        if(b != null)
            id = b.getInt("id");
        if(id!=-1)
        {
            na=new NotasMultimediaAdministradora(EditarNotaMultimedia.this);
            nm=na.getNotaMultimedia(id);
            titulo.setText(nm.getTitulo());
            String root = Environment.getExternalStorageDirectory().toString();
            File imgFile = new  File(root + "/imagenes_multimedia/"+String.valueOf(id));
            if(imgFile.exists())
            {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imagen.setImageBitmap(myBitmap);
            }
            else
                imagen.setImageResource(R.mipmap.no_image);
        }
        else
            Toast.makeText(EditarNotaMultimedia.this,"Error al consultar",Toast.LENGTH_SHORT).show();

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder menu = new AlertDialog.Builder(EditarNotaMultimedia.this);
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
                    cambioImagen=true;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == CAMERA_REQUEST)
            {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imagen.setImageBitmap(photo);
                cambioImagen=true;
            }
        }
    }
    @Override
    public void onBackPressed()
    {
        if(!titulo.equals(nm.getTitulo()))
        {
            nm.setTitulo(titulo.getText().toString());
            na.modificarNotaMultimedia(nm);
            if(cambioImagen)
            {
                Bitmap bitmap = ((BitmapDrawable)imagen.getDrawable()).getBitmap();
                SaveImage(bitmap);
            }
            Intent i = new Intent(EditarNotaMultimedia.this,NotasMultimedia.class);
            Bundle b = new Bundle();
            b.putString("categoria",nm.getCategoria());
            i.putExtras(b);
            finish();
            startActivity(i);
        }
    }
    private void SaveImage(Bitmap finalBitmap) {

        NotasMultimediaAdministradora a = new NotasMultimediaAdministradora(EditarNotaMultimedia.this);
        String root = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/imagenes_multimedia/";
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = String.valueOf(nm.getId());
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
