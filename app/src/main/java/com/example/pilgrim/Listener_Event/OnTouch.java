package com.example.pilgrim.Listener_Event;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.pilgrim.R;

public class OnTouch implements View.OnTouchListener {

    Context context;

    Animation press;
    Animation release;
    Animation sparkle;
    Animation click;

    public OnTouch(Context context) {
        this.context = context;
        /***************Animation******************/
        press =
                AnimationUtils.loadAnimation(context, R.anim.effect_scale_press);
        release =
                AnimationUtils.loadAnimation(context, R.anim.effect_scale_release);
        sparkle =
                AnimationUtils.loadAnimation(context, R.anim.effect_alpha_sparkle);
        click =
                AnimationUtils.loadAnimation(context, R.anim.effect_scale_click);
        /*************Animation******************/
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                Log.i("Motion", "press");
//                Toast.makeText(context, "press", Toast.LENGTH_SHORT).show();
                v.startAnimation(press);
                return true;
            }

            case MotionEvent.ACTION_MOVE: {
                Log.i("Motion", "move");
//                Toast.makeText(context, "move", Toast.LENGTH_SHORT).show();
                v.startAnimation(sparkle);
                return true;
            }

            case MotionEvent.ACTION_CANCEL: {
                Log.i("Motion", "cancel");
//                Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                v.startAnimation(release);
                return true;
            }

            case MotionEvent.ACTION_UP: {
                Log.i("Motion", "release");
//                Toast.makeText(context, "release", Toast.LENGTH_SHORT).show();
                v.startAnimation(click);
                return false;
            }

        }

        return false;
    }
}
