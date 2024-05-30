package com.example.proyecto1;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GeneraVenta extends StringRequest {
    private static final String GENERAR_VENTA_URL="http://192.168.56.1/controlLadrillera/Register.php";
     private Map<String,String> params;
     public GeneraVenta(String ladrillo, int cantidad, String tipo, double precio, Response.Listener<String>listener){
         super(Method.POST, GENERAR_VENTA_URL,listener, null);
         //definimos parametros
         params=new HashMap<>();
         params.put("ladrillo",ladrillo);
         params.put("cantidad",cantidad+"");
         params.put("tipo",tipo);
         params.put("precio",precio+"");
     }

    @Nullable
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
