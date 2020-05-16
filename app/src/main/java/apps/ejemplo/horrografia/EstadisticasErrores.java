package apps.ejemplo.horrografia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;


public class EstadisticasErrores extends AppCompatActivity {


    PieChart pieChart;

    private CircleButton btnhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas_errores);

        Intent i = this.getIntent();
        final String nombre = i.getStringExtra("nombre");

        btnhome = (CircleButton)findViewById(R.id.btnHome);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AdministradorHome.class);
                intent.putExtra("nombre",nombre);
                EstadisticasErrores.this.startActivity(intent);
                EstadisticasErrores.this.finish();
            }
        });




        pieChart = (PieChart)findViewById(R.id.graficoPastel);

        pieChart.setUsePercentValues(true);

        crearGraficoPastel();


    }


    private void crearGraficoPastel()
    {
        //String url ="http://horrography.000webhostapp.com/obtenerEstadisticasErrores.php";
        String url ="http://horrografia.ucr.ac.cr/obtenerEstadisticasErrores.php";
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getInfo(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue cola = Volley.newRequestQueue(EstadisticasErrores.this);
        cola.add(stringRequest);
    }

    private void getInfo(String respuesta) {
        List<PieEntry> pieEntries = new ArrayList<>();
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for(int i = 0; i < jsonArreglo.length(); i++){
                Estadisticas est = new Estadisticas();
                est.setTotal_errores(jsonArreglo.getJSONObject(i).getInt("total_errores"));
                est.setDescripcion_nivel(jsonArreglo.getJSONObject(i).getString("descripcion_nivel"));
                pieEntries.add(new PieEntry(est.getTotal_errores(),est.getDescripcion_nivel()));
            }
            pieChart.animateXY(5000,5000);

            pieChart.setHoleRadius(25f);
            pieChart.setTransparentCircleRadius(25f);

            PieDataSet pieDataSet = new PieDataSet(pieEntries,"Porcentaje de errores");
            pieDataSet.setValueTextSize(12);//
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

            PieData pieData = new PieData(pieDataSet);

            pieData.setValueFormatter(new PercentFormatter());//

            pieChart.setData(pieData);

            Description description = new Description();
            description.setText("niveles");
            pieChart.setDescription(description);


            pieChart.setRotationEnabled(true);

            pieChart.invalidate();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
