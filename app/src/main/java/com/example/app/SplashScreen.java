package com.example.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    TextView textView;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.progress);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        progressAnimation();

        mTextView = findViewById(R.id.name_app);

        YoYo.with(Techniques.Tada).duration(700).repeat(1).playOn(mTextView);

    }

    public void progressAnimation(){
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar,textView,0f,100f);
        anim.setDuration(5000);//5 segundos
        progressBar.setAnimation(anim);
    }
}
