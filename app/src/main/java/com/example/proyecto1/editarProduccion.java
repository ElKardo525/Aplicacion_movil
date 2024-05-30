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

public class editarProduccion extends AppCompatActivity {
    // Declaramos las variables
    EditText edLad,edCant,edTip;
    private int position;
    private String idProducto;
    private String fechaCreacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Establecemos el diseño del Menu Principal
        setContentView(R.layout.activity_editar_produccion);
        //Inicializamos de componentes de la interfaz de usuario
        edLad = findViewById(R.id.txt_ladAct);
        edCant = findViewById(R.id.txt_cantidadAct);
        edTip = findViewById(R.id.txt_tipoAct);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        //Obtenemos los datos del array list basado en la posición
        idProducto = InventarioProduccion.produccionRegistroArrayList.get(position).getIdProducto();
        fechaCreacion = InventarioProduccion.produccionRegistroArrayList.get(position).getFecha_creacion();
        // Establecer los valores en los EditTexts
        edLad.setText(InventarioProduccion.produccionRegistroArrayList.get(position).getLadrillo());
        edCant.setText(InventarioProduccion.produccionRegistroArrayList.get(position).getCantidad());
        edTip.setText(InventarioProduccion.produccionRegistroArrayList.get(position).getTipo());
        // Ajustamos los insets de la ventana para la vista principal
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //Método para actualizar los datos de producción
    public void actualizar(View view){
        // Obtener los valores ingresados por el usuario
        final String ladrillo = edLad.getText().toString().trim();
        final String cantidad = edCant.getText().toString().trim();
        final String tipo = edTip.getText().toString().trim();
        // Mostrarmos un ProgressDialog mientras se realiza la operación
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Actualizado");
        progressDialog.show();
        // Creamos una solicitud POST para actualizar los datos de producción medainte le url
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.56.1/controlLadrillera/actualizarProducto.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Mostramos la respuesta del servidor
                Toast.makeText(editarProduccion.this, response, Toast.LENGTH_SHORT).show();
                // Inicia la actividad InventarioProduccion y finalizar la actual
                startActivity(new Intent(getApplicationContext(), InventarioProduccion.class));
                finish();
                // Cierra el ProgressDialog
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Muestra un mensaje de error
                Toast.makeText(editarProduccion.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Preparar los parámetros a enviar en la solicitud POST
                Map<String,String> params = new HashMap<>();
                params.put("IdProducto", idProducto);
                params.put("ladrillo", ladrillo);
                params.put("cantidad", cantidad);
                params.put("tipo", tipo);
                params.put("fehca_creacion", fechaCreacion);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(editarProduccion.this);
        requestQueue.add(request);
    }
}