package com.pitersk.poormanslogicanalyzerapp;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        LinearLayout view = new LinearLayout(getApplicationContext());
        view.setOrientation(LinearLayout.VERTICAL);
        View ball1 = new BouncingBall((getApplicationContext()));
        ball1.setBackgroundColor(Color.BLACK);
        View ball2 = new BouncingBall((getApplicationContext()));
        ball2.setBackgroundColor(Color.BLUE);
        view.addView(ball1,600, 600 );
        view.addView(ball2,600,600);
        setContentView(view);
*/

        setContentView(R.layout.bouncing_ball);

    }

}
