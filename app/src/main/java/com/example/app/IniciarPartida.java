package com.example.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;
import cz.msebera.android.httpclient.Header;

public class IniciarPartida extends AppCompatActivity {

    private Spinner spinnerNivelPartida;
    private CircleButton btnhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_partida);

        final TextView mensaje = (TextView)findViewById(R.id.mensaje);
        final String nombre = getIntent().getStringExtra("nombre");
        mensaje.setText(mensaje.getText()+" "+nombre);


        spinnerNivelPartida = (Spinner)findViewById(R.id.spNivelPartida);

        getData();

        Button btnIniciarPartida = (Button)findViewById(R.id.btnIniciarPartida);

        btnIniciarPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nivel = Integer.parseInt(spinnerNivelPartida.getSelectedItem().toString());

                switch (nivel){
                    case 1:
                        Toast.makeText(IniciarPartida.this,nivel+ " seleccionado",Toast.LENGTH_SHORT).show();
                        Intent intentPartida = new Intent(IniciarPartida.this,Partida.class);
                        intentPartida.putExtra("nivel",Integer.toString(nivel));
                        intentPartida.putExtra("nombre",nombre);
                        IniciarPartida.this.startActivity(intentPartida);
                        IniciarPartida.this.finish();
                        break;
                    case 2:
                        Toast.makeText(IniciarPartida.this,nivel+ " seleccionado",Toast.LENGTH_SHORT).show();
                        Intent intentnivel2 = new Intent(IniciarPartida.this,PartidaNivel2.class);
                        intentnivel2.putExtra("nivel",Integer.toString(nivel));
                        intentnivel2.putExtra("nombre",nombre);
                        IniciarPartida.this.startActivity(intentnivel2);
                        IniciarPartida.this.finish();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(),nivel +" seleccionado",Toast.LENGTH_SHORT).show();
                        Intent intentnivel3 = new Intent(IniciarPartida.this, PartidaNivel3.class);
                        intentnivel3.putExtra("nivel",Integer.toString(nivel));
                        intentnivel3.putExtra("nombre",nombre);
                        IniciarPartida.this.startActivity(intentnivel3);
                        IniciarPartida.this.finish();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(),nivel +" seleccionado",Toast.LENGTH_SHORT).show();
                        Intent intentnivel4 = new Intent(IniciarPartida.this,PartidaNivel4.class);
                        intentnivel4.putExtra("nivel",Integer.toString(nivel));
                        intentnivel4.putExtra("nombre",nombre);
                        IniciarPartida.this.startActivity(intentnivel4);
                        IniciarPartida.this.finish();
                        break;
                        default:
                            Intent intentdefault = new Intent(getApplicationContext(),PartidaDefecto.class);
                            intentdefault.putExtra("nivel",Integer.toString(nivel));
                            intentdefault.putExtra("nombre",nombre);
                            IniciarPartida.this.startActivity(intentdefault);
                            IniciarPartida.this.finish();
                            break;

                }

            }
        });

        btnhome = (CircleButton)findViewById(R.id.btnHome);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(getApplicationContext(),Home.class);
                intentHome.putExtra("nombre",nombre);
                IniciarPartida.this.startActivity(intentHome);
                IniciarPartida.this.finish();
            }
        });
    }

    private void getData() {
        StringRequest stringRequest = new StringRequest("http://horrography.000webhostapp.com/obtenerNiveles.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cargarSpinnerNivelPartida(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
        });
        RequestQueue cola = Volley.newRequestQueue(IniciarPartida.this);
        cola.add(stringRequest);
    }


    /*
    private void llenarSpinnerNivelPartida() {
        String url = "http://horrography.000webhostapp.com/obtenerNiveles.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    cargarSpinnerNivelPartida(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    */
    private void cargarSpinnerNivelPartida(String respuesta) {
        ArrayList<Niveles> lista = new ArrayList<Niveles>();
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for(int i = 0; i < jsonArreglo.length(); i++){
                Niveles n = new Niveles();
                n.setId(jsonArreglo.getJSONObject(i).getInt("id_nivel"));
                lista.add(n);
            }
            ArrayAdapter<Niveles> a = new ArrayAdapter<Niveles>(this,android.R.layout.simple_dropdown_item_1line, lista);
            spinnerNivelPartida.setAdapter(a);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
