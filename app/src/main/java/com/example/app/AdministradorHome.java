package com.example.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdministradorHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_home);

        final TextView mensaje = (TextView)findViewById(R.id.mensaje);
        Intent i = this.getIntent();
        String nombre = i.getStringExtra("nombre");
        int edad = i.getIntExtra("edad",-1);
        mensaje.setText(mensaje.getText()+" "+nombre+ " Su edad:"+edad+"");

        Button nivel = findViewById(R.id.btnNivel);
        nivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNivel = new Intent(AdministradorHome.this,Nivel.class);
                AdministradorHome.this.startActivity(intentNivel);
                AdministradorHome.this.finish();
            }
        });

        Button salir = findViewById(R.id.btnSalir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSalir = new Intent(AdministradorHome.this,MainActivity.class);
                AdministradorHome.this.startActivity(intentSalir);
                AdministradorHome.this.finish();
            }
        });

        Button registroAdministrador = findViewById(R.id.btnRegistroAdministrador);
        registroAdministrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistro = new Intent(AdministradorHome.this,RegistroAdministrador.class);
                AdministradorHome.this.startActivity(intentRegistro);
                AdministradorHome.this.finish();
            }
        });

        Button etapa = findViewById(R.id.btnEtapa);
        etapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEtapa = new Intent(AdministradorHome.this,Etapa.class);
                AdministradorHome.this.startActivity(intentEtapa);
                AdministradorHome.this.finish();
            }
        });

        Button item = findViewById(R.id.btnItem);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentItem = new Intent(AdministradorHome.this, CrearItem.class);
                AdministradorHome.this.startActivity(intentItem);
                AdministradorHome.this.finish();
            }
        });



    }
}
