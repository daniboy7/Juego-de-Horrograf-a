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
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CrearItem extends AppCompatActivity {
    private  AsyncHttpClient cliente;
    private Spinner spinnerNivel;
    private  Spinner spinnerEtapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_item);

        cliente = new AsyncHttpClient();

        final EditText fraseT = (EditText)findViewById(R.id.frase);
        final EditText fraseCorrectaT = (EditText)findViewById(R.id.fraseCorrecta);
        spinnerNivel = (Spinner)findViewById(R.id.spNivel);
        llenarSpinnerNivel();
        spinnerEtapa = (Spinner)findViewById(R.id.spEtapa);
        llenarSpinnerEtapa();
        Button btnCrearItem = (Button)findViewById(R.id.btnCrearItem);


        btnCrearItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String frase = fraseT.getText().toString();
                String frase_correcta = fraseCorrectaT.getText().toString();
                int nivel = Integer.parseInt(spinnerNivel.getSelectedItem().toString());
                int etapa = Integer.parseInt(spinnerEtapa.getSelectedItem().toString());

                Items items = new Items();
                items.setFrase(frase);
                items.setFrase_correcta(frase_correcta);
                items.setEtapa(etapa);
                items.setNivel(nivel);

                agregarItem(items);
            }
        });

    }

    private void agregarItem(Items i){
        String url ="http://horrografia.000webhostapp.com/agregarItem.php?";
        String parametros = "frase="+i.getFrase()+"&frase_correcta="+i.getFrase_correcta()+"&etapa="+i.getEtapa()+"&nivel="+i.getNivel();
        cliente.post(url + parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    Toast.makeText(CrearItem.this,"Item agregado correctamente",Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(CrearItem.this, AdministradorHome.class);
                    CrearItem.this.startActivity(i);
                    CrearItem.this.finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private void llenarSpinnerEtapa() {
        String url = "http://horrografia.000webhostapp.com/obtenerEtapas.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    cargarSpinnerEtapa(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
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

    private void llenarSpinnerNivel(){
        String url = "http://horrografia.000webhostapp.com/obtenerNiveles.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    cargarSpinnerNivel(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
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
