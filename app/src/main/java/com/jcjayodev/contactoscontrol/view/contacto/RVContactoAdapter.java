package com.jcjayodev.contactoscontrol.view.contacto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jcjayodev.contactoscontrol.R;
import com.jcjayodev.contactoscontrol.model.Contacto;

import java.util.ArrayList;
import java.util.List;

public class RVContactoAdapter extends RecyclerView.Adapter<RVContactoAdapter.ViewHolder> {
    private List<Contacto> itemList;
    private RVContactoView view;

    /**
     * Constructor
     **/
    public RVContactoAdapter(RVContactoView view) {
        this.view = view;
        itemList = new ArrayList<>();
    }

    /**
     * Asigna los datos
     **/
    public void setData(List<Contacto> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    /**
     * Crea un elemento
     **/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacto, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Asigna los datos de un elemento
     **/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacto item = itemList.get(position);
        holder.bind(item);
    }

    /**
     * Obtiene la cantidad de elementos
     **/
    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nombre;
        private final TextView telefono;
        private final TextView email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            telefono = itemView.findViewById(R.id.telefono);
            email = itemView.findViewById(R.id.email);
            itemView.setOnClickListener(this);
        }

        /**
         * Asigna los datos
         **/
        public void bind(Contacto item) {
            nombre.setText(item.getNombre());
            telefono.setText("Telefono: " + item.getTelefono());
            email.setText("Email: " + item.getEmail());
        }

        /**
         * Implementa la interfaz OnClickListener
         **/
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Contacto item = itemList.get(position);
                view.onItemClick(item);
            }
        }
    }
}
