package com.example.proyecto1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter extends ArrayAdapter<Ventas> {
    //Declaracion de variables tipo Context y tipo List
    Context context;
    List<Ventas> arrayventas;
    //Constructor del adaptador que recibe el contexto y la lista de ventas
    public Adapter(@NonNull Context context, List<Ventas> arrayventas) {
        super(context, R.layout.item_venta, arrayventas);
        this.context = context;
        this.arrayventas = arrayventas;
    }
    //Método que se llama para obtener la vista de un elemento de la lista
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Inflamos el layout del item_venta para crear la vista de un elemento de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venta, null, true);

        // Obtener referencias a los TextView dentro del layout del item
        TextView txtID = view.findViewById(R.id.txt_id);
        TextView txtNomLad = view.findViewById(R.id.txt_ladrillo);
        TextView txtCanLad = view.findViewById(R.id.txt_can);
        TextView txtTipLad = view.findViewById(R.id.txt_ti);
        TextView txtPreLad = view.findViewById(R.id.txt_pre);
        TextView txtFechaVen = view.findViewById(R.id.txt_fecha);

        // Concatenar etiquetas con los valores de los campos
        txtID.setText("ID: " + arrayventas.get(position).getIdVenta());
        txtNomLad.setText("Ladrillo: " + arrayventas.get(position).getLadrillo());
        txtCanLad.setText("Cantidad: " + arrayventas.get(position).getCantidad());
        txtTipLad.setText("Tipo: " + arrayventas.get(position).getTipo());
        txtPreLad.setText("Precio: " + arrayventas.get(position).getPrecio());
        txtFechaVen.setText("Fecha: " + arrayventas.get(position).getFecha_creacion());

        // Devolver la vista configurada del elemento de la lista
        return view;
    }
}