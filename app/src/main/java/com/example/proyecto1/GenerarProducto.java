package com.example.proyecto1;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GenerarProducto extends StringRequest {
    private static final String GENERAR_Producto_URL="http://192.168.56.1/controlLadrillera/RegistroProducto.php";
    private Map<String,String> params;
    public GenerarProducto(String ladrillo, int cantidad, String tipo, Response.Listener<String>listener){
        super(Method.POST, GENERAR_Producto_URL,listener, null);
        //definimos parametros
        params=new HashMap<>();
        params.put("ladrillo",ladrillo);
        params.put("cantidad",cantidad+"");
        params.put("tipo",tipo);
    }

    @Nullable
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}