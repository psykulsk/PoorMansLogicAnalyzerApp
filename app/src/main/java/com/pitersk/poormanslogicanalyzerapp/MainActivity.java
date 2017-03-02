package com.pitersk.poormanslogicanalyzerapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View bouncingBallView = new BouncingBall(this);
        setContentView(bouncingBallView);
        bouncingBallView.setBackgroundColor(Color.BLACK);

    }

}
