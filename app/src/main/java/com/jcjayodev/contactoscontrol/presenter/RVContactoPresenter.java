package com.jcjayodev.contactoscontrol.presenter;

import android.content.Context;

import com.jcjayodev.contactoscontrol.model.Contacto;
import com.jcjayodev.contactoscontrol.model.database.ContactoDB;
import com.jcjayodev.contactoscontrol.view.contacto.RVContactoView;

import java.util.List;

public class RVContactoPresenter {
    private RVContactoView view;
    Context context;

    public RVContactoPresenter(RVContactoView view, Context context) {
        this.view = view;
        this.context = context;
    }
    /**
     * Cargar los datos de la tabla de Contactos
     **/
    public void loadData(int id) {
        List<Contacto> itemList;
        ContactoDB db = new ContactoDB(context);
        itemList = db.getAll(id);
        view.showData(itemList);
    }
    /**
     * Busca un elemento en la tabla de Contactos
     **/
    public void searchElement(String cadena) {
        List<Contacto> itemList;
        ContactoDB db = new ContactoDB(context);
        itemList = db.search(cadena);
        view.showData(itemList);
    }
}