package apps.ejemplo.horrografia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class ActualizarUsuario extends AppCompatActivity {
    EditText usuarioT;
    EditText passwordT;
    EditText correoT;
    EditText nombreT;
    EditText apellidosT;
    EditText edadT;

    Button btnConsultar;
    Button btnActualizar;
    private CircleButton btnhome;

    Usuarios jugadorActual;

    String nombreUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);

        usuarioT = (EditText)findViewById(R.id.usuario);
        passwordT = (EditText)findViewById(R.id.password);
        correoT = (EditText)findViewById(R.id.correo);
        nombreT =(EditText)findViewById(R.id.nombre);
        apellidosT = (EditText)findViewById(R.id.apellidos);
        edadT = (EditText)findViewById(R.id.edad);

        btnConsultar = (Button)findViewById(R.id.btnConsultar);
        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnhome = (CircleButton)findViewById(R.id.btnHome);

        nombreUsuario = getIntent().getStringExtra("nombre");

        getDataJugador(nombreUsuario);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDataUsuario(jugadorActual.getId_usuario());

            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usuario = usuarioT.getText().toString();
                String contraseña = passwordT.getText().toString();
                String correo = correoT.getText().toString();
                final String nombre = nombreT.getText().toString();
                String apellidos = apellidosT.getText().toString();
                int edad = Integer.parseInt(edadT.getText().toString());
                int id_usuario = jugadorActual.getId_usuario();
                String tipo = "jugador";

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");
                            if(ok==true){
                                Toast.makeText(getApplicationContext(),"Datos personales actualizados",Toast.LENGTH_SHORT).show();
                                usuarioT.setText("");
                                passwordT.setText("");
                                correoT.setText("");
                                nombreT.setText("");
                                apellidosT.setText("");
                                edadT.setText("");
                                setNombreUsuario(nombre);
                            }else{
                                Toast.makeText(getApplicationContext(),"Fallo en actualizar datos personales",Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };
                ActualizarUsuarioRequest r = new ActualizarUsuarioRequest(usuario,contraseña,correo, nombre,
                        apellidos,edad,id_usuario,tipo,respuesta);
                RequestQueue cola = Volley.newRequestQueue(ActualizarUsuario.this);
                cola.add(r);
            }
        });

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(getApplicationContext(),Home.class);
                intentHome.putExtra("nombre",nombreUsuario);
                ActualizarUsuario.this.startActivity(intentHome);
                ActualizarUsuario.this.finish();
            }
        });
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    private void getDataUsuario(int id_usuario) {
        /*String url ="http://horrography.000webhostapp.com/obtenerUsuario.php?"; */
        String url ="http://horrografia.ucr.ac.cr/obtenerUsuario.php?";
        String tipo = "jugador";
        String parametros = "id_usuario="+id_usuario+"&tipo="+tipo;
        StringRequest stringRequest = new StringRequest(url + parametros,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        fillUsuario(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue cola = Volley.newRequestQueue(ActualizarUsuario.this);
        cola.add(stringRequest);
    }

    private void fillUsuario(String respuesta) {
        ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for(int i = 0; i < jsonArreglo.length(); i++){
                Usuarios jugador = new Usuarios();
                jugador.setId_usuario(jsonArreglo.getJSONObject(i).getInt("id_usuario"));
                jugador.setUsuario(jsonArreglo.getJSONObject(i).getString("usuario"));
                jugador.setPassword(jsonArreglo.getJSONObject(i).getString("contraseña"));
                jugador.setCorreo(jsonArreglo.getJSONObject(i).getString("correo"));
                jugador.setNombre(jsonArreglo.getJSONObject(i).getString("nombre"));
                jugador.setApellidos(jsonArreglo.getJSONObject(i).getString("apellidos"));
                jugador.setEdad(jsonArreglo.getJSONObject(i).getInt("edad"));
                lista.add(jugador);
            }
            usuarioT.setText(lista.get(0).getUsuario());
            passwordT.setText(lista.get(0).getPassword());
            correoT.setText(lista.get(0).getCorreo());
            nombreT.setText(lista.get(0).getNombre());
            apellidosT.setText(lista.get(0).getApellidos());
            edadT.setText(Integer.toString(lista.get(0).getEdad()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Usuarios getJugadorActual() {
        return jugadorActual;
    }

    public void setJugadorActual(Usuarios jugadorActual) {
        this.jugadorActual = jugadorActual;
    }

    private void getDataJugador(String nombre) {
        /*String url ="http://horrography.000webhostapp.com/obtener_id_usuario.php?"; */
        String url ="http://horrografia.ucr.ac.cr/obtener_id_usuario.php?";
        String tipo = "jugador";
        String parametros = "nombre="+nombre+"&tipo="+tipo;
        StringRequest stringRequest = new StringRequest(url + parametros,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        fillJugador(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue cola = Volley.newRequestQueue(ActualizarUsuario.this);
        cola.add(stringRequest);
    }

    private void fillJugador(String respuesta) {
        ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for(int i = 0; i < jsonArreglo.length(); i++){
                Usuarios jugador = new Usuarios();
                jugador.setId_usuario(jsonArreglo.getJSONObject(i).getInt("id_usuario"));
                jugador.setUsuario(jsonArreglo.getJSONObject(i).getString("usuario"));
                jugador.setPassword(jsonArreglo.getJSONObject(i).getString("contraseña"));
                jugador.setCorreo(jsonArreglo.getJSONObject(i).getString("correo"));
                jugador.setNombre(jsonArreglo.getJSONObject(i).getString("nombre"));
                jugador.setApellidos(jsonArreglo.getJSONObject(i).getString("apellidos"));
                jugador.setEdad(jsonArreglo.getJSONObject(i).getInt("edad"));
                lista.add(jugador);
            }
            setJugadorActual(lista.get(0));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
