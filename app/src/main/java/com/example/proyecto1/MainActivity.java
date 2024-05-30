package com.example.proyecto1;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.io.StringReader;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Elementos usados en la interfaz de usuario
    EditText txtUsername, txtPassword;
    Button login_btn;
    String usuario, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Inicializar elementos de la interfaz
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        login_btn = findViewById(R.id.login_btn);
        // Recuperar preferencias almacenadas para completar automáticamente los campos
        recuperarPreferencias();

        // Configurar el botón de inicio de sesión para validar el usuario al ser presionado
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            //Metodo para evaluar la accion del boton
            public void onClick(View v) {
                // Obtenemos los valores de usuario y contraseña
                usuario = txtUsername.getText().toString();
                password = txtPassword.getText().toString();
                // Verificar que los campos no estén vacíos
                if(!usuario.isEmpty() && !password.isEmpty()){
                    //Llamamos al metodo para validar el usuario medinate la url
                    validarUsuario("http://192.168.56.1/controlLadrillera/validar.php");
                }else {
                    //Mostramos mensaje en caso de un error
                    Toast.makeText(MainActivity.this,"Campos Vacios",Toast.LENGTH_SHORT).show();
                }

            }
        });
        //Ajustar el padding de la vista principal para considerar las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //Método para validar el usuario con el servidor.
    private void validarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Verificar si la respuesta no está vacía
                if (!response.isEmpty()){
                    // Guardar las preferencias del usuario
                    guardarPreferencias();
                    //Nos dirigimos a la actividad principal del menú
                    Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                    startActivity(intent);
                    finish();
                }else{
                    // Mostrar un mensaje si los datos son incorrectos
                    Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Mostrar un mensaje de error en caso de fallo en la petición
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            //@Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Crear un mapa de parámetros para enviar al servidor
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("correo", usuario);
                parametros.put("password", password);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    //Método para guardar las preferencias del usuario.
    public void guardarPreferencias(){
        // Obtener las preferencias de la aplicación
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        // Guardar las preferencias del usuario
        editor.putString("correo", usuario);
        editor.putString("password", password);
        editor.putBoolean("sesion", true);
        editor.commit();
    }
    //Método para recuperar las preferencias del usuario
    public void recuperarPreferencias(){
        // Obtenemos las preferencias de la aplicación
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        // Establecer los valores recuperados en los campos de texto
        txtUsername.setText(preferences.getString("correo","correo@gmail.com"));
        txtPassword.setText(preferences.getString("password","123456789"));
    }
}