package com.example.hardcoregym;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hardcoregym.Intro;
import com.example.hardcoregym.R;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    TextView Gymname, JustGym;
    Animation leftoright, righttoleft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        logo        = findViewById(R.id.logo);
        Gymname   = findViewById(R.id.tvAppName);
        JustGym = findViewById(R.id.tvGym);

        logo.animate().translationX(2000).setDuration(2000).setStartDelay(2900);

        leftoright  = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        righttoleft = AnimationUtils.loadAnimation(this,R.anim.righttoleft);

        JustGym.setAnimation(righttoleft);
        Gymname.setAnimation(leftoright);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent a = new Intent(SplashScreen.this, Intro.class);
                startActivity(a);
                finish();
            }
        },2500);
    }
}