package com.jcjayodev.contactoscontrol.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "agenda_control.db";
    public static final String TABLE_CONTACT0S = "tbl_contactos";

    public static final String TABLE_CLIENTES = "tbl_clientes";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Clientes(db);
        Contactos(db);
    }


    //Crear tabla de clientes
    private void Clientes(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CLIENTES + " (" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "nombre TEXT NOT NULL," + "domicilio TEXT NOT NULL," + "codigo_postal TEXT NOT NULL," + "poblacion TEXT NOT NULL)");
    }

    //Crear tabla de contacto
    private void Contactos(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CONTACT0S + " (" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "cliente_id INTEGER," + "nombre TEXT NOT NULL," + "telefono TEXT NOT NULL," + "email TEXT NOT NULL," + " FOREIGN KEY (cliente_id) REFERENCES " + TABLE_CLIENTES + "(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //CLEAN DATABASE
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT0S);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
        //CREATE TABLES
        onCreate(db);
    }
}
