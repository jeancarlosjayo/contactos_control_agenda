package com.jcjayodev.contactoscontrol.view.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
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
import com.jcjayodev.contactoscontrol.presenter.RVClientePresenter;
import com.jcjayodev.contactoscontrol.view.contacto.ContactoListaActivity;

import java.util.ArrayList;
import java.util.List;

public class ClienteListaActivity extends AppCompatActivity implements RVClienteView {
    FloatingActionButton addClientButton;
    private RecyclerView recyclerView;
    private RVClienteAdapter adapter;
    private RVClientePresenter presenter;
    SearchView searchView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_lista);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RVClienteAdapter(this);
        recyclerView.setAdapter(adapter);
        presenter = new RVClientePresenter(this, ClienteListaActivity.this);
        //presenter.loadData();
        addClientButton = findViewById(R.id.addBtnClientButton);
        addClientButton.setOnClickListener(v -> {
            Intent intent = new Intent(ClienteListaActivity.this, ClienteRegistroActivity.class);
            intent.putExtra("option", "1");
            intent.putExtra("id", "0");
            Log.i("id", "0");
            Log.i("option", "1");
            startActivity(intent);
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    presenter.loadData();
                } else {
                    presenter.searchElement(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    presenter.loadData();
                } else {
                    presenter.searchElement(newText);
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume", "onResume");
        presenter.loadData();
    }

    @Override
    public void showData(List<Cliente> itemList) {
        if (adapter != null) {
            adapter.setData(itemList);
        }
    }

    @Override
    public void onItemClick(Cliente item) {
        // Opciones
        final String[] options = {"Abrir lista de contactos", "Detalle del cliente", "Editar", "Eliminar"};

        // Crear el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opciones").setItems(options, (dialog, which) -> {
            String selectedOption = options[which];

            if (selectedOption.equals("Abrir lista de contactos")) {
                Intent intent = new Intent(ClienteListaActivity.this, ContactoListaActivity.class);
                intent.putExtra("id", String.valueOf(item.getId()));
                Log.i("id", String.valueOf(item.getId()));
                Log.i("option", "Abrir lista de contactos");
                startActivity(intent);
            }

            if (selectedOption.equals("Detalle del cliente")) {
                dialog.dismiss();
                Intent intent = new Intent(ClienteListaActivity.this, ClienteRegistroActivity.class);
                intent.putExtra("option", "2");
                intent.putExtra("id", String.valueOf(item.getId()));
                Log.i("id", String.valueOf(item.getId()));
                Log.i("option", "2");
                startActivity(intent);
            }
            if (selectedOption.equals("Editar")) {
                dialog.dismiss();
                Intent intent = new Intent(ClienteListaActivity.this, ClienteRegistroActivity.class);
                intent.putExtra("option", "3");
                intent.putExtra("id", String.valueOf(item.getId()));
                Log.i("id", String.valueOf(item.getId()));
                Log.i("option", "3");
                startActivity(intent);
            }
            if (selectedOption.equals("Eliminar")) {
                dialog.dismiss();
                AlertDialog.Builder eliminarBuilder = new AlertDialog.Builder(this);
                eliminarBuilder.setTitle("Eliminar");
                eliminarBuilder.setMessage("¿Desea eliminar este cliente?");
                eliminarBuilder.setPositiveButton("Si", (dialog1, which1) -> {
                    dialog1.dismiss();
                    checkContactsCount(item.getId());
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

    private void checkContactsCount(int id) {
        ContactoDB contactoDB = new ContactoDB(ClienteListaActivity.this);
        ArrayList<Contacto> deleted = contactoDB.getAll(id);

        if (deleted.size() > 0) {
            Toast.makeText(this, "No se puede eliminar este cliente, tiene " + deleted.size() + " contactos", Toast.LENGTH_SHORT).show();
        } else {
            ClienteDB clienteDB = new ClienteDB(ClienteListaActivity.this);
            boolean deleted1 = clienteDB.delete(id);
            if (deleted1) {
                Toast.makeText(this, " Eliminado", Toast.LENGTH_SHORT).show();
                presenter.loadData();
            } else {
                Toast.makeText(ClienteListaActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}