package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.content.*;


public class MainActivity extends AppCompatActivity {
  private static int SPLASH_TIME_OUT=1600;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(MainActivity.this, tabLogin.class);
                startActivity(home);
                finish();


            }
        },SPLASH_TIME_OUT);

    }
}



