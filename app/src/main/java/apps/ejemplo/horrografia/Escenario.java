package apps.ejemplo.horrografia;


import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.app.R;

import java.util.Timer;



public class Escenario extends AppCompatActivity {
    RelativeLayout frame;

    private ImageView backgroundOne;
    private ImageView backgroundTwo;

    private ImageView tree;
    private ImageView bird;
    private ImageView star;
    private ImageView ape;
    private ImageView coin;
    private ImageView apple;

    private Button up;
    private Button down;
    private Button left;
    private Button right;

    private Button leftape;
    private Button rightape;

    private int frameHeight;
    private int frameWidth;
    private int birdHeightSize;
    private int birdWidthSize;

    private int apeWidthSize;

    //Position bird
    private int birdY;
    private int birdX;

    //Position ape
    private int apeX;
    private int apeY;

    //Position apple
    private int appleY;
    private int appleX;

    //Initialize class
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    AnimationDrawable flapping;
    AnimationDrawable animationStar;
    AnimationDrawable walkingape;
    AnimationDrawable animationCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario);

        up =(Button)findViewById(R.id.btnUp);
        down =(Button)findViewById(R.id.btnDown);
        left = (Button)findViewById(R.id.btnLeft);
        right = (Button)findViewById(R.id.btnRight);
        leftape = (Button)findViewById(R.id.btnLeftApe);
        rightape = (Button)findViewById(R.id.btnRightApe);


        frame = (RelativeLayout) findViewById(R.id.bgBird);



        bird = (ImageView)findViewById(R.id.bird);
        bird.setBackgroundResource(R.drawable.flappingwings);
        flapping =(AnimationDrawable)bird.getBackground();
        flapping.start();

        birdY = 500;
        birdX = 10;
        bird.setY(birdY);
        bird.setX(birdX);

        star = (ImageView)findViewById(R.id.star);
        star.setBackgroundResource(R.drawable.animationstars);
        animationStar = (AnimationDrawable)star.getBackground();
        animationStar.start();

        star.setX(500);
        star.setY(300);

        ape = (ImageView)findViewById(R.id.ape);
        ape.setBackgroundResource(R.drawable.animationape);
        walkingape = (AnimationDrawable)ape.getBackground();
        walkingape.start();

        apeX = 300;
        apeY= 800;
        ape.setX(apeX);
        ape.setY(apeY);


        coin = (ImageView)findViewById(R.id.coin);
        coin.setBackgroundResource(R.drawable.animationcoins);
        animationCoins = (AnimationDrawable)coin.getBackground();
        animationCoins.start();

        coin.setX(600);
        coin.setY(700);

        apple = (ImageView)findViewById(R.id.apple);
        appleX = 300;
        appleY = 300;
        apple.setX(appleX);
        apple.setY(appleY);

        leftape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apeX -= 30;
                checkApePosition();
                ape.setX(apeX);
            }
        });

        rightape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apeX += 30;
                checkApePosition();
                ape.setX(apeX);
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birdY -= 20;
                checkBirdPosition();
                bird.setY(birdY);
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birdY += 20;
                checkBirdPosition();
                bird.setY(birdY);
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birdX -=20;
                checkBirdPosition();
                bird.setX(birdX);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birdX +=20;
                checkBirdPosition();
                bird.setX(birdX);
            }
        });


        backgroundOne = (ImageView) findViewById(R.id.background_one);
        backgroundTwo = (ImageView) findViewById(R.id.background_two);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
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

        tree = (ImageView)findViewById(R.id.tree);
        tree.setX(600);
        tree.setY(500);



    }

    private void checkApePosition() {
        frameWidth = frame.getWidth();
        apeWidthSize = ape.getWidth();
        if (apeX < 0)
            apeX = 0;
        if (apeX > frameWidth - apeWidthSize)
            apeX = frameWidth - apeWidthSize;
    }

    private void checkBirdPosition() {
        frameHeight = frame.getHeight();
        frameWidth = frame.getWidth();
        birdHeightSize = bird.getHeight();
        birdWidthSize = bird.getWidth();

        if (birdCollision(appleX,appleY)){
            Toast.makeText(Escenario.this,"Colisión",Toast.LENGTH_SHORT).show();
            apple.setVisibility(View.INVISIBLE);
        }
        if (birdY < 0)
            birdY = 0;
        if (birdY > frameHeight - birdHeightSize)
            birdY = frameHeight - birdHeightSize;
        if (birdX < 0)
            birdX = 0;
        if (birdX > frameWidth - birdWidthSize)
            birdX = frameWidth - birdWidthSize;
    }

    private boolean birdCollision(int x,int y){
        if(birdX < x && x < (birdX+ bird.getWidth()) && birdY < y && y < (birdY + bird.getHeight())){
            return true;
        }
        return false;
    }

}
