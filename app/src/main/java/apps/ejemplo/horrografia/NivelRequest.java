package apps.ejemplo.horrografia;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NivelRequest extends StringRequest {
    //private static  final String ruta ="http://horrography.000webhostapp.com/registroNivel.php";
    private static  final String ruta ="http://horrografia.ucr.ac.cr/registroNivel.php";
    private Map<String,String> parametros;
    public NivelRequest(String descripcion, Response.Listener<String> listener)
    {
        super(Method.POST,ruta,listener,null);
        parametros = new HashMap<>();
        parametros.put("descripcion",descripcion+"");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }

}
