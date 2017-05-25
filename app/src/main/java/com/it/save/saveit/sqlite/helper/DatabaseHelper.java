package com.it.save.saveit.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.it.save.saveit.CategoriaClass;
import com.it.save.saveit.MainActivity;
import com.it.save.saveit.R;
import com.it.save.saveit.notas.texto.NotasDeTextoClass;
//import com.it.save.saveit.notas_de_texto.NotasDeTextoClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Jorge on 23/05/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "saveItDB.db";

    // Table Names
    private static final String TABLE_CATEGORIA = "categoria";
    private static final String TABLE_NOTA = "nota";
    private static final String TABLE_NOTA_TEXTO = "nota_texto";
    private static final String TABLE_NOTA_MULTIMEDIA = "nota_multimedia";
    private static final String TABLE_TIPO = "tipo";

    // nombre de columnas en comun
    private static final String KEY_ID = "id";

    // Nombre de las columnas de la tabla categoria
    private static final String TITULO_CATEGORIA = "titulo";
    private static final String ID_IMAGEN = "imagen";

    // Nombre de las columnas de la tabla nota
    private static final String TITULO_NOTA = "titulo";
    private static final String TIPO = "tipo";
    private static final String ID_CATEGORIA = "titulo_categoria";

    // Nombre de las columnas de la tabla nota_texto
    private static final String TEXTO_CUERPO = "texto_cuerpo";

    // Nombre de las columnas de la tabla nota_texto
    private static final String ELEMENTO_MM = "elemento_multimedia";


    //Seccion de la creacion de tablas

    //Tabla nota
    private static final String CREAR_TABLA_NOTA = "CREATE TABLE "
            + TABLE_NOTA + "(" + KEY_ID + " INTEGER PRIMARY KEY," + TITULO_NOTA
            + " TEXT," + TIPO + " INTEGER," + ID_CATEGORIA
            + " TEXT" + ")";

    //Tabla categoria
    private static final String CREAR_TABLA_CATEGORIA = "CREATE TABLE "
            + TABLE_CATEGORIA + "(" + TITULO_CATEGORIA + " TEXT PRIMARY KEY," + ID_IMAGEN
            + " INTEGER)";

    //Tabla NOTA DE TEXTO
    private static final String CREAR_TABLA_NOTA_TEXTO = "CREATE TABLE "
            + TABLE_NOTA_TEXTO + "(" + KEY_ID + " INTEGER PRIMARY KEY," + TEXTO_CUERPO
            + " TEXT)";

    //Tabla NOTA MULTIMEDIA
    private static final String CREAR_TABLA_NOTA_MM = "CREATE TABLE "
            + TABLE_NOTA_MULTIMEDIA + "(" + KEY_ID + " INTEGER PRIMARY KEY," + ELEMENTO_MM
            + " INTEGER)";

    Context context;
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        try
        {

            //db=getWritableDatabase();
            db.execSQL(CREAR_TABLA_NOTA);
            db.execSQL(CREAR_TABLA_CATEGORIA);
            db.execSQL(CREAR_TABLA_NOTA_TEXTO);
            db.execSQL(CREAR_TABLA_NOTA_MM);
            //insertarCategoria(new CategoriaClass("Sin categoria",R.mipmap.sin_categoria_ico,0));
            //insertarCategoria(new CategoriaClass("Papelera de reciclaje",R.mipmap.recycler_ico,0));
            Toast.makeText(context,"Creado exitosamentes",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(context,"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
            }

    }
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // on upgrade drop older tables
        try
        {
            //db=getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTA_TEXTO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTA_MULTIMEDIA);

            // create new tables
            onCreate(db);
        }catch (Exception e){Toast.makeText(MainActivity.getmContext(),"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();}

    }

    // ------------------------ Acciones para la tabla de categoria ----------------//

    public long insertarCategoria(CategoriaClass categoria) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITULO_CATEGORIA, categoria.getCategoria());
        values.put(ID_IMAGEN, categoria.getImage());

        // insert row
        long idObtenido = db.insert(TABLE_CATEGORIA, null, values);

        return idObtenido;
    }

    public CategoriaClass getCategoria(String tituloCategoria) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIA + " WHERE "
                + TITULO_CATEGORIA + " = '" + tituloCategoria+"'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        CategoriaClass categoria = new CategoriaClass(c.getString(0),c.getInt(1),0);

        return categoria;
    }

    public Vector<CategoriaClass> getTodasLasCategorias() {
        Vector<CategoriaClass> categorias = new Vector<CategoriaClass>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do
            {
                CategoriaClass categoria = new CategoriaClass(c.getString(0),c.getInt(1),0);
                categorias.add(categoria);
            } while (c.moveToNext());
        }

        return categorias;
    }
    public int getCategoriasCount()
    {
        int count=0;
        String countQuery = "SELECT  * FROM " + TABLE_CATEGORIA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int updateToDo(CategoriaClass categoria) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITULO_CATEGORIA, categoria.getCategoria());
        values.put(ID_IMAGEN, categoria.getImage());

        // updating row
        return db.update(TABLE_CATEGORIA, values, TITULO_CATEGORIA + " = ?",
                new String[] { categoria.getCategoria()});
    }

    public void deleteCategoria(String tituloCategoria) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORIA, TITULO_CATEGORIA + " = ?",
                new String[] { tituloCategoria });
    }

    public boolean existeCategoria(String categoria)
    {
        int count=0;
        String countQuery = "SELECT  * FROM " + TABLE_CATEGORIA + " WHERE "+TITULO_CATEGORIA + " ='"+categoria+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();
        if(count>0)
            return true;
        return false;
    }

    // ------------------------ Acciones para la tabla de notas de texto ----------------//

    public long insertarNotaDeTexto(NotasDeTextoClass notasDeTextoClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITULO_NOTA, notasDeTextoClass.getTitulo());
        values.put(TIPO, 0);
        values.put(ID_CATEGORIA, notasDeTextoClass.getCategoria());

        // insert row
        long idObtenido = db.insert(TABLE_NOTA, null, values);
        values = new ContentValues();
        values.put(KEY_ID,lastID());
        values.put(TEXTO_CUERPO,notasDeTextoClass.getTexto());
        idObtenido = db.insert(TABLE_NOTA_TEXTO, null, values);
        return idObtenido;
    }

    public int lastID() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTA;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToLast();

         int id = c.getInt(0);

        return id;
    }

    public NotasDeTextoClass getNotasDeTextoClass(String tituloNota) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTA_TEXTO + " WHERE "
                + TITULO_NOTA + " = '" + tituloNota+"'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        NotasDeTextoClass notasDeTextoClass = new NotasDeTextoClass(c.getInt(0),tituloNota,c.getString(1));

        return notasDeTextoClass;
    }

    public Vector<NotasDeTextoClass> getTodasLasNotasDeTexto(String categoria)
    {
        Vector<NotasDeTextoClass> vectorNotas = new Vector<NotasDeTextoClass>();
        String selectQuery = "SELECT  " + TABLE_NOTA + "." + KEY_ID + ", " + TABLE_NOTA + "." + TITULO_NOTA + ", "
                + TABLE_NOTA_TEXTO + "." + TEXTO_CUERPO + " FROM " + TABLE_NOTA + ", " + TABLE_NOTA_TEXTO
                + " WHERE " + TABLE_NOTA + "." + ID_CATEGORIA + "='" + categoria + "' AND " + TABLE_NOTA_TEXTO + "." + KEY_ID + " = "
                + TABLE_NOTA + "." + KEY_ID + " GROUP BY " + TABLE_NOTA + "." + KEY_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                NotasDeTextoClass notasDeTextoClass = new NotasDeTextoClass(c.getInt(0), c.getString(1), c.getString(2));
                vectorNotas.add(notasDeTextoClass);
            } while (c.moveToNext());
        }

        return vectorNotas;
    }
    public int updateNotaDeTexto(NotasDeTextoClass notasDeTextoClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITULO_NOTA, notasDeTextoClass.getTitulo());
        // updating row
         db.update(TABLE_NOTA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(notasDeTextoClass.getId())});

        values.put(TEXTO_CUERPO, notasDeTextoClass.getTexto());
        // updating row
        db.update(TABLE_NOTA_TEXTO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(notasDeTextoClass.getId())});
        return 0;
    }

    public void deleteNotaTexto(int idNotaTexto)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTA,  KEY_ID + " = ?",
                new String[] { String.valueOf(idNotaTexto) });
        db.delete(TABLE_NOTA_TEXTO, KEY_ID + " = ?",
                new String[] { String.valueOf(idNotaTexto) });
    }

    public int getNotasDeTextoCount(String cate)
    {
        int count=0;
        try
        {
            String countQuery = "SELECT  * FROM " + TABLE_NOTA+" WHERE "+ID_CATEGORIA+" = '"+cate+"'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);

            count = cursor.getCount();
            cursor.close();
        }catch (Exception e)
        {
            count=0;
        }

        // return count
        return count;
    }

}
