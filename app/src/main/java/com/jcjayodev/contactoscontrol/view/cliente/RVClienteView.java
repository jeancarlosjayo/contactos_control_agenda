package com.jcjayodev.contactoscontrol.view.cliente;

import com.jcjayodev.contactoscontrol.model.Cliente;

import java.util.List;

public interface RVClienteView {
    /**
     * Cargar los datos
     **/
    void showData(List<Cliente> itemList);
    /**
     * Asigna el evento de click de un elemento
     **/
    void onItemClick(Cliente item);
}
