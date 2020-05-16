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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class EstadisticasAciertos extends AppCompatActivity {
    private BarChart barChart;

    private CircleButton btnHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas_aciertos);

        Intent i = this.getIntent();
        final String nombre = i.getStringExtra("nombre");

        btnHome = (CircleButton)findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AdministradorHome.class);
                intent.putExtra("nombre",nombre);
                EstadisticasAciertos.this.startActivity(intent);
                EstadisticasAciertos.this.finish();
            }
        });

        barChart = (BarChart)findViewById(R.id.graficoBarras);

        crearGraficoBarras();
    }

    private void crearGraficoBarras()
    {
        //String url ="http://horrography.000webhostapp.com/obtenerEstadisticasAciertos.php";
        String url ="http://horrografia.ucr.ac.cr/obtenerEstadisticasAciertos.php";
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
        RequestQueue cola = Volley.newRequestQueue(EstadisticasAciertos.this);
        cola.add(stringRequest);
    }

    private void getInfo(String respuesta)
    {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);

            for(int i = 0; i < jsonArreglo.length(); i++){
                Estadisticas est = new Estadisticas();
                est.setTotal_aciertos(jsonArreglo.getJSONObject(i).getInt("total_aciertos"));
                est.setDescripcion_nivel(jsonArreglo.getJSONObject(i).getString("descripcion_nivel"));
                barEntries.add(new BarEntry(i,est.getTotal_aciertos()));
                labels.add(est.getDescripcion_nivel());
            }

            BarDataSet barDataSet = new BarDataSet(barEntries, "Niveles");
            barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            barDataSet.setValueTextSize(12);

            BarData barData = new BarData(barDataSet);
            barData.setBarWidth(0.9f);

            barChart.animateY(5000);
            barChart.setData(barData);

            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.setGranularity(1);
            xAxis.setGranularityEnabled(true);


            barChart.setFitBars(true);

            Description description = new Description();
            description.setText("Porcentaje de aciertos");
            barChart.setDescription(description);


            barChart.invalidate();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
