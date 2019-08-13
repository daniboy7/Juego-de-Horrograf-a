package com.example.app;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView registro = (TextView)findViewById(R.id.registroLogin);
        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        final EditText usuarioT = (EditText)findViewById(R.id.usuarioLogin);
        final EditText contraseñaT = (EditText)findViewById(R.id.passwordLogin);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(MainActivity.this,Registro.class);
                MainActivity.this.startActivity(registro);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usuario = usuarioT.getText().toString();
                final String contraseña = contraseñaT.getText().toString();
                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");
                            if (ok == true) {
                                String nombre = jsonRespuesta.getString("nombre");
                                int edad = jsonRespuesta.getInt("edad");
                                String tipo = jsonRespuesta.getString("tipo");

                                if (tipo.equals("jugador")){
                                    Intent bienvenido = new Intent(MainActivity.this, Home.class);
                                    bienvenido.putExtra("nombre", nombre);
                                    bienvenido.putExtra("edad", edad);

                                    MainActivity.this.startActivity(bienvenido);
                                    MainActivity.this.finish();
                                }

                                else if(tipo.equals("administrador")){
                                    Intent admin = new Intent(MainActivity.this, AdministradorHome.class);
                                    admin.putExtra("nombre",nombre);
                                    admin.putExtra("edad",edad);

                                    MainActivity.this.startActivity(admin);
                                    MainActivity.this.finish();
                                }

                            } else {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                                alerta.setMessage("Fallo en el Login").setNegativeButton("Reintentar", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.getMessage();
                        }
                    }
                };
                    LoginRequest r = new LoginRequest(usuario, contraseña, respuesta);
                    RequestQueue cola = Volley.newRequestQueue(MainActivity.this);
                    cola.add(r);
                }
        });
    }
}
