package apps.ejemplo.horrografia;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameOver extends AppCompatActivity {
    TextView textviewPuntuacion;
    Button btnContinuar;

    MediaPlayer mp_sound_finished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        mp_sound_finished = MediaPlayer.create(this,R.raw.finished);
        mp_sound_finished.setVolume(0.5f,0.5f);
        mp_sound_finished.start();

        final String nombre = getIntent().getStringExtra("nombre");
        final String nivel = getIntent().getStringExtra("nivel");
        final String puntuacion = getIntent().getStringExtra("puntuacion");
        final String errores = getIntent().getStringExtra("errores");
        final String aciertos = getIntent().getStringExtra("aciertos");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String date = sdf.format(new Date());


        final String estado = "incompleto";

        textviewPuntuacion = (TextView)findViewById(R.id.textoPuntuacion);
        textviewPuntuacion.setText("Nombre : "+nombre+"\n Puntuación : "+puntuacion+"\n Errores : "+errores+ "\n Aciertos : "+aciertos
                +"\n Fecha : "+date+ "\n Nivel : "+nivel);

        btnContinuar = (Button) findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");
                            if(ok==true){
                                Toast.makeText(getApplicationContext(), "Partida guardada", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Fallo al guardar partida",Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };
                RegistroPartidaRequest r = new RegistroPartidaRequest(nombre,date,Integer.parseInt(nivel)
                        ,estado, Double.parseDouble(puntuacion),Integer.parseInt(errores),Integer.parseInt(aciertos),respuesta);
                RequestQueue cola = Volley.newRequestQueue(GameOver.this);
                cola.add(r);


                Intent i = new Intent(GameOver.this, IniciarPartida.class);
                i.putExtra("nombre",nombre);
                GameOver.this.startActivity(i);
                GameOver.this.finish();
            }
        });
    }


}
