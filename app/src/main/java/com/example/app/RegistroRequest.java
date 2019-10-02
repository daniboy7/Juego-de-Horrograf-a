package com.example.app;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroRequest extends StringRequest {
    private static  final String ruta = "http://horrography.000webhostapp.com/registro.php";
    private Map<String,String> parametros;
    public RegistroRequest(String usuario, String contraseña, String correo, String nombre, String apellidos, int edad, String tipo, Response.Listener<String> listener)
    {
        super(Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("usuario",usuario+"");
        parametros.put("contraseña",contraseña+"");
        parametros.put("correo",correo+"");
        parametros.put("nombre",nombre+"");
        parametros.put("apellidos",apellidos+"");
        parametros.put("edad",edad+"");
        parametros.put("tipo",tipo+"");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
