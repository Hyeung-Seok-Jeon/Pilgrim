package com.example.pilgrim.interfacepack;

import android.view.animation.Animation;

public interface ClickAni extends Animation.AnimationListener {

    @Override
    void onAnimationStart(Animation animation);

    @Override
    void onAnimationEnd(Animation animation);

    @Override
    void onAnimationRepeat(Animation animation);
}
