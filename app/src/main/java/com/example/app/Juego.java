package com.example.app;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class Juego extends AppCompatActivity {
    private AsyncHttpClient cliente;

    TextView textViewItemCount;
    TextView textViewScore;
    TextView txtNivel;
    TextView txtEtapa;
    TextView textViewErrores;
    TextView textViewCountDown;
    Button buttonConfirmNext;


    ArrayList<Item> listQuestions;
    int itemCounter;
    int itemCountTotal;
    Item currentItem;

    TextView item;

    EditText editRespuesta;

    Double score;
    int errores;

    boolean answered;

    final long COUNTDOWN_IN_MILLIS = 1000;//
    CountDownTimer countDownTimer; //
    long timeLeftInMillis; //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        cliente = new AsyncHttpClient();

        item = (TextView)findViewById(R.id.textoItem);

        textViewItemCount = (TextView)findViewById(R.id.itemCount);

        txtNivel = (TextView)findViewById(R.id.textviewNivel);
        txtEtapa = (TextView)findViewById(R.id.textviewEtapa);
        textViewScore = (TextView)findViewById(R.id.puntuacion);
        textViewErrores = (TextView)findViewById(R.id.errores);
        textViewCountDown = (TextView)findViewById(R.id.tiempo);

        buttonConfirmNext = (Button)findViewById(R.id.btnConfirmNext);
        editRespuesta = (EditText)findViewById(R.id.textoRespuesta);


        final int etapa = 1;
        final int nivel = 1;

        getQuestion(etapa,nivel);

        setItemCounter(0);
        setScore(0.0);
        setErrores(0);
        setAnswered(true);

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(itemCounter < itemCountTotal){
                    setCurrentItem(listQuestions.get(itemCounter));

                    item.setText(getCurrentItem().getFrase());
                    txtNivel.setText(getCurrentItem().getDescripcion_nivel());
                    txtEtapa.setText(getCurrentItem().getDescripcion_etapa());

                    setItemCounter(getItemCounter() + 1);
                }else{
                    finish();
                }*/
                if(!isAnswered()){
                    String resp = editRespuesta.getText().toString();
                    if(resp.length() != 0){
                        checkAnswer();
                    }else{
                        Toast.makeText(Juego.this,"Por favor ingrese una respuesta",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showNextItem();
                }
            }
        });


    }

    private void showNextItem() {
        editRespuesta.setTextColor(Color.BLACK);
        editRespuesta.setText("");
        if(itemCounter < itemCountTotal ){
            setCurrentItem(listQuestions.get(itemCounter));

            item.setText(getCurrentItem().getFrase());
            txtNivel.setText(getCurrentItem().getDescripcion_nivel());
            txtEtapa.setText(getCurrentItem().getDescripcion_etapa());

            setItemCounter(getItemCounter() + 1);
            textViewItemCount.setText("Item : "+itemCounter+"/"+itemCountTotal);

            setAnswered(false);
            buttonConfirmNext.setText("Confirmar");

            //timeLeftInMillis = COUNTDOWN_IN_MILLIS * (getCurrentItem().getMinutos()*60);//
            timeLeftInMillis = COUNTDOWN_IN_MILLIS * 30;
            startCountDown();//
        }else{
            finish();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished ;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int)(timeLeftInMillis / 1000) / 60;
        int seconds = (int)(timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d",minutes,seconds);
        textViewCountDown.setText(timeFormatted);

        if(timeLeftInMillis < 10000){
            textViewCountDown.setTextColor(Color.RED);
        }else {
            textViewCountDown.setTextColor(Color.WHITE);
        }
    }

    private void checkAnswer(){
        setAnswered(true);

        countDownTimer.cancel();//

        String answer = editRespuesta.getText().toString();
        if (answer.equalsIgnoreCase(getCurrentItem().getFrase_correcta())){
            setScore(getScore() + getCurrentItem().getValor_puntuacion());
            textViewScore.setText(Double.toString(getScore()));
        }
        else if(!(answer.equalsIgnoreCase(getCurrentItem().getFrase_correcta()))){
            setScore(getScore() - getCurrentItem().getValor_error());
            textViewScore.setText(Double.toString(getScore()));

            setErrores(getErrores()+ 1);
            textViewErrores.setText(Integer.toString(getErrores()));
        }
        showSolution();
    }

    private void showSolution() {
        editRespuesta.setTextColor(Color.GREEN);
        editRespuesta.setText("La respuesta correcta es "+getCurrentItem().getFrase_correcta());
        if(itemCounter < itemCountTotal){
            buttonConfirmNext.setText("Continuar");
        }else{
            buttonConfirmNext.setText("Terminar");
        }
    }

    public int getErrores() {
        return errores;
    }

    public void setErrores(int errores) {
        this.errores = errores;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public void setItemCounter(int itemCounter) {
        this.itemCounter = itemCounter;
    }

    public int getItemCounter() {
        return itemCounter;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public void setListQuestions(ArrayList<Item> listQuestions) {
        this.listQuestions = listQuestions;
    }

    public ArrayList<Item> getListQuestions() {
        return listQuestions;
    }

    public void setItemCountTotal(int itemCountTotal) {
        this.itemCountTotal = itemCountTotal;
    }

    public int getItemCountTotal() {
        return itemCountTotal;
    }

    private void getQuestion(int etapa, int nivel) {
        String url ="http://horrografia.000webhostapp.com/obtenerItemsporEtapaNivel.php?";
        String parametros = "etapa="+etapa+"&nivel="+nivel;
        cliente.post(url + parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    fillQuestion(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void fillQuestion(String respuesta) {
        ArrayList<Item> lista = new ArrayList<Item>();
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for(int i = 0; i < jsonArreglo.length(); i++){
                Item question = new Item();
                question.setId(jsonArreglo.getJSONObject(i).getInt("id_item"));
                question.setFrase(jsonArreglo.getJSONObject(i).getString("frase"));
                question.setFrase_correcta(jsonArreglo.getJSONObject(i).getString("frase_correcta"));
                question.setNivel(jsonArreglo.getJSONObject(i).getInt("id_nivel"));
                question.setDescripcion_nivel(jsonArreglo.getJSONObject(i).getString("descripcion_nivel"));
                question.setEtapa(jsonArreglo.getJSONObject(i).getInt("id_etapa"));
                question.setDescripcion_etapa(jsonArreglo.getJSONObject(i).getString("descripcion_etapa"));
                question.setValor_error(jsonArreglo.getJSONObject(i).getDouble("valor_error"));
                question.setValor_puntuacion(jsonArreglo.getJSONObject(i).getDouble("valor_puntuacion"));
                question.setErrores_no_permitidos(jsonArreglo.getJSONObject(i).getInt("errores_no_permitidos"));
                question.setMinutos(jsonArreglo.getJSONObject(i).getInt("minutos"));
                lista.add(question);
            }
            Collections.shuffle(lista);
            setListQuestions(lista);
            setItemCountTotal(lista.size());
            /*Item actual = lista.get(2);
            String texto = actual.getId()+" "+actual.getFrase()+" "+actual.getFrase_correcta()+" "+actual.getValor_error()
                    +" "+actual.getValor_puntuacion()+" "+actual.getErrores_no_permitidos()+" "+actual.getMinutos();
            item.setText(texto);*/


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }
}
