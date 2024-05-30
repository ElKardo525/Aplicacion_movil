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

public class Produccion extends AppCompatActivity implements View.OnClickListener {

    EditText txtlad2, txtcantidad2, txttipo2;
    Button btn_guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_produccion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtlad2 = findViewById(R.id.txt_lad2);
        txtcantidad2 = findViewById(R.id.txt_cantidad2);
        txttipo2 = findViewById(R.id.txt_tipo2);
        btn_guardar = findViewById(R.id.btn_generar);

        btn_guardar.setOnClickListener(this);
    }

    public void regresar (View view){
        Intent regresar  = new Intent(this, MenuPrincipal.class);
        startActivity(regresar);
    }


    @Override
    public void onClick(View v) {
        final String ladrillo=txtlad2.getText().toString();
        final int cantidad=Integer.parseInt(txtcantidad2.getText().toString());
        final String tipo=txttipo2.getText().toString();

        Response.Listener<String> respoListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Toast.makeText(Produccion.this, "Registro guardado", Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Produccion.this);
                        builder.setMessage("Error Registro de Venta").setNegativeButton("Retry", null).create().show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        GenerarProducto generarProducto = new GenerarProducto(ladrillo,cantidad,tipo,respoListener);
        RequestQueue queue = Volley.newRequestQueue(Produccion.this);
        queue.add(generarProducto);
    }
}