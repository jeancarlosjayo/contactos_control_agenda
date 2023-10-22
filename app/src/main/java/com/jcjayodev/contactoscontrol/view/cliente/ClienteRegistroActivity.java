package com.jcjayodev.contactoscontrol.view.cliente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jcjayodev.contactoscontrol.R;
import com.jcjayodev.contactoscontrol.model.Cliente;
import com.jcjayodev.contactoscontrol.model.database.ClienteDB;

public class ClienteRegistroActivity extends AppCompatActivity {
    EditText nombreEdt;
    EditText domicilioEdt;
    EditText cpEdt;
    EditText poblacionEdt;
    Button saveButton;
    ImageView backButton, imageAccount;
    TextView titulo;

    String idCliente;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_registro);
        //Vincular los elementos de la interfaz
        titulo = findViewById(R.id.title);
        nombreEdt = findViewById(R.id.nombre);
        domicilioEdt = findViewById(R.id.domicilio);
        cpEdt = findViewById(R.id.codigo_postal);
        poblacionEdt = findViewById(R.id.poblacion);
        saveButton = findViewById(R.id.boton);
        backButton = findViewById(R.id.backButton);
        imageAccount = findViewById(R.id.imageAccount);
        //Regresar a la lista de clientes
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
                idCliente = id;
            } else {
                option = extras.getString("option");
                id = extras.getString("id");
                idCliente = id;
            }
            optionConfig(option, id);
        } else {
            String option = (String) savedInstanceState.getSerializable("option");
            String id = (String) savedInstanceState.getSerializable("id");
            idCliente = id;
            optionConfig(option, id);
        }
    }

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

    private void registerConfig() {
        titulo.setText("Nuevo Cliente");
        //Guardar los datos
        saveButton.setOnClickListener(v -> {
            String nombre = nombreEdt.getText().toString();
            String domicilio = domicilioEdt.getText().toString();
            String cp = cpEdt.getText().toString();
            String poblacion = poblacionEdt.getText().toString();
            validateFields(nombre, domicilio, cp, poblacion, "1");
        });
    }

    private void updateConfig(String id) {
        //Cambiar el título de la aplicación
        titulo.setText("Editar Cliente");
        //Obtener datos del usuario
        ClienteDB clienteDB = new ClienteDB(ClienteRegistroActivity.this);
        Cliente cliente = clienteDB.getItem(Integer.parseInt(id));
        nombreEdt.setText(cliente.getNombre());
        domicilioEdt.setText(cliente.getDomicilio());
        cpEdt.setText(cliente.getCodigo_postal());
        poblacionEdt.setText(cliente.getPoblacion());
        saveButton.setText("Actualizar");
        saveButton.setOnClickListener(v -> {
            String nombre = nombreEdt.getText().toString();
            String domicilio = domicilioEdt.getText().toString();
            String cp = cpEdt.getText().toString();
            String poblacion = poblacionEdt.getText().toString();
            validateFields(nombre, domicilio, cp, poblacion, "2");
        });
    }

    private void visualizerConfig(String id) {
        //Cambiar el título de la aplicación
        titulo.setText("");
        //Obtener datos del usuario
        ClienteDB clienteDB = new ClienteDB(ClienteRegistroActivity.this);
        Cliente cliente = clienteDB.getItem(Integer.parseInt(id));
        nombreEdt.setText(cliente.getNombre());
        domicilioEdt.setText(cliente.getDomicilio());
        cpEdt.setText(cliente.getCodigo_postal());
        poblacionEdt.setText(cliente.getPoblacion());
        nombreEdt.setClickable(false);
        domicilioEdt.setClickable(false);
        cpEdt.setClickable(false);
        poblacionEdt.setClickable(false);
        nombreEdt.setEnabled(false);
        poblacionEdt.setEnabled(false);
        cpEdt.setEnabled(false);
        domicilioEdt.setEnabled(false);
        saveButton.setVisibility(View.GONE);
    }

    /**
     * Controlar la pulsación del botón de regresar
     **/
    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * Validar los campos
     **/
    private void validateFields(String nombre, String domicilio, String cp, String poblacion, String option) {
        if (nombre.isEmpty()) {
            nombreEdt.setError("El nombre es obligatorio");
        }
        if (domicilio.isEmpty()) {
            domicilioEdt.setError("El domicilio es obligatorio");
        }
        if (cp.isEmpty()) {
            cpEdt.setError("El código postal es obligatorio");
        }
        if (poblacion.isEmpty()) {
            poblacionEdt.setError("La población es obligatorio");
        }
        if (!nombre.isEmpty() && !domicilio.isEmpty() && !cp.isEmpty() && !poblacion.isEmpty()) {
            nombreEdt.setError(null);
            domicilioEdt.setError(null);
            cpEdt.setError(null);
            poblacionEdt.setError(null);
            if (option.equals("1")) {
                insertarCliente(nombre, domicilio, cp, poblacion);
            }
            if (option.equals("2")) {
                actualizarCliente(nombre, domicilio, cp, poblacion);
            }
        }
    }

    private void actualizarCliente(String nombre, String domicilio, String cp, String poblacion) {
        ClienteDB clienteDB = new ClienteDB(ClienteRegistroActivity.this);
        boolean updated = clienteDB.update(Integer.parseInt(idCliente), nombre, domicilio, cp, poblacion);
        if (updated) {
            Toast.makeText(this, "Actualizacion exitosa", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error: No se pudo actualizar", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Insertar datos en la tabla de cliente
     **/
    private void insertarCliente(String nombre, String domicilio, String cp, String poblacion) {
        ClienteDB clienteDB = new ClienteDB(ClienteRegistroActivity.this);
        long id = clienteDB.insert(nombre, domicilio, cp, poblacion);
        if (id > 0) {
            cleanFields();
            Toast.makeText(this, "Registro " + id + " exitoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: Registro existente", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Limpiar los campos
     **/
    private void cleanFields() {
        nombreEdt.setText("");
        domicilioEdt.setText("");
        cpEdt.setText("");
        poblacionEdt.setText("");
    }

}