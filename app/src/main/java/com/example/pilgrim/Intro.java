package com.example.pilgrim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Intro extends AppCompatActivity {

    Handler handler;

    Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            Intent intent = new Intent(Intro.this, login.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        handler = new Handler();
        handler.postDelayed(runnable, 1500);


    }

//    @Override
//    protected void onRestart()
//    {
//        super.onRestart();
//        setContentView(R.layout.intro);
//        ImageView move = (ImageView) findViewById(R.id.imageView);
//        Glide.with(this).load(R.drawable.pensumove).into(move);
//        handler = new Handler();
//        handler.postDelayed(runnable, 1000);
//    }
//
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        setContentView(R.layout.intro);
//        ImageView move = (ImageView) findViewById(R.id.imageView);
//        Glide.with(this).load(R.drawable.pensumove).into(move);
//        handler = new Handler();
//        handler.postDelayed(runnable, 1000);
//    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }


}

