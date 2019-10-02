package com.example.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class Top extends AppCompatActivity {

    private CircleButton btnhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        Intent i = this.getIntent();
        final String nombre = i.getStringExtra("nombre");

        TableLayout table = findViewById(R.id.tabla);


        getData(table);

        btnhome = (CircleButton)findViewById(R.id.btnHome);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(getApplicationContext(),Home.class);
                intentHome.putExtra("nombre",nombre);
                Top.this.startActivity(intentHome);
                Top.this.finish();
            }
        });
    }



    private void getData(final TableLayout table) {
        StringRequest stringRequest = new StringRequest("http://horrography.000webhostapp.com/obtenerTop.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cargarTop(response, table);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue cola = Volley.newRequestQueue(Top.this);
        cola.add(stringRequest);

    }

    private  void cargarTop(String respuesta, TableLayout table){

        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for(int i = 0; i < jsonArreglo.length(); i++){
                Partidas p = new Partidas();
                p.setUsuario(jsonArreglo.getJSONObject(i).getString("usuario"));
                p.setNivel(jsonArreglo.getJSONObject(i).getInt("nivel"));
                p.setPuntuacion(jsonArreglo.getJSONObject(i).getDouble("puntuacion"));
                p.setErrores(jsonArreglo.getJSONObject(i).getInt("errores"));


                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));

                TextView columnUsuario = new TextView(this);
                //columnUsuario.setText("Daniela");
                columnUsuario.setText(p.getUsuario());
                columnUsuario.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                columnUsuario.setGravity(Gravity.CENTER);
                columnUsuario.setBackgroundResource(R.color.white);

                TextView columnNivel = new TextView(this);
                //columnNivel.setText("1");
                columnNivel.setText(Integer.toString(p.getNivel()));
                columnNivel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
                columnNivel.setGravity(Gravity.CENTER);
                columnNivel.setBackgroundResource(R.color.white);

                TextView columnPuntuacion = new TextView(this);
                //columnPuntuacion.setText("1250");
                columnPuntuacion.setText(Double.toString(p.getPuntuacion()));
                columnPuntuacion.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
                columnPuntuacion.setGravity(Gravity.CENTER);
                columnPuntuacion.setBackgroundResource(R.color.white);

                TextView columnErrores = new TextView(this);
                //columnErrores.setText("0");
                columnErrores.setText(Integer.toString(p.getErrores()));
                columnErrores.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
                columnErrores.setGravity(Gravity.CENTER);
                columnErrores.setBackgroundResource(R.color.white);

                row.addView(columnUsuario);
                row.addView(columnNivel);
                row.addView(columnPuntuacion);
                row.addView(columnErrores);

                table.addView(row);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
