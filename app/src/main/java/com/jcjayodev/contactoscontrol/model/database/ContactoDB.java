package com.jcjayodev.contactoscontrol.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.jcjayodev.contactoscontrol.model.Cliente;
import com.jcjayodev.contactoscontrol.model.Contacto;

import java.util.ArrayList;

public class ContactoDB extends DBHelper {
    Context context;

    public ContactoDB(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    /**
     * Insertar datos
     **/
    public long insert(String nombre, int cliente, String telefono, String email) {
        long id = 0;
        try {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("cliente_id", cliente);
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("email", email);

            // Consulta para verificar si el registro ya existe
            String[] projection = {"nombre"};
            String selection = "nombre = ? AND cliente_id = ? AND telefono = ? AND email = ?";
            String[] selectionArgs = {nombre, String.valueOf(cliente), telefono, email};
            Cursor cursor = db.query(TABLE_CONTACT0S, projection, selection, selectionArgs, null, null, null);

            if (cursor.getCount() > 0) {
                // El registro ya existe, manejar la lógica correspondiente
                id = -1;
                //Toast.makeText(context, "El registro ya existe", Toast.LENGTH_SHORT).show();
            } else {
                // El registro no existe, realizar la inserción
                id = db.insert(TABLE_CONTACT0S, null, values);
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return id;
    }

    /**
     * Obtener todos los registros
     **/
    public ArrayList<Contacto> getAll(int cliente_id) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Contacto> lista = new ArrayList<>();
        Contacto contacto;
        Cursor cursorContacto;
        cursorContacto = db.rawQuery("SELECT * FROM " + TABLE_CONTACT0S + " WHERE cliente_id = " + cliente_id + "", null);
        if (cursorContacto.moveToFirst()) {
            do {
                contacto = new Contacto();
                contacto.setId(cursorContacto.getInt(0));
                contacto.setCliente_id(cursorContacto.getInt(1));
                contacto.setNombre(cursorContacto.getString(2));
                contacto.setTelefono(cursorContacto.getString(3));
                contacto.setEmail(cursorContacto.getString(4));
                lista.add(contacto);
            } while (cursorContacto.moveToNext());
        }
        cursorContacto.close();
        return lista;
    }

    /**
     * Ver contacto
     **/
    public Contacto getItem(int id) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Contacto contacto = null;
        Cursor cursorContacto;
        cursorContacto = db.rawQuery("SELECT * FROM " + TABLE_CONTACT0S + " WHERE id = " + id + " LIMIT 1 ", null);
        if (cursorContacto.moveToFirst()) {
            contacto = new Contacto();
            contacto.setId(cursorContacto.getInt(0));
            contacto.setCliente_id(cursorContacto.getInt(1));
            contacto.setNombre(cursorContacto.getString(2));
            contacto.setTelefono(cursorContacto.getString(3));
            contacto.setEmail(cursorContacto.getString(4));
            //lista.add(cliente);
        }
        cursorContacto.close();
        return contacto;
    }

    /**
     * Editar datos
     **/
    public boolean update(int id, String nombre, String telefono, String email) {
        boolean updated = false;
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("UPDATE " + TABLE_CONTACT0S + " SET nombre = '" + nombre + "', telefono = '" + telefono + "', email = '" + email + "' WHERE id = '" + id + "' ");
            updated = true;
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            updated = false;
        } finally {
            db.close();
        }
        return updated;
    }

    /**
     * Eliminar elemento
     **/
    public boolean delete(int id) {
        boolean deleted = false;
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_CONTACT0S + " WHERE id = " + id);
            deleted = true;
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            deleted = false;
        } finally {
            db.close();
        }
        return deleted;
    }

    /**
     * Buscar elemento
     **/
    public ArrayList<Contacto> search(String cadena) {
        ArrayList<Contacto> lista = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT0S + " WHERE nombre LIKE '" + cadena + "%'" + " OR telefono LIKE '" + cadena + "%'" + " OR email LIKE '" + cadena + "%'", null);
        if (cursor.moveToFirst()) {
            do {
                Contacto contacto = new Contacto();
                contacto.setId(cursor.getInt(0));
                contacto.setCliente_id(cursor.getInt(1));
                contacto.setNombre(cursor.getString(2));
                contacto.setTelefono(cursor.getString(3));
                contacto.setEmail(cursor.getString(4));
                lista.add(contacto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

}
