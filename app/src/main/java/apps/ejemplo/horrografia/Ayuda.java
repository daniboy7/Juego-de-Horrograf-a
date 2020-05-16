package apps.ejemplo.horrografia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.markushi.ui.CircleButton;

public class Ayuda extends AppCompatActivity {


    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    private CircleButton btnHome;
    String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        nombre = getIntent().getStringExtra("nombre");

        btnHome = (CircleButton)findViewById(R.id.btnHome);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(getApplicationContext(),Home.class);
                intentHome.putExtra("nombre",nombre);
                Ayuda.this.startActivity(intentHome);
                Ayuda.this.finish();
            }
        });

        listView = (ExpandableListView)findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Jugar");
        listDataHeader.add("Top");
        listDataHeader.add("Actualizar datos personales");

        List<String> jugar = new ArrayList<>();
        jugar.add("Presionar el botón JUGAR del menú");
        jugar.add("Seleccionar un nivel y presionar el botón INICIAR PARTIDA");
        jugar.add("Seguidamente presionar el botón INICIAR");

        List<String> top = new ArrayList<>();
        top.add("Presionar el botón TOP del menú");

        List<String> actualizar = new ArrayList<>();
        actualizar.add("Seleccionar el botón ACTUALIZAR DATOS PERSONALES del menú");
        actualizar.add("Ingresar los datos solicitados");
        actualizar.add("Presionar el botón ACTUALIZAR");

        listHash.put(listDataHeader.get(0),jugar);
        listHash.put(listDataHeader.get(1),top);
        listHash.put(listDataHeader.get(2),actualizar);

    }
}
