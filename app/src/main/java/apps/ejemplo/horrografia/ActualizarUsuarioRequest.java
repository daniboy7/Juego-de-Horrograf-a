package apps.ejemplo.horrografia;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ActualizarUsuarioRequest extends StringRequest {
    //private static  final String ruta = "http://horrography.000webhostapp.com/actualizarUsuario.php";
    private static final String ruta = "http://horrografia.ucr.ac.cr/actualizarUsuario.php";
    private Map<String,String> parametros;
    public  ActualizarUsuarioRequest(String usuario,String contraseña,String correo, String nombre,String apellidos,int edad,int id_usuario,String tipo, Response.Listener<String> listener)
    {
        super(Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("usuario",usuario+"");
        parametros.put("contraseña",contraseña+"");
        parametros.put("correo",correo+"");
        parametros.put("nombre",nombre+"");
        parametros.put("apellidos",apellidos+"");
        parametros.put("edad",edad+"");
        parametros.put("id_usuario",id_usuario+"");
        parametros.put("tipo",tipo+"");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
