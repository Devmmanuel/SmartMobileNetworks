package com.nss.nss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;


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
    private String insertar = "insert into historicosRedesMoviles (fecha,dbm,asu,pais,tipo_de_red,tipo_de_red_telefonica)" +
            " values ('22/10/19',-113,23,'mx','4G','UMTS')";

    public AdminSql(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "historicosRedesMoviles", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crearTabla);
        //db.execSQL(insertar);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS historicosRedesMoviles");
        db.execSQL(crearTabla);
        //db.execSQL(insertar);
    }
}
