package com.example.pilgrim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//정방향 세로로 완전히 고정,회전불가
        handler = new Handler();
        handler.postDelayed(runnable, 1500);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }


}

