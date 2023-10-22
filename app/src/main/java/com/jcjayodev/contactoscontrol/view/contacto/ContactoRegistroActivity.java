package com.jcjayodev.contactoscontrol.view.contacto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jcjayodev.contactoscontrol.R;
import com.jcjayodev.contactoscontrol.model.Contacto;
import com.jcjayodev.contactoscontrol.model.database.ContactoDB;

public class ContactoRegistroActivity extends AppCompatActivity {
    //Declaracion de variables
    EditText nombreEdt;
    EditText telefonoEdt;
    EditText emailEdt;
    Button saveButton;
    ImageView backButton, imageAccount;
    TextView titulo;
    String idContacto;
    String idCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto_registro);
        //Vincular los elementos de la interfaz
        titulo = findViewById(R.id.title);
        nombreEdt = findViewById(R.id.nombre);
        telefonoEdt = findViewById(R.id.telefono);
        emailEdt = findViewById(R.id.email);
        saveButton = findViewById(R.id.boton);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            onBackPressed();
        });
        //Recibir elementos del intent
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            String option;
            String id;
            if (extras == null) {
                option = "0";
                id = "0";
                idContacto = id;
                idCliente = "0";
            } else {
                option = extras.getString("option");
                id = extras.getString("idContacto");
                idContacto = id;
                idCliente = extras.getString("idCliente");
            }
            optionConfig(option, id);
        } else {
            String option = (String) savedInstanceState.getSerializable("option");
            String id = (String) savedInstanceState.getSerializable("idContacto");
            idContacto = id;
            idCliente = (String) savedInstanceState.getSerializable("idCliente");
            optionConfig(option, id);
        }
    }

    /**
     * @param option La opción seleccionada por el usuario en la interfaz
     * @param id     El id del cliente seleccionado
     **/
    private void optionConfig(String option, String id) {

        switch (option) {
            case "1":
                //Cambiar el título de la aplicación
                registerConfig();
                break;
            case "2":
                visualizerConfig(id);
                break;
            case "3":
                updateConfig(id);
                break;
        }

    }

    /**
     * Configura la vista para actualizar un Contacto
     **/
    private void updateConfig(String id) {
        //Cambiar el título de la aplicación
        titulo.setText("Editar Cliente");
        //Obtener datos del usuario
        ContactoDB contactoDB = new ContactoDB(ContactoRegistroActivity.this);
        Contacto contacto = contactoDB.getItem(Integer.parseInt(id));
        nombreEdt.setText(contacto.getNombre());
        telefonoEdt.setText(contacto.getTelefono());
        emailEdt.setText(contacto.getEmail());
        saveButton.setText("Actualizar");
        saveButton.setOnClickListener(v -> {
            String nombre = nombreEdt.getText().toString();
            String telefono = telefonoEdt.getText().toString();
            String email = emailEdt.getText().toString();
            validateFields(nombre, telefono, email, "2", id);
        });
    }

    /**
     * Configura la vista para visualizar un Contacto
     **/
    private void visualizerConfig(String id) {
        //Cambiar el título de la aplicación
        titulo.setText("");
        //Obtener datos del usuario
        ContactoDB contactoDB = new ContactoDB(ContactoRegistroActivity.this);
        Contacto contacto = contactoDB.getItem(Integer.parseInt(id));
        nombreEdt.setText(contacto.getNombre());
        telefonoEdt.setText(contacto.getTelefono());
        emailEdt.setText(contacto.getEmail());
        nombreEdt.setEnabled(false);
        telefonoEdt.setEnabled(false);
        emailEdt.setEnabled(false);
        nombreEdt.setClickable(false);
        telefonoEdt.setClickable(false);
        emailEdt.setClickable(false);
        saveButton.setVisibility(View.GONE);
    }

    /**
     * Configura la vista para crear un nuevo Contacto
     **/
    private void registerConfig() {
        titulo.setText("Nuevo Contacto");
        //Guardar los datos
        saveButton.setOnClickListener(v -> {
            String nombre = nombreEdt.getText().toString();
            String telefono = telefonoEdt.getText().toString();
            String email = emailEdt.getText().toString();
            validateFields(nombre, telefono, email, "1", idCliente);
        });
    }

    /**
     * Valida los campos
     **/
    private void validateFields(String nombre, String telefono, String email, String option, String idCliente) {
        if (nombre.isEmpty()) {
            nombreEdt.setError("El nombre es obligatorio");
        }
        if (telefono.isEmpty()) {
            telefonoEdt.setError("El teléfono es obligatorio");
        }
        if (email.isEmpty()) {
            emailEdt.setError("El email es obligatorio");
        }
        if (!nombre.isEmpty() && !telefono.isEmpty() && !email.isEmpty()) {
            nombreEdt.setError(null);
            telefonoEdt.setError(null);
            emailEdt.setError(null);
            if (option.equals("1")) {
                insertarContacto(nombre, telefono, email);
            }
            if (option.equals("2")) {
                actualizarContacto(nombre, telefono, email);
            }
        }
    }

    /**
     * Insertar datos en la tabla contacto
     **/
    private void insertarContacto(String nombre, String telefono, String email) {
        ContactoDB contactoDB = new ContactoDB(ContactoRegistroActivity.this);
        long id = contactoDB.insert(nombre, Integer.parseInt(idCliente), telefono, email);
        if (id > 0) {
            cleanFields();
            Toast.makeText(this, "Registro " + id + " exitoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: Registro existente", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Actualizar datos en la tabla contacto
     **/
    private void actualizarContacto(String nombre, String telefono, String email) {
        ContactoDB contactoDB = new ContactoDB(ContactoRegistroActivity.this);
        boolean updated = contactoDB.update(Integer.parseInt(idContacto), nombre, telefono, email);
        if (updated) {
            Toast.makeText(this, "Actualizacion exitosa", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error: No se pudo actualizar", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Limpiar los campos
     **/
    private void cleanFields() {
        nombreEdt.setText("");
        telefonoEdt.setText("");
        emailEdt.setText("");
    }
}