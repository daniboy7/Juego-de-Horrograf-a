package apps.ejemplo.horrografia;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.app.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class PartidaNivel2 extends AppCompatActivity {
    RelativeLayout frame;

    private ImageView backgroundOne;
    private ImageView backgroundTwo;

    private ImageView ape;

    private ImageView fruit1;
    private ImageView fruit2;
    private ImageView fruit3;
    private ImageView fruit4;

    //Position ape
    private int apeX;
    private int apeY;

    private int fruit1X;
    private int fruit2X;
    private int fruit3X;
    private int fruit4X;

    TextView textViewItemCount;
    TextView item;
    TextView txtTitulo;
    TextView textviewScore;
    TextView textviewErrores;
    TextView textViewCountDown;
    TextView textViewNivel;
    TextView textviewFormaCorrecta;

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
    private int apeHeightSize;
    private int apeWidthSize;

    AnimationDrawable walkingape;

    private static final long COUNTDOWN_IN_MILLIS = 1000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    MediaPlayer mp;
    MediaPlayer mp_sound_collect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_nivel2);

        mp = MediaPlayer.create(this, R.raw.follow_me);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f,0.5f);
        mp.start();

        frame = (RelativeLayout) findViewById(R.id.bgEscenario);

        ape = (ImageView)findViewById(R.id.ape);
        ape.setBackgroundResource(R.drawable.animationape);
        walkingape = (AnimationDrawable)ape.getBackground();
        walkingape.start();

        apeX = 20;
        apeY= 670;
        ape.setX(apeX);
        ape.setY(apeY);

        fruit1 = (ImageView)findViewById(R.id.fruit1);
        fruit1.setY(670);
        fruit1X = 20 + 200;
        fruit1.setX(fruit1X);
        fruit2 = (ImageView)findViewById(R.id.fruit2);
        fruit2.setY(670);
        fruit2X = 220 + 200;
        fruit2.setX(fruit2X);
        fruit3 = (ImageView)findViewById(R.id.fruit3);
        fruit3X = 420 + 200;
        fruit3.setY(670);
        fruit3.setX(fruit3X);
        fruit4 = (ImageView)findViewById(R.id.fruit4);
        fruit4.setY(670);
        fruit4X = 620 + 200;
        fruit4.setX(fruit4X);

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

        nombre = getIntent().getStringExtra("nombre");

        textViewItemCount = (TextView)findViewById(R.id.itemCount);
        item = (TextView)findViewById(R.id.textoItem);
        txtTitulo = (TextView)findViewById(R.id.textviewTitulo);
        textviewScore =(TextView)findViewById(R.id.puntuacion);
        textviewErrores = (TextView)findViewById(R.id.errores);
        textViewCountDown = (TextView)findViewById(R.id.tiempo);
        textViewNivel = (TextView)findViewById(R.id.textviewNivel);
        textviewFormaCorrecta = (TextView)findViewById(R.id.texto_forma_correcta);

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
                        Toast.makeText(PartidaNivel2.this,"Por favor seleccione una respuesta",Toast.LENGTH_SHORT).show();
                        YoYo.with(Techniques.RubberBand).duration(700).repeat(1).playOn(respuesta);
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

            respuesta.setTextColor(Color.GREEN);
        }
        else if( !(answer.equals(getCurrentItem().getForma_correcta()))){
            setScore(getScore() - getCurrentItem().getValor_error());
            textviewScore.setText(Double.toString(getScore()));

            setErrores(getErrores()+ 1);
            textviewErrores.setText(Integer.toString(getErrores()));

            respuesta.setTextColor(Color.RED);

            textviewFormaCorrecta.setTextColor(Color.GREEN);
            textviewFormaCorrecta.setText("La forma correcta es "+getCurrentItem().getForma_correcta());
        }
        showSolution();
    }

    private void showSolution() {
        //textviewFormaCorrecta.setTextColor(Color.GREEN);
        //textviewFormaCorrecta.setText("La forma correcta es "+getCurrentItem().getForma_correcta());

        if(itemCounter < itemCountTotal){
            buttonConfirmNext.setText("Continuar");
        }else{
            buttonConfirmNext.setText("Terminar");
        }
    }


    private void showNextItem() {
        respuesta.setTextColor(Color.BLACK);
        respuesta.setText("");
        textviewFormaCorrecta.setText("");

        if (lapCounter <= lapCountTotal){
            if(itemCounter < itemCountTotal){
                setCurrentItem(listQuestions.get(itemCounter));

                if (getErrores() < getCurrentItem().getErrores_no_permitidos()){
                    item.setText(getCurrentItem().getTexto_item());

                    YoYo.with(Techniques.Landing).duration(700).repeat(2).playOn(item);

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
                    mp.release();

                    Intent gameover = new Intent(PartidaNivel2.this,GameOver.class);
                    gameover.putExtra("nombre",nombre);
                    puntuacionTablero = Double.toString(getScore());
                    erroresTablero = Integer.toString(getErrores());
                    aciertosTablero = Integer.toString(getAciertos());
                    gameover.putExtra("puntuacion",puntuacionTablero);
                    gameover.putExtra("errores",erroresTablero);
                    gameover.putExtra("aciertos",aciertosTablero);
                    gameover.putExtra("nivel",nivel);
                    PartidaNivel2.this.startActivity(gameover);
                    PartidaNivel2.this.finish();
                }

            }else{
                showNextLap();
            }
        }else{
            mp.release();

            Intent tableroPuntuacion = new Intent(PartidaNivel2.this,Puntuacion.class);
            tableroPuntuacion.putExtra("nombre",nombre);
            puntuacionTablero = Double.toString(getScore());
            erroresTablero = Integer.toString(getErrores());
            aciertosTablero = Integer.toString(getAciertos());
            tableroPuntuacion.putExtra("puntuacion",puntuacionTablero);
            tableroPuntuacion.putExtra("errores",erroresTablero);
            tableroPuntuacion.putExtra("aciertos",aciertosTablero);
            tableroPuntuacion.putExtra("nivel",nivel);
            PartidaNivel2.this.startActivity(tableroPuntuacion);
            PartidaNivel2.this.finish();
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

        apeX +=200;
        checkApePosition();
        ape.setX(apeX);
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
        //String url ="http://horrography.000webhostapp.com/obtenerItemsporEtapaNivel.php?";
        String url ="http://horrografia.ucr.ac.cr/obtenerItemsporEtapaNivel.php?";
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
        RequestQueue cola = Volley.newRequestQueue(PartidaNivel2.this);
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

    private void playSoundCollect()
    {
        mp_sound_collect = MediaPlayer.create(this,R.raw.coin_collect);
        mp_sound_collect.setVolume(0.5f,0.5f);
        mp_sound_collect.start();
    }

    private void checkApePosition() {
        frameHeight = frame.getHeight();
        frameWidth = frame.getWidth();
        apeHeightSize = ape.getHeight();
        apeWidthSize = ape.getWidth();

        if (apeX == fruit1X)
        {
            fruit1.setVisibility(View.INVISIBLE);
            playSoundCollect();
        }
        if (apeX == fruit2X)
        {
            fruit2.setVisibility(View.INVISIBLE);
            playSoundCollect();
        }
        if (apeX == fruit3X)
        {
            fruit3.setVisibility(View.INVISIBLE);
            playSoundCollect();
        }
        if (apeX == fruit4X)
        {
            fruit4.setVisibility(View.INVISIBLE);
            playSoundCollect();
        }
    }

}
