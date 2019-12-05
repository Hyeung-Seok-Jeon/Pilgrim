package com.example.pilgrim.Timerpack;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class TimerThread extends Thread {

    TimerHandler timerHandler;

    int s = 0;
    int m = 0;

    public TimerThread(TimerHandler timerHandler) {
        this.timerHandler = timerHandler;
    }

    @Override
    public void run() {
        Bundle bundle = new Bundle();
        try {
            T:
            while (true) {

                Thread.sleep(1000);
                s++;

                if (s >= 60) {
                    m++;
                    s = 0;
                }

//                Log.d("Thread", m + ":" + s);

                Message message = timerHandler.obtainMessage();
                bundle.putInt("sec", s);
                bundle.putInt("min", m);
                message.setData(bundle);

                timerHandler.sendMessage(message);
            }
        } catch (InterruptedException e) {
            Log.d("Timer", "Interruoted");
        }
    }
}