package com.jcjayodev.contactoscontrol.view.contacto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jcjayodev.contactoscontrol.R;
import com.jcjayodev.contactoscontrol.model.Cliente;
import com.jcjayodev.contactoscontrol.model.Contacto;
import com.jcjayodev.contactoscontrol.model.database.ClienteDB;
import com.jcjayodev.contactoscontrol.model.database.ContactoDB;
import com.jcjayodev.contactoscontrol.presenter.RVContactoPresenter;

import java.util.List;

public class ContactoListaActivity extends AppCompatActivity implements RVContactoView {
    String idCliente;
    TextView title;
    Cliente cliente;
    FloatingActionButton addContactButton;
    private RecyclerView recyclerView;
    private RVContactoAdapter adapter;
    private RVContactoPresenter presenter;
    SearchView searchView;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto_lista);
        title = findViewById(R.id.title);
        backButton = findViewById(R.id.backButton);
        //Recibir elementos del intent
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            String id;
            if (extras == null) {
                id = "0";
            } else {
                id = extras.getString("id");
            }
            idCliente = id;
            loadInfoCliente(idCliente);
        } else {
            idCliente = (String) savedInstanceState.getSerializable("id");
            loadInfoCliente(idCliente);
        }
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RVContactoAdapter(this);
        recyclerView.setAdapter(adapter);
        presenter = new RVContactoPresenter(this, ContactoListaActivity.this);
        //presenter.loadData();
        addContactButton = findViewById(R.id.floatingActionButton);
        addContactButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactoListaActivity.this, ContactoRegistroActivity.class);
            intent.putExtra("option", "1");
            intent.putExtra("idContacto", "0");
            intent.putExtra("idCliente", idCliente);
            Log.i("id", "0");
            Log.i("option", "1");
            startActivity(intent);
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    presenter.loadData(Integer.parseInt(idCliente));
                } else {
                    presenter.searchElement(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    presenter.loadData(Integer.parseInt(idCliente));
                } else {
                    presenter.searchElement(newText);
                }
                return false;
            }
        });
        backButton.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void loadInfoCliente(String id) {
        ClienteDB db = new ClienteDB(this);
        cliente = db.getItem(Integer.parseInt(id));
        title.setText("Lista de Contactos de " + cliente.getNombre());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume", "onResume");
        presenter.loadData(Integer.parseInt(idCliente));
    }

    @Override
    public void showData(List<Contacto> itemList) {
        if (adapter != null) {
            adapter.setData(itemList);
        }
    }

    @Override
    public void onItemClick(Contacto item) {
        // Opciones
        final String[] options = {"Detalle del contacto", "Editar", "Eliminar"};

        // Crear el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opciones").setItems(options, (dialog, which) -> {
            String selectedOption = options[which];

            if (selectedOption.equals("Detalle del contacto")) {
                dialog.dismiss();
                Intent intent = new Intent(ContactoListaActivity.this, ContactoRegistroActivity.class);
                intent.putExtra("option", "2");
                intent.putExtra("idContacto", String.valueOf(item.getId()));
                intent.putExtra("idCliente", idCliente);
                Log.i("id", String.valueOf(item.getId()));
                Log.i("option", "2");
                startActivity(intent);
            }
            if (selectedOption.equals("Editar")) {
                dialog.dismiss();
                Intent intent = new Intent(ContactoListaActivity.this, ContactoRegistroActivity.class);
                intent.putExtra("option", "3");
                intent.putExtra("idContacto", String.valueOf(item.getId()));
                intent.putExtra("idCliente", idCliente);
                Log.i("id", String.valueOf(item.getId()));
                Log.i("option", "3");
                startActivity(intent);
            }
            if (selectedOption.equals("Eliminar")) {
                dialog.dismiss();
                AlertDialog.Builder eliminarBuilder = new AlertDialog.Builder(this);
                eliminarBuilder.setTitle("Eliminar");
                eliminarBuilder.setMessage("¿Desea eliminar este contacto?");
                eliminarBuilder.setPositiveButton("Si", (dialog1, which1) -> {
                    dialog1.dismiss();
                    //checkContactsCount(item.getId());
                    ContactoDB contactoDB = new ContactoDB(ContactoListaActivity.this);
                    boolean deleted = contactoDB.delete(item.getId());
                    if (deleted) {
                        Toast.makeText(this, " Eliminado", Toast.LENGTH_SHORT).show();
                        presenter.loadData(Integer.parseInt(idCliente));
                    } else {
                        Toast.makeText(ContactoListaActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", (dialog1, which1) -> {
                    dialog1.dismiss();
                    Toast.makeText(this, "Eliminación cancelada", Toast.LENGTH_SHORT).show();
                }).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        // Mostrar el AlertDialog
        alertDialog.show();
    }
}