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

public class Inventario extends AppCompatActivity {
    //Variables y componentes de la interfaz
    ListView listView;
    Adapter adapter;
    public static ArrayList<Ventas> ventasArrayList = new ArrayList<>();
    String url = "http://192.168.56.1/controlLadrillera/mostrar.php";
    Ventas ventas;

    //Se inicializan los elementos de la interfaz y se configuran los eventos.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Establecer el diseño de la actividad
        setContentView(R.layout.activity_inventario);
        // Inicializar componentes de la interfaz
        listView = findViewById(R.id.listMostrar);
        adapter = new Adapter(this, ventasArrayList);
        listView.setAdapter(adapter);

        // Configuramos el  evento de click en los ítems de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // Crear un mensaje de alerta
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                CharSequence[] dialogItem = {"Editar", "Eliminar"};
                builder.setTitle(ventasArrayList.get(position).getLadrillo());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //Nos dirige a la actividad de edición de ventas
                                startActivity(new Intent(getApplicationContext(), editarVentas.class)
                                        .putExtra("position", position));
                                break;
                            case 1:
                                //Llamamos al metodo EliminarDatos para elimiar el registro seleccionado
                                EliminarDatos(ventasArrayList.get(position).getIdVenta());
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        // Listar los datos al cargar la interfaz
        ListarDatos();
        // Ajustar el padding de la vista principal para considerar las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //Método para eliminar un registro de venta.
    private void EliminarDatos(final String idVenta) {
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.56.1/controlLadrillera/eliminarVenta.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Verificar la respuesta del servidor
                if (response.equalsIgnoreCase("Datos eliminados")) {
                    Toast.makeText(Inventario.this, "Eliminado", Toast.LENGTH_SHORT).show();
                    // Recargar la actividad de inventario
                    startActivity(new Intent(getApplicationContext(), Inventario.class));
                } else {
                    //Muestra un mensaje de error al no poder eliminar un registro
                    Toast.makeText(Inventario.this, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Imprime el error especifico
                Toast.makeText(Inventario.this, "error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Crear parámetros para la solicitud
                Map<String, String> params = new HashMap<>();
                params.put("IdVenta", idVenta);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    //Metodo para agregar la funcion de regresar al menu principal al boton
    public void regresar(View view) {
        Intent regresar = new Intent(this, MenuPrincipal.class);
        startActivity(regresar);
    }

    //Método para listar los datos de ventas desde el servidor.
    private void ListarDatos() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Inventario", "Response: " + response);
                // Limpiar la lista de ventas existente para evitar duplicados
                ventasArrayList.clear();

                try {
                    // Convertir la respuesta del servidor en un objeto JSON
                    JSONObject jsonObject = new JSONObject(response);
                    // Obtener el valor del campo "exito" del objeto JSON
                    String exito = jsonObject.getString("exito");
                    if (exito.equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("datos");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            // Extrae    los datos de cada venta
                            String idventa = object.optString("IdVenta", "N/A");
                            String ladrillo = object.optString("ladrillo", "N/A");
                            String cantidad = object.optString("cantidad", "N/A");
                            String tipo = object.optString("tipo", "N/A");
                            String precio = object.optString("precio", "N/A");
                            String fecha = object.optString("fecha_creacion", "N/A");
                            // Crea un nuevo objeto Ventas con los datos extraídos
                            Ventas ventas = new Ventas(idventa, ladrillo, cantidad, tipo, precio, fecha);
                            // Añade el objeto Ventas a la lista
                            ventasArrayList.add(ventas);
                        }
                        // Notificar al adaptador que los datos han cambiado para actualizar la lista en la interfaz
                        adapter.notifyDataSetChanged();
                    } else {
                        // Muestra un mensaje si no se encontraron datos
                        Toast.makeText(Inventario.this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // Maneja cualquier excepción que ocurra al parsear la respuesta JSON
                    e.printStackTrace();
                    Toast.makeText(Inventario.this, "Error en la respuesta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar errores de la solicitud, como problemas de red o del servidor
                Toast.makeText(Inventario.this, "Error en la red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Añade la solicitud a la cola de peticiones para que se ejecute
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}