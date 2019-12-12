package com.nss.nss;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AdminSql extends SQLiteOpenHelper {

    private String crearTabla = "CREATE TABLE historicosRedesMoviles (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "fecha TEXT," +
            "dbm INTEGER," +
            "asu INTEGER," +
            "pais TEXT," +
            "tipo_de_red TEXT," +
            "tipo_de_red_telefonica TEXT" +
            ")";
private  SQLiteDatabase db;


    public AdminSql(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
          db = this.getWritableDatabase();
    }

    public void insertar(int iDbm, int iAsu, imformacionDispositivos info) {
        try {
            db = AdminSql.this.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("fecha", obtenerFecha());
            registro.put("dbm", iDbm);
            registro.put("asu", iAsu);
            registro.put("pais", info.getCodigoPais());
            registro.put("tipo_de_red", info.getTypeOfNetwork234());
            registro.put("tipo_de_red_telefonica", info.getTypeOfNetwork());
            db.insert("historicosRedesMoviles", null, registro);
            //**Toast.makeText(getActivity(), "Se cargaron los registros correctamente", Toast.LENGTH_SHORT).show();
            db.close();
        } catch (Exception e) {
         Log.w("Error",e.getMessage());
        }
    }

    /**
     *@return String
     * regresa la fecha actual en formato dd/mm/yy
     */
    public String obtenerFecha() {
        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String salida = df.format(fecha);
        return salida;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crearTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS historicosRedesMoviles");
        db.execSQL(crearTabla);
    }
}
