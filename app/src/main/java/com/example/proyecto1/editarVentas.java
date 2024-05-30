package com.example.proyecto1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class editarVentas extends AppCompatActivity {

    EditText edLad, edCant, edTip, edPrec;
    private int position;
    private String idVenta;
    private String fechaCreacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_ventas);

        edLad = findViewById(R.id.txt_ladAct);
        edCant = findViewById(R.id.txt_cantidadAct);
        edTip = findViewById(R.id.txt_tipoAct);
        edPrec = findViewById(R.id.txt_precioAct);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        idVenta = Inventario.ventasArrayList.get(position).getIdVenta();
        fechaCreacion = Inventario.ventasArrayList.get(position).getFecha_creacion();

        edLad.setText(Inventario.ventasArrayList.get(position).getLadrillo());
        edCant.setText(Inventario.ventasArrayList.get(position).getCantidad());
        edTip.setText(Inventario.ventasArrayList.get(position).getTipo());
        edPrec.setText(Inventario.ventasArrayList.get(position).getPrecio());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void actualizar(View view){
        final String ladrillo = edLad.getText().toString().trim();
        final String cantidad = edCant.getText().toString().trim();
        final String tipo = edTip.getText().toString().trim();
        final String precio = edPrec.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Actualizando");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.56.1/controlLadrillera/actualizarVenta.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(editarVentas.this, response, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Inventario.class));
                finish();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editarVentas.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("IdVenta", idVenta);
                params.put("ladrillo", ladrillo);
                params.put("cantidad", cantidad);
                params.put("tipo", tipo);
                params.put("precio", precio);
                params.put("fecha_creacion", fechaCreacion);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(editarVentas.this);
        requestQueue.add(request);
    }
}