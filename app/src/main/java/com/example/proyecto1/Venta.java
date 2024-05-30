package com.example.proyecto1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Venta extends AppCompatActivity implements View.OnClickListener {
    //Declaramos las variables de la interfaz
    EditText txtlad, txtcantidad, txttipo, txtprecio;
    Button btn_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Establecemos el diseño del Menu Principal
        setContentView(R.layout.activity_venta);
        // Ajustar los insets de la ventana para la vista principal
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Inicializamos las variables de la interfaz de usuario
        txtlad = findViewById(R.id.txt_lad);
        txtcantidad = findViewById(R.id.txt_cantidad);
        txttipo = findViewById(R.id.txt_tipo);
        txtprecio = findViewById(R.id.txt_precio);
        btn_registrar = findViewById(R.id.btn_generar);
        //Asignar el evento de clic al botón registrar
        btn_registrar.setOnClickListener(this);
    }
    //Metodo para regresar al Menu Principal mediante el boton
    public void regresar(View view) {
        Intent regresar = new Intent(this, MenuPrincipal.class);
        startActivity(regresar);
    }
    //Manejamos los clicks en los botones
    @Override
    public void onClick(View view) {
        // Obtener los valores ingresados por el usuario
        final String ladrillo = txtlad.getText().toString();
        final int cantidad = Integer.parseInt(txtcantidad.getText().toString());
        final String tipo = txttipo.getText().toString();
        final double precio = Double.parseDouble(txtprecio.getText().toString());
        // Crear un listener para manejar la respuesta del servidor
        Response.Listener<String> respoListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        // Mostrar mensaje de éxito al guardar un registro
                        Toast.makeText(Venta.this, "Registro guardado", Toast.LENGTH_LONG).show();
                    } else {
                        // Mostramos un mensaje de error al no poder guardar el registro
                        AlertDialog.Builder builder = new AlertDialog.Builder(Venta.this);
                        builder.setMessage("Error Registro de Venta").setNegativeButton("Retry", null).create().show();
                    }

                } catch (JSONException e) {
                    // Manejar excepción de parseo
                    throw new RuntimeException(e);
                }
            }
        };
        // Creamos la solicitud para registrar la venta
        GeneraVenta generaVenta = new GeneraVenta(ladrillo, cantidad, tipo, precio, respoListener);
        RequestQueue queue = Volley.newRequestQueue(Venta.this);
        queue.add(generaVenta);
    }
}