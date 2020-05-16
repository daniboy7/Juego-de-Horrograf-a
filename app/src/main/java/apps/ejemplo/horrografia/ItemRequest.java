package apps.ejemplo.horrografia;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ItemRequest extends StringRequest {
    //private static  final String ruta ="http://horrography.000webhostapp.com/registroItem.php";
    private static  final String ruta ="http://horrografia.ucr.ac.cr/registroItem.php";
    private Map<String,String> parametros;
    public ItemRequest(String texto_item, String forma_correcta ,String pista, int etapa, int nivel, Response.Listener<String> listener)
    {
        super(Method.POST, ruta,listener,null);
        parametros = new HashMap<>();
        parametros.put("texto_item",texto_item+"");
        parametros.put("forma_correcta",forma_correcta+"");
        parametros.put("pista",pista+"");
        parametros.put("etapa",etapa+"");
        parametros.put("nivel",nivel+"");
    }
    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
