package com.example.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final TextView mensaje = (TextView)findViewById(R.id.mensaje);
        Intent i = this.getIntent();
        final String nombre = i.getStringExtra("nombre");
        int edad = i.getIntExtra("edad",-1);
        mensaje.setText(mensaje.getText()+" "+nombre);

        Button salir = findViewById(R.id.btnSalir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSalir = new Intent(Home.this,MainActivity.class);
                Home.this.startActivity(intentSalir);
                Home.this.finish();
            }
        });

        Button jugar = findViewById(R.id.btnJugar);
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentJugar = new Intent(Home.this,IniciarPartida.class);
                intentJugar.putExtra("nombre",nombre);
                Home.this.startActivity(intentJugar);
                Home.this.finish();
            }
        });

        Button top = findViewById(R.id.btnTop);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTop = new Intent(Home.this,Top.class);
                intentTop.putExtra("nombre",nombre);
                Home.this.startActivity(intentTop);
                Home.this.finish();
            }
        });

        Button actualizar = findViewById(R.id.btnActualizar);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentActualizar = new Intent(Home.this,ActualizarUsuario.class);
                intentActualizar.putExtra("nombre",nombre);
                Home.this.startActivity(intentActualizar);
                Home.this.finish();
            }
        });

    }
}
