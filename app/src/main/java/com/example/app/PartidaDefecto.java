package com.example.app;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class PartidaDefecto extends AppCompatActivity {
    RelativeLayout frame;

    private ImageView backgroundOne;
    private ImageView backgroundTwo;

    private ImageView boy;

    private ImageView snack1;
    private ImageView snack2;
    private ImageView snack3;
    private ImageView snack4;

    private int boyX;
    private int boyY;

    private int snack1X;
    private int snack2X;
    private int snack3X;
    private int snack4X;

    TextView textViewItemCount;
    TextView item;
    TextView txtTitulo;
    TextView textviewScore;
    TextView textviewErrores;
    TextView textViewCountDown;
    TextView textViewNivel;

    Button buttonConfirmNext;
    Button buttonClue;

    EditText respuesta;

    ArrayList<Item> listQuestions;
    int itemCounter;
    int itemCountTotal;
    Item currentItem;
    int lapCounter;
    int lapCountTotal;

    Double score;
    int errores;
    int aciertos;
    boolean answered;

    String nivel;
    String nombre;
    String erroresTablero;
    String aciertosTablero;
    String puntuacionTablero;

    private int frameHeight;
    private int frameWidth;
    private int boyHeightSize;
    private int boyWidthSize;

    AnimationDrawable running;

    private static final long COUNTDOWN_IN_MILLIS = 1000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_defecto);

        frame =(RelativeLayout)findViewById(R.id.bgPaisaje);

        boy = (ImageView)findViewById(R.id.boy);
        boy.setBackgroundResource(R.drawable.animationboy);
        running = (AnimationDrawable)boy.getBackground();
        running.start();

        boyY = 670;
        boyX = 10;
        boy.setY(boyY);
        boy.setX(boyX);

        snack1 = (ImageView)findViewById(R.id.snack1);
        snack1.setY(670);
        snack1X = 10 + 200;
        snack1.setX(snack1X);
        snack2 = (ImageView)findViewById(R.id.snack2);
        snack2.setY(670);
        snack2X = 210 + 200;
        snack2.setX(snack2X);
        snack3 = (ImageView)findViewById(R.id.snack3);
        snack3.setY(670);
        snack3X = 410 + 200;
        snack3.setX(snack3X);
        snack4 = (ImageView)findViewById(R.id.snack4);
        snack4.setY(670);
        snack4X = 610 + 200;
        snack4.setX(snack4X);

        backgroundOne = (ImageView) findViewById(R.id.background_one);
        backgroundTwo = (ImageView) findViewById(R.id.background_two);

        final ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                final float progress = (float)valueAnimator.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();

        nivel = getIntent().getStringExtra("nivel");
        final TextView titulo = (TextView)findViewById(R.id.textviewTitulo);

        nombre = getIntent().getStringExtra("nombre");

        textViewItemCount = (TextView)findViewById(R.id.itemCount);
        item = (TextView)findViewById(R.id.textoItem);
        txtTitulo = (TextView)findViewById(R.id.textviewTitulo);
        textviewScore =(TextView)findViewById(R.id.puntuacion);
        textviewErrores = (TextView)findViewById(R.id.errores);
        textViewCountDown = (TextView)findViewById(R.id.tiempo);
        textViewNivel = (TextView)findViewById(R.id.textviewNivel);

        respuesta = (EditText)findViewById(R.id.texto_respuesta);

        buttonClue = (Button) findViewById(R.id.clue);


        buttonConfirmNext = (Button)findViewById(R.id.btnConfirmNext);

        setLapCounter(1);
        setLapCountTotal(4);
        setItemCounter(0);

        getData(lapCounter,Integer.parseInt(nivel));

        setScore(0.0);
        setErrores(0);
        setAciertos(0);
        setAnswered(true);


        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAnswered()){
                    if(!(respuesta.getText().toString().isEmpty())){
                        checkAnswer();
                    }else{
                        Toast.makeText(PartidaDefecto.this,"Por favor ingrese una respuesta",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showNextItem();
                }
            }
        });

    }


    private void checkAnswer() {
        setAnswered(true);

        countDownTimer.cancel();

        String answer = respuesta.getText().toString().trim();
        if ( answer.equals(getCurrentItem().getForma_correcta()) ){
            setScore(getScore() + getCurrentItem().getValor_puntuacion());
            textviewScore.setText(Double.toString(getScore()));

            setAciertos(getAciertos() + 1);
        }
        else if( !(answer.equals(getCurrentItem().getForma_correcta()))){
            setScore(getScore() - getCurrentItem().getValor_error());
            textviewScore.setText(Double.toString(getScore()));

            setErrores(getErrores()+ 1);
            textviewErrores.setText(Integer.toString(getErrores()));
        }
        showSolution();
    }

    private void showSolution() {
        respuesta.setTextColor(Color.GREEN);
        respuesta.setText(getCurrentItem().getForma_correcta());

        if(itemCounter < itemCountTotal){
            buttonConfirmNext.setText("Continuar");
        }else{
            buttonConfirmNext.setText("Terminar");
        }
    }


    private void showNextItem() {
        respuesta.setTextColor(Color.BLACK);
        respuesta.setText("");

        if (lapCounter <= lapCountTotal){
            if(itemCounter < itemCountTotal){
                setCurrentItem(listQuestions.get(itemCounter));

                if (getErrores() < getCurrentItem().getErrores_no_permitidos()){
                    item.setText(getCurrentItem().getTexto_item());

                    txtTitulo.setText(getCurrentItem().getDescripcion_etapa());

                    textViewNivel.setText(getCurrentItem().getDescripcion_nivel());

                    buttonClue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(),getCurrentItem().getPista(),Toast.LENGTH_LONG).show();
                        }
                    });

                    setItemCounter(getItemCounter() + 1);
                    textViewItemCount.setText("Item : "+itemCounter+"/"+itemCountTotal);

                    setAnswered(false);
                    buttonConfirmNext.setText("Confirmar");

                    timeLeftInMillis = COUNTDOWN_IN_MILLIS * 30;
                    startCountDown();
                }
                else{
                    Intent gameover = new Intent(PartidaDefecto.this,GameOver.class);
                    gameover.putExtra("nombre",nombre);
                    puntuacionTablero = Double.toString(getScore());
                    erroresTablero = Integer.toString(getErrores());
                    aciertosTablero = Integer.toString(getAciertos());
                    gameover.putExtra("puntuacion",puntuacionTablero);
                    gameover.putExtra("errores",erroresTablero);
                    gameover.putExtra("aciertos",aciertosTablero);
                    gameover.putExtra("nivel",nivel);
                    PartidaDefecto.this.startActivity(gameover);
                    PartidaDefecto.this.finish();
                }

            }else{
                showNextLap();
            }
        }else{

            Intent tableroPuntuacion = new Intent(PartidaDefecto.this,Puntuacion.class);
            tableroPuntuacion.putExtra("nombre",nombre);
            puntuacionTablero = Double.toString(getScore());
            erroresTablero = Integer.toString(getErrores());
            aciertosTablero = Integer.toString(getAciertos());
            tableroPuntuacion.putExtra("puntuacion",puntuacionTablero);
            tableroPuntuacion.putExtra("errores",erroresTablero);
            tableroPuntuacion.putExtra("aciertos",aciertosTablero);
            tableroPuntuacion.putExtra("nivel",nivel);
            PartidaDefecto.this.startActivity(tableroPuntuacion);
            PartidaDefecto.this.finish();
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

    private void showNextLap(){
        setLapCounter(getLapCounter() + 1);
        setItemCounter(0);
        getData(lapCounter,Integer.parseInt(nivel));

        boyX +=200;
        checkboyPosition();
        boy.setX(boyX);
    }

    public ArrayList<Item> getListQuestions() {
        return listQuestions;
    }

    public void setListQuestions(ArrayList<Item> listQuestions) {
        this.listQuestions = listQuestions;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public int getErrores() {
        return errores;
    }

    public void setErrores(int errores) {
        this.errores = errores;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public int getItemCounter() {
        return itemCounter;
    }

    public void setItemCounter(int itemCounter) {
        this.itemCounter = itemCounter;
    }

    public int getItemCountTotal() {
        return itemCountTotal;
    }

    public void setItemCountTotal(int itemCountTotal) {
        this.itemCountTotal = itemCountTotal;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    public int getLapCounter() {
        return lapCounter;
    }

    public void setLapCounter(int lapCounter) {
        this.lapCounter = lapCounter;
    }

    public int getLapCountTotal() {
        return lapCountTotal;
    }

    public void setLapCountTotal(int lapCountTotal) {
        this.lapCountTotal = lapCountTotal;
    }

    public int getAciertos() {
        return aciertos;
    }

    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }

    private void getData(int etapa, int nivel) {
        String url ="http://horrography.000webhostapp.com/obtenerItemsporEtapaNivel.php?";
        String parametros = "etapa="+etapa+"&nivel="+nivel;
        StringRequest stringRequest = new StringRequest(url + parametros,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        fillQuestion(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue cola = Volley.newRequestQueue(PartidaDefecto.this);
        cola.add(stringRequest);
    }

    private void fillQuestion(String respuesta) {
        ArrayList<Item> lista = new ArrayList<Item>();
        try{
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for(int i = 0; i < jsonArreglo.length(); i++){
                Item question = new Item();
                question.setId(jsonArreglo.getJSONObject(i).getInt("id_item"));
                question.setTexto_item(jsonArreglo.getJSONObject(i).getString("texto_item"));
                question.setForma_correcta(jsonArreglo.getJSONObject(i).getString("forma_correcta"));
                question.setPista(jsonArreglo.getJSONObject(i).getString("pista"));
                question.setNivel(jsonArreglo.getJSONObject(i).getInt("id_nivel"));
                question.setDescripcion_nivel(jsonArreglo.getJSONObject(i).getString("descripcion_nivel"));
                question.setEtapa(jsonArreglo.getJSONObject(i).getInt("id_etapa"));
                question.setDescripcion_etapa(jsonArreglo.getJSONObject(i).getString("descripcion_etapa"));
                question.setValor_error(jsonArreglo.getJSONObject(i).getDouble("valor_error"));
                question.setValor_puntuacion(jsonArreglo.getJSONObject(i).getDouble("valor_puntuacion"));
                question.setErrores_no_permitidos(jsonArreglo.getJSONObject(i).getInt("errores_no_permitidos"));
                question.setMinutos(jsonArreglo.getJSONObject(i).getInt("minutos"));
                question.setNumero_items(jsonArreglo.getJSONObject(i).getInt("numero_items"));
                lista.add(question);
            }
            Collections.shuffle(lista);
            setListQuestions(lista);
            //setItemCountTotal(lista.size());
            setItemCountTotal(lista.get(0).getNumero_items());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkboyPosition() {
        frameHeight = frame.getHeight();
        frameWidth = frame.getWidth();
        boyHeightSize = boy.getHeight();
        boyWidthSize = boy.getWidth();

        if (boyX == snack1X)
            snack1.setVisibility(View.INVISIBLE);
        if (boyX == snack2X)
            snack2.setVisibility(View.INVISIBLE);
        if (boyX == snack3X)
            snack3.setVisibility(View.INVISIBLE);
        if (boyX == snack4X)
            snack4.setVisibility(View.INVISIBLE);
    }

}
