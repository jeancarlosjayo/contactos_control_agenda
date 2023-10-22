package com.jcjayodev.contactoscontrol.view.contacto;

import com.jcjayodev.contactoscontrol.model.Contacto;

import java.util.List;

public interface RVContactoView {
    /**
     * Cargar los datos
     **/
    void showData(List<Contacto> itemList);

    /**
     * Asigna el elemento seleccionado de la lista de Contactos
     **/
    void onItemClick(Contacto item);
}
