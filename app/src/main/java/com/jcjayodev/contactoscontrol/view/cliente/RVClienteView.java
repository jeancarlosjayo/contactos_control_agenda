package com.jcjayodev.contactoscontrol.view.cliente;

import com.jcjayodev.contactoscontrol.model.Cliente;

import java.util.List;

public interface RVClienteView {
    void showData(List<Cliente> itemList);

    void onItemClick(Cliente item);
}
