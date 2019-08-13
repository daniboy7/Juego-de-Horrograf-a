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

public class Etapa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapa);

        final EditText descripcionT = (EditText)findViewById(R.id.descripcionEtapa);
        final EditText valor_errorT = (EditText)findViewById(R.id.valorError);
        final EditText valor_puntuacionT = (EditText)findViewById(R.id.valorPuntuacion);
        final EditText errores_no_permitidosT = (EditText)findViewById(R.id.erroresNoPermitidos);
        final EditText minutosT = (EditText)findViewById(R.id.minutos);
        Button btnRegistroEtapa = (Button)findViewById(R.id.btnRegistroEtapa);

        btnRegistroEtapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descripcion = descripcionT.getText().toString();
                Double valor_error = Double.parseDouble(valor_errorT.getText().toString());
                Double valor_puntuacion = Double.parseDouble(valor_puntuacionT.getText().toString());
                int errores_no_permitidos = Integer.parseInt(errores_no_permitidosT.getText().toString());
                int minutos = Integer.parseInt(minutosT.getText().toString());

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");
                            if(ok == true){
                                Intent i = new Intent(Etapa.this, AdministradorHome.class);
                                Etapa.this.startActivity(i);
                                Etapa.this.finish();
                            }else{
                                android.app.AlertDialog.Builder alerta = new AlertDialog.Builder(Etapa.this);
                                alerta.setMessage("Fallo en crear etapa").setNegativeButton("Reintentar",null).create().show();
                            }
                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };
                EtapaRequest r = new EtapaRequest(descripcion,valor_error,valor_puntuacion,errores_no_permitidos,minutos,respuesta);
                RequestQueue cola = Volley.newRequestQueue(Etapa.this);
                cola.add(r);
            }
        });
    }
}
