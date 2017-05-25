package com.it.save.saveit;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.it.save.saveit.sqlite.model.CategoriaAdministradora;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class NuevaCategoria extends AppCompatActivity {

    private final int MY_PERMISSIONS = 100;
    ImageView imgSelected;
    Button agregar;
    EditText txtNombreCategoria;
    CategoriaAdministradora ca;
    boolean imagenIncertadaCcorrectamente=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_categoria);
        ca=new CategoriaAdministradora(this);
        imgSelected=(ImageView) findViewById(R.id.imgvNuevaImagen);
        agregar=(Button)findViewById(R.id.btnAgregar);
        txtNombreCategoria=(EditText)findViewById(R.id.txtNombreCategoria);
        imgSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pidePermisosEscritura();
                pidePermisosLectura();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

                // where do we want to find the data?
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                // finally, get a URI representation
                Uri data = Uri.parse(pictureDirectoryPath);

                // set the data and type.  Get all image types.
                photoPickerIntent.setDataAndType(data, "image/*");

                // we will invoke this activity, and get something back from it.
                startActivityForResult(photoPickerIntent, 20);
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ca.VerificaTitulo(txtNombreCategoria.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Ya existe categoria", Toast.LENGTH_SHORT).show();
                    txtNombreCategoria.setText("");
                    txtNombreCategoria.setFocusable(true);
                }
                else if(txtNombreCategoria.getText().length()==0)
                {
                    Toast.makeText(getApplicationContext(), "El campo no puede ir vacio", Toast.LENGTH_SHORT).show();
                    txtNombreCategoria.setText("");
                    txtNombreCategoria.setFocusable(true);
                }
                else if(!imagenIncertadaCcorrectamente)
                    Toast.makeText(getApplicationContext(), "Selecciones imagen", Toast.LENGTH_SHORT).show();
                else
                {
                    Bitmap bitmap = ((BitmapDrawable)imgSelected.getDrawable()).getBitmap();
                    SaveImage(bitmap);
                    ca.InsertarCategoria(new CategoriaClass(txtNombreCategoria.getText().toString(),-1,0));
                    Toast.makeText(getApplicationContext(), "Agregado", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(NuevaCategoria.this,MainActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // if we are here, everything processed successfully.
            if (requestCode == 20) {
                // if we are here, we are hearing back from the image gallery.

                // the address of the image on the SD Card.
                Uri imageUri = data.getData();

                // declare a stream to read the image data from the SD Card.
                InputStream inputStream;

                // we are getting an input stream, based on the URI of the image.
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    // get a bitmap from the stream.
                    Bitmap image = BitmapFactory.decodeStream(inputStream);


                    // show the image to the user
                    imgSelected.setImageBitmap(image);
                    imagenIncertadaCcorrectamente=true;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                }
            }
        }
    }
    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/imagenes_categorias/";
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = txtNombreCategoria.getText().toString()+".jpg";
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
        if (ContextCompat.checkSelfPermission(NuevaCategoria.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(NuevaCategoria.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {

            } else {
                ActivityCompat.requestPermissions(NuevaCategoria.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
            }
        }
    }
    private void pidePermisosLectura()
    {
        if (ContextCompat.checkSelfPermission(NuevaCategoria.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(NuevaCategoria.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))
            {

            } else {
                ActivityCompat.requestPermissions(NuevaCategoria.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
        }
    }
}
