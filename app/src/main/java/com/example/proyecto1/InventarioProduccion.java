package com.example.proyecto1;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventarioProduccion extends AppCompatActivity {

    ListView listView;
    AdapterProduccion adapterProduccion;
    public static ArrayList<ProduccionRegistro> produccionRegistroArrayList = new ArrayList<>();
    String url = "http://192.168.56.1/controlLadrillera/mostrarProduccion.php";
    ProduccionRegistro produccionRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventario_produccion);
        listView = findViewById(R.id.listMostrar2);
        adapterProduccion = new AdapterProduccion(this,produccionRegistroArrayList);
        listView.setAdapter(adapterProduccion);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                CharSequence[] dialogItem = {"Editar", "Eliminar"};
                builder.setTitle(produccionRegistroArrayList.get(position).getLadrillo());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                startActivity(new Intent(getApplicationContext(), editarProduccion.class)
                                        .putExtra("position", position));
                                break;
                            case 1:
                                EliminarDatos(produccionRegistroArrayList.get(position).getIdProducto());
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

        ListarDatos();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void EliminarDatos(String idProducto) {
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.56.1/controlLadrillera/eliminarProducto.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Datos Eliminados")) {
                    Toast.makeText(InventarioProduccion.this, "Elimiado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), InventarioProduccion.class));
                } else {
                    Toast.makeText(InventarioProduccion.this, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(InventarioProduccion.this, "error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("IdProducto", idProducto);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void regresar(View view) {
        Intent regresar = new Intent(this, MenuPrincipal.class);
        startActivity(regresar);
    }

    private void ListarDatos() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Inventario", "Response: " + response);
                produccionRegistroArrayList.clear();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    if (exito.equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("datos");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String idproducto = object.optString("IdProducto", "N/A");
                            String ladrillo = object.optString("ladrillo", "N/A");
                            String cantidad = object.optString("cantidad", "N/A");
                            String tipo = object.optString("tipo", "N/A");
                            String fecha = object.optString("fecha_creacion", "N/A");

                            ProduccionRegistro produccionRegistro = new ProduccionRegistro(idproducto, ladrillo, cantidad, tipo, fecha);
                            produccionRegistroArrayList.add(produccionRegistro);
                        }
                        adapterProduccion.notifyDataSetChanged();
                    } else {
                        Toast.makeText(InventarioProduccion.this, "No se encnotraron datos", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(InventarioProduccion.this, "Error en la respuesta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InventarioProduccion.this, "Error en la red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}