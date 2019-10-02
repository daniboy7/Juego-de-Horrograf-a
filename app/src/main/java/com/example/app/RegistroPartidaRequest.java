package com.example.app;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroPartidaRequest extends StringRequest {
    private static  final String ruta = "http://horrography.000webhostapp.com/registroPartida.php";
    private Map<String,String> parametros;

    public RegistroPartidaRequest(String usuario, String fecha, int nivel, String estado, Double puntuacion, int errores,int aciertos, Response.Listener<String> listener)
    {
        super(Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("usuario",usuario+"");
        parametros.put("fecha",fecha+"");
        parametros.put("nivel",nivel+"");
        parametros.put("estado",estado+"");
        parametros.put("puntuacion",puntuacion+"");
        parametros.put("errores",errores+"");
        parametros.put("aciertos",aciertos+"");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
