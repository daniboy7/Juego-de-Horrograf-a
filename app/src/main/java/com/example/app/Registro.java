package com.example.app;

import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        final EditText usuarioT = (EditText)findViewById(R.id.usuarioRegistro);
        final EditText contraseñaT = (EditText)findViewById(R.id.passwordRegistro);
        final EditText correoT = (EditText)findViewById(R.id.correoRegistro);
        final EditText nombreT = (EditText)findViewById(R.id.nombreRegistro);
        final EditText apellidosT = (EditText)findViewById(R.id.apellidosRegistro);
        final EditText edadT = (EditText)findViewById(R.id.edadRegistro);
        Button btnRegistro = (Button)findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = usuarioT.getText().toString();
                String contraseña = contraseñaT.getText().toString();
                String correo = correoT.getText().toString();
                String nombre = nombreT.getText().toString();
                String apellidos = apellidosT.getText().toString();
                int edad = Integer.parseInt(edadT.getText().toString());
                String tipo = "jugador";

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       try{
                           JSONObject jsonRespuesta = new JSONObject(response);
                           boolean ok = jsonRespuesta.getBoolean("success");
                           if(ok == true){
                               Intent i = new Intent(Registro.this, MainActivity.class);
                               Registro.this.startActivity(i);
                               Registro.this.finish();
                           }else{
                               AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                               alerta.setMessage("Fallo en el Registro").setNegativeButton("Reintentar",null).create().show();
                           }
                       }catch (JSONException e){
                           e.getMessage();
                       }
                    }
                };

                RegistroRequest r = new RegistroRequest(usuario,contraseña,correo,nombre,apellidos,edad,tipo,respuesta);
                RequestQueue cola = Volley.newRequestQueue(Registro.this);
                cola.add(r);
            }
        });
    }
}
