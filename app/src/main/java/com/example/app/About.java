package com.example.app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tomer.fadingtextview.FadingTextView;

import java.util.concurrent.TimeUnit;

import at.markushi.ui.CircleButton;

public class About extends AppCompatActivity {
    private FadingTextView fadingTextView;

    private CircleButton btnLogin;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mp = MediaPlayer.create(this, R.raw.smiley_island);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f,0.5f);
        mp.start();

        fadingTextView = findViewById(R.id.fading_text_view);

        fadingText();

        btnLogin = (CircleButton)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.release();

                Intent intentLogin = new Intent(About.this,MainActivity.class);
                About.this.startActivity(intentLogin);
                About.this.finish();
            }
        });

    }

    public void fadingText()
    {
        String[] texto = {"Horrografía fue desarrollado por Argenes Daniel Montoya Aguilar, \n " +
                "Estudiante de la carrera de Ingeniería en Computación \ndel Instituto Tecnológico de Costa Rica",
                "junto con el Instituto de Investigaciones Linguísticas (INIL) de la UCR ",
                "y la Unidad de Gestión y Transferencia del Conocimiento\n para la Innovación (PROINNOVA) de la " +
                        "\nVicerrectoría de Investigación de la UCR",
                "Efectos de sonido obtenidos de \nhttps://www.zapsplat.com",
        "Música obtenida de\nhttps://www.playonloop.com","Imagenes obtenidas de\nhttps://www.freepik.com "};
        fadingTextView.setTexts(texto);
        fadingTextView.setTimeout(4000, TimeUnit.MILLISECONDS);
    }
}
