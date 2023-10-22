package com.jcjayodev.contactoscontrol.presenter;

import android.content.Context;

import com.jcjayodev.contactoscontrol.model.Cliente;
import com.jcjayodev.contactoscontrol.model.database.ClienteDB;
import com.jcjayodev.contactoscontrol.view.cliente.RVClienteView;

import java.util.List;

public class RVClientePresenter {
    private RVClienteView view;
    Context context;

    public RVClientePresenter(RVClienteView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void loadData() {
        // Obtener los datos del modelo (por ejemplo, desde una base de datos o una API
        List<Cliente> itemList;
        ClienteDB db = new ClienteDB(context);
        itemList = db.getAll();
        // Pasar los datos a la Vista para mostrarlos en el RecyclerView
        view.showData(itemList);
    }

    public void searchElement(String cadena) {
        // Obtener los datos del modelo (por ejemplo, desde una base de datos o una API
        List<Cliente> itemList;
        ClienteDB db = new ClienteDB(context);
        itemList = db.search(cadena);
        // Pasar los datos a la Vista para mostrarlos en el RecyclerView
        view.showData(itemList);
    }
}