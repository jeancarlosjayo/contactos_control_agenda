package com.jcjayodev.contactoscontrol.view.contacto;

import com.jcjayodev.contactoscontrol.model.Contacto;

import java.util.List;

public interface RVContactoView {
    void showData(List<Contacto> itemList);

    void onItemClick(Contacto item);
}
