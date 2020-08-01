package com.example.navigation2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
public class Splash extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView img;
    SharedPreferences sharedPreferences;
    Boolean firsttime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        img = findViewById(R.id.img);
        img.animate().alpha(2000).setDuration(0);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent dsp = new Intent(Splash.this,MainActivity.class);
                startActivity(dsp);
                finish();
            }
        },2000);
    }
}