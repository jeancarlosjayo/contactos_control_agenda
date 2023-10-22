package com.jcjayodev.contactoscontrol.view.cliente;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jcjayodev.contactoscontrol.R;
import com.jcjayodev.contactoscontrol.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class RVClienteAdapter extends RecyclerView.Adapter<RVClienteAdapter.ViewHolder> {
    private List<Cliente> itemList;
    private RVClienteView view;

    public RVClienteAdapter(RVClienteView view) {
        this.view = view;
        itemList = new ArrayList<>();
    }

    public void setData(List<Cliente> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cliente item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nombre;
        private final TextView domicilio;
        private final TextView cp;
        private final TextView poblacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            domicilio = itemView.findViewById(R.id.domicilio);
            cp = itemView.findViewById(R.id.codigoPostal);
            poblacion = itemView.findViewById(R.id.poblacion);
            itemView.setOnClickListener(this);
        }

        public void bind(Cliente item) {
            nombre.setText(item.getNombre());
            domicilio.setText("Domicilio: " + item.getDomicilio());
            cp.setText("Código Postal: " + item.getCodigo_postal());
            poblacion.setText("Población: " + item.getPoblacion());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Cliente item = itemList.get(position);
                view.onItemClick(item);
            }
        }
    }
}
