package com.example.app;

import android.app.AlertDialog;
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

public class Nivel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel);
        final EditText descripcionT = (EditText)findViewById(R.id.descripcionNivel);

        Button btnRegistroNivel = (Button)findViewById(R.id.btnRegistroNivel);
        btnRegistroNivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descripcion = descripcionT.getText().toString();

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");
                            if(ok == true){
                                Intent i = new Intent(Nivel.this, AdministradorHome.class);
                                Nivel.this.startActivity(i);
                                Nivel.this.finish();
                            }
                            else{
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Nivel.this);
                                alerta.setMessage("Fallo en crear nivel").setNegativeButton("Reintentar",null).create().show();
                            }
                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };
                NivelRequest r = new NivelRequest(descripcion, respuesta);
                RequestQueue cola = Volley.newRequestQueue(Nivel.this);
                cola.add(r);
            }
        });
    }
}
