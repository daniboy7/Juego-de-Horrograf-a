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

public class Nivel extends AppCompatActivity {
    private CircleButton btnhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel);

        Intent i = this.getIntent();
        final String nombre = i.getStringExtra("nombre");

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
                                Toast.makeText(Nivel.this,"Nivel agregado correctamente",Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(Nivel.this, AdministradorHome.class);
                                i.putExtra("nombre",nombre);
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

        btnhome = (CircleButton)findViewById(R.id.btnHome);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent administradorhome = new Intent(getApplicationContext(),AdministradorHome.class);
                administradorhome.putExtra("nombre",nombre);
                Nivel.this.startActivity(administradorhome);
                Nivel.this.finish();
            }
        });
    }
}
