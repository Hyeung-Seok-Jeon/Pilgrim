package com.example.pilgrim.Timerpack;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class TimerHandler extends Handler {
    TextView numCntView;

    public TimerHandler(TextView counter) {
        this.numCntView = counter;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);

        Bundle bundle = msg.getData();
        int sec = bundle.getInt("sec");
        int min = bundle.getInt("min");
        numCntView.setText(min + " : " + sec);
    }
}