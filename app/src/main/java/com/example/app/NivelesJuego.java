package com.example.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import at.markushi.ui.CircleButton;

public class NivelesJuego extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveles_juego);

        final Button btnHome = (Button)findViewById(R.id.btnHome);
        final Button btnNivel1 = (Button)findViewById(R.id.btnNivel1);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(NivelesJuego.this,Home.class);
                NivelesJuego.this.startActivity(intentHome);
                NivelesJuego.this.finish();
            }
        });


    }
}
