package com.jcjayodev.contactoscontrol.presenter;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jcjayodev.contactoscontrol.model.database.DBHelper;

public class MyAplication extends Application {
    /**
     * Este método se llama al crear la aplicación y crea la base de datos
     **/
    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db != null) {
            db.close();
            Log.i("Base de datos", "Base de datos abierta");
            //Toast.makeText(this, "Base de datos abierta", Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Base de datos", "Error al crear la base de datos");
            //Toast.makeText(this, "Error al crear la base de datos", Toast.LENGTH_SHORT).show();
        }
    }
}
