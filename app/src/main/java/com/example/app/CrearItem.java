package com.example.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;
import cz.msebera.android.httpclient.Header;

public class CrearItem extends AppCompatActivity {

    private Spinner spinnerNivel;
    private  Spinner spinnerEtapa;
    private CircleButton btnhome;

    String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_item);

        Intent i = this.getIntent();
        nombre = i.getStringExtra("nombre");

        final EditText texto_itemT = (EditText)findViewById(R.id.texto_item);
        final EditText forma_correctaT = (EditText)findViewById(R.id.forma_correcta);

        final EditText pistaT = (EditText)findViewById(R.id.pista);

        spinnerNivel = (Spinner)findViewById(R.id.spNivel);
        fillSpinnerNivel();
        spinnerEtapa = (Spinner)findViewById(R.id.spEtapa);
        fillSpinnerEtapa();
        Button btnCrearItem = (Button)findViewById(R.id.btnCrearItem);


        btnCrearItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto_item = texto_itemT.getText().toString();
                String forma_correcta = forma_correctaT.getText().toString();
                String pista = pistaT.getText().toString();
                int nivel = Integer.parseInt(spinnerNivel.getSelectedItem().toString());
                int etapa = Integer.parseInt(spinnerEtapa.getSelectedItem().toString());

                Items items = new Items();

                items.setTexto_item(texto_item);
                items.setForma_correcta(forma_correcta);
                items.setPista(pista);
                items.setNivel(nivel);
                items.setEtapa(etapa);

                addItem(items);
            }
        });


        btnhome = (CircleButton)findViewById(R.id.btnHome);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent administradorhome = new Intent(getApplicationContext(),AdministradorHome.class);
                administradorhome.putExtra("nombre",nombre);
                CrearItem.this.startActivity(administradorhome);
                CrearItem.this.finish();
            }
        });

    }

    private void addItem(Items i) {
        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonRespuesta = new JSONObject(response);
                    boolean ok = jsonRespuesta.getBoolean("success");
                    if(ok==true){
                        Toast.makeText(CrearItem.this,"Item agregado correctamente",Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(CrearItem.this, AdministradorHome.class);
                        i.putExtra("nombre",nombre);
                        CrearItem.this.startActivity(i);
                        CrearItem.this.finish();
                    }else{
                        android.app.AlertDialog.Builder alerta = new AlertDialog.Builder(CrearItem.this);
                        alerta.setMessage("Fallo en crear item").setNegativeButton("Reintentar",null).create().show();
                    }
                }catch (JSONException e){
                    e.getMessage();
                }
            }
        };
        ItemRequest r = new ItemRequest(i.getTexto_item(),i.getForma_correcta(),i.getPista(),i.getEtapa(),i.getNivel(),respuesta);
        RequestQueue cola = Volley.newRequestQueue(CrearItem.this);
        cola.add(r);
    }


    private void fillSpinnerEtapa() {
        String url = "http://horrography.000webhostapp.com/obtenerEtapas.php";
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cargarSpinnerEtapa(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue cola = Volley.newRequestQueue(CrearItem.this);
        cola.add(stringRequest);
    }



    private void cargarSpinnerEtapa(String respuesta) {
        ArrayList<Etapas> lista = new ArrayList<Etapas>();
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for(int i = 0; i < jsonArreglo.length(); i++){
                Etapas e = new Etapas();
                e.setId(jsonArreglo.getJSONObject(i).getInt("id_etapa"));
                lista.add(e);
            }
            ArrayAdapter<Etapas> a = new ArrayAdapter<Etapas>(this,android.R.layout.simple_dropdown_item_1line, lista);
            spinnerEtapa.setAdapter(a);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void fillSpinnerNivel() {
        String url = "http://horrography.000webhostapp.com/obtenerNiveles.php";
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cargarSpinnerNivel(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue cola = Volley.newRequestQueue(CrearItem.this);
        cola.add(stringRequest);
    }


    private void cargarSpinnerNivel(String respuesta) {
        ArrayList<Niveles> lista = new ArrayList<Niveles>();
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for(int i = 0; i < jsonArreglo.length(); i++){
                Niveles n = new Niveles();
                n.setId(jsonArreglo.getJSONObject(i).getInt("id_nivel"));
                lista.add(n);
            }
            ArrayAdapter<Niveles> a = new ArrayAdapter<Niveles>(this,android.R.layout.simple_dropdown_item_1line, lista);
            spinnerNivel.setAdapter(a);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
