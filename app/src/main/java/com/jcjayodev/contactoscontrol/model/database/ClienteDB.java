package com.jcjayodev.contactoscontrol.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.jcjayodev.contactoscontrol.model.Cliente;

import java.util.ArrayList;

public class ClienteDB extends DBHelper {
    Context context;

    public ClienteDB(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    /**
     * Insertar datos en la tabla de cliente
     **/
    public long insert(String nombre, String domicilio, String cp, String poblacion) {
        long id = 0;
        try {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre.trim());
            values.put("domicilio", domicilio.trim());
            values.put("codigo_postal", cp.trim());
            values.put("poblacion", poblacion.trim());

            // Consulta para verificar si el registro ya existe
            String[] projection = {"nombre"};
            String selection = "nombre = ? AND domicilio = ?";
            String[] selectionArgs = {nombre, domicilio};
            Cursor cursor = db.query(TABLE_CLIENTES, projection, selection, selectionArgs, null, null, null);

            if (cursor.getCount() > 0) {
                // El registro ya existe, manejar la lógica correspondiente
                id = -1;
                //Toast.makeText(context, "El registro ya existe", Toast.LENGTH_SHORT).show();
            } else {
                // El registro no existe, realizar la inserción
                id = db.insert(TABLE_CLIENTES, null, values);
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
    public ArrayList<Cliente> getAll() {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Cliente> lista = new ArrayList<>();
        Cliente cliente;
        Cursor cursorCliente;
        cursorCliente = db.rawQuery("SELECT * FROM " + TABLE_CLIENTES, null);
        if (cursorCliente.moveToFirst()) {
            do {
                cliente = new Cliente();
                cliente.setId(cursorCliente.getInt(0));
                cliente.setNombre(cursorCliente.getString(1));
                cliente.setDomicilio(cursorCliente.getString(2));
                cliente.setCodigo_postal(cursorCliente.getString(3));
                cliente.setPoblacion(cursorCliente.getString(4));
                lista.add(cliente);
            } while (cursorCliente.moveToNext());
        }
        cursorCliente.close();
        return lista;
    }

    /**
     * Ver cliente
     **/
    public Cliente getItem(int id) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cliente cliente = null;
        Cursor cursorCliente;
        cursorCliente = db.rawQuery("SELECT * FROM " + TABLE_CLIENTES + " WHERE id = " + id + " LIMIT 1 ", null);
        if (cursorCliente.moveToFirst()) {

            cliente = new Cliente();
            cliente.setId(cursorCliente.getInt(0));
            cliente.setNombre(cursorCliente.getString(1));
            cliente.setDomicilio(cursorCliente.getString(2));
            cliente.setCodigo_postal(cursorCliente.getString(3));
            cliente.setPoblacion(cursorCliente.getString(4));
            //lista.add(cliente);

        }
        cursorCliente.close();
        return cliente;
    }

    /**
     * Editar datos en la tabla de cliente
     **/
    public boolean update(int id, String nombre, String domicilio, String cp, String poblacion) {
        boolean updated = false;
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("UPDATE " + TABLE_CLIENTES + " SET nombre = '" + nombre.trim() + "', domicilio = '" + domicilio.trim() + "', codigo_postal = '" + cp.trim() + "', poblacion = '" + poblacion.trim() + "' WHERE id = '" + id + "' ");
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
            db.execSQL("DELETE FROM " + TABLE_CLIENTES + " WHERE id = '" + id + "' ");
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
    public ArrayList<Cliente> search(String cadena) {
        ArrayList<Cliente> lista = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CLIENTES + " WHERE nombre LIKE '" + cadena + "%' " + " OR domicilio LIKE '" + cadena + "%' " + " OR poblacion LIKE '" + cadena + "%'" + " OR codigo_postal LIKE '" + cadena + "%'", null);
        if (cursor.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                cliente.setId(cursor.getInt(0));
                cliente.setNombre(cursor.getString(1));
                cliente.setDomicilio(cursor.getString(2));
                cliente.setCodigo_postal(cursor.getString(3));
                cliente.setPoblacion(cursor.getString(4));
                lista.add(cliente);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }


}
