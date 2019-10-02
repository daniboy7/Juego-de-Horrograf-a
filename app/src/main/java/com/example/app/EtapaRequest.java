package com.example.app;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EtapaRequest extends StringRequest {
    private static  final String ruta ="http://horrography.000webhostapp.com/registroEtapa.php";
    private Map<String,String> parametros;
    public EtapaRequest(String descripcion, Double valor_error, Double valor_puntuacion, int errores_no_permitidos, int minutos , int numero_items, Response.Listener<String> listener)
    {
        super(Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("descripcion",descripcion+"");
        parametros.put("valor_error",valor_error+"");
        parametros.put("valor_puntuacion",valor_puntuacion+"");
        parametros.put("errores_no_permitidos",errores_no_permitidos+"");
        parametros.put("minutos",minutos+"");
        parametros.put("numero_items",numero_items+"");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
