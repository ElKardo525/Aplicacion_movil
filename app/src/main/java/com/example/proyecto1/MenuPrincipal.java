package com.example.proyecto1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Establecemos el diseño del Menu Principal
        setContentView(R.layout.activity_menu_principal);

        // Ajustar el padding de la vista principal para considerar las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //Metodo para dirigirnosa la  interfaz Ventas
    public void irVenta(View view){
        Intent irVenta  = new Intent(this, Venta.class);
        startActivity(irVenta);
    }
    //Metodo para dirigirnosa la  interfaz Inventario
    public void irRegistroVentas(View view){
        Intent irInventario = new Intent(this, Inventario.class);
        startActivity(irInventario);
    }
    //Metodo para dirigirnosa la  interfaz Productos
    public void irProducto(View view){
        Intent irProducto = new Intent(this, Producto.class);
        startActivity(irProducto);
    }
    //Metodo para dirigirnosa la  interfaz Produccion
    public void irProduccion(View view){
        Intent irProduccion = new Intent(this, Produccion.class);
        startActivity(irProduccion);
    }
    //Metodo para dirigirnosa la  interfaz RegistroProduccion
    public void irRegistroProducto(View view){
        Intent irProduccion = new Intent(this, InventarioProduccion.class);
        startActivity(irProduccion);
    }
}