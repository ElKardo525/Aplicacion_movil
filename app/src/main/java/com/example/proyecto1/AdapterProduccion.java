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

public class AdapterProduccion extends ArrayAdapter<ProduccionRegistro> {

    Context context;
    List<ProduccionRegistro> arrayProduccionRegistros;

    public AdapterProduccion(@NonNull Context context, List<ProduccionRegistro> arrayProduccionRegistros) {
        super(context, R.layout.item_produccion, arrayProduccionRegistros);
        this.context = context;
        this.arrayProduccionRegistros = arrayProduccionRegistros;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produccion, null, true);

        TextView txtID = view.findViewById(R.id.txt_id);
        TextView txtNomLad = view.findViewById(R.id.txt_ladrillo);
        TextView txtCanLad = view.findViewById(R.id.txt_can);
        TextView txtTipLad = view.findViewById(R.id.txt_ti);
        TextView txtFechaVen = view.findViewById(R.id.txt_fecha);

        txtID.setText("ID: " + arrayProduccionRegistros.get(position).getIdProducto());
        txtNomLad.setText("Ladrillo: " + arrayProduccionRegistros.get(position).getLadrillo());
        txtCanLad.setText("Cantidad: " + arrayProduccionRegistros.get(position).getCantidad());
        txtTipLad.setText("Tipo: " + arrayProduccionRegistros.get(position).getTipo());
        txtFechaVen.setText("Fecha: " + arrayProduccionRegistros.get(position).getFecha_creacion());
        return view;
    }
}