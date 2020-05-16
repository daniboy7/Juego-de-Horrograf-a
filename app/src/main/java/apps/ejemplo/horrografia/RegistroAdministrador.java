package apps.ejemplo.horrografia;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import at.markushi.ui.CircleButton;

public class RegistroAdministrador extends AppCompatActivity {
    private CircleButton btnhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_administrador);

        Intent i = this.getIntent();
        final String nombre = i.getStringExtra("nombre");

        final EditText usuarioAdmin = (EditText)findViewById(R.id.usuarioAdmin);
        final EditText passwordAdmin = (EditText)findViewById(R.id.passwordAdmin);
        final EditText correoAdmin = (EditText)findViewById(R.id.correoAdmin);
        final EditText nombreAdmin = (EditText)findViewById(R.id.nombreAdmin);
        final EditText apellidosAdmin = (EditText)findViewById(R.id.apellidosAdmin);
        final EditText edadAdmin = (EditText)findViewById(R.id.edadAdmin);

        Button btnRegistroAdmin = (Button)findViewById(R.id.btnRegistroAdministrador);
        btnRegistroAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = usuarioAdmin.getText().toString();
                String contraseña = passwordAdmin.getText().toString();
                String correo = correoAdmin.getText().toString();
                final String nombre = nombreAdmin.getText().toString();
                String apellidos = apellidosAdmin.getText().toString();
                int edad = Integer.parseInt(edadAdmin.getText().toString());
                String tipo = "administrador";

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");
                            if(ok==true){
                                Toast.makeText(RegistroAdministrador.this,"Administrador agregado correctamente",Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(RegistroAdministrador.this,AdministradorHome.class);
                                i.putExtra("nombre",nombre);
                                RegistroAdministrador.this.startActivity(i);
                                RegistroAdministrador.this.finish();
                            }
                            else{
                                AlertDialog.Builder alerta = new AlertDialog.Builder(RegistroAdministrador.this);
                                alerta.setMessage("Fallo en el registro").setNegativeButton("Reintentar",null).create().show();
                            }
                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };
                RegistroAdministradorRequest r = new RegistroAdministradorRequest(usuario,contraseña,correo,nombre,apellidos,edad,tipo,respuesta);
                RequestQueue cola = Volley.newRequestQueue(RegistroAdministrador.this);
                cola.add(r);
            }
        });

        btnhome = (CircleButton)findViewById(R.id.btnHome);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent administradorhome = new Intent(getApplicationContext(),AdministradorHome.class);
                administradorhome.putExtra("nombre",nombre);
                RegistroAdministrador.this.startActivity(administradorhome);
                RegistroAdministrador.this.finish();
            }
        });
    }
}
