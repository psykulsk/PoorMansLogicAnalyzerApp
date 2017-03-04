package com.pitersk.poormanslogicanalyzerapp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Vector;

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

        Vector<Byte> signalMockup = new Vector<>(100);
        signalMockup.add((byte) 1);
        signalMockup.add((byte) 2);
        signalMockup.add((byte) 3);
        signalMockup.add((byte) 5);
        signalMockup.add((byte) 1);
        signalMockup.add((byte) 2);
        signalMockup.add((byte) 3);
        signalMockup.add((byte) 5);
        signalMockup.add((byte) 1);
        signalMockup.add((byte) 2);
        signalMockup.add((byte) 3);
        signalMockup.add((byte) 5);
        signalMockup.add((byte) 1);
        signalMockup.add((byte) 2);
        signalMockup.add((byte) 3);
        signalMockup.add((byte) 5);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);

        LinearLayout view = new LinearLayout(getApplicationContext());
        view.setOrientation(LinearLayout.VERTICAL);
        SignalTrace signalTrace0 = new SignalTrace(getApplicationContext(), signalMockup, 0, paint);
        SignalTrace signalTrace1 = new SignalTrace(getApplicationContext(), signalMockup, 1, paint);
        SignalTrace signalTrace2 = new SignalTrace(getApplicationContext(), signalMockup, 2, paint);

        signalTrace0.setBackgroundColor(Color.BLACK);
        signalTrace1.setBackgroundColor(Color.BLACK);
        signalTrace2.setBackgroundColor(Color.BLACK);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        lp.weight = 1;

        signalTrace0.setLayoutParams(lp);
        signalTrace1.setLayoutParams(lp);
        signalTrace2.setLayoutParams(lp);

        view.setWeightSum(3);
        view.addView(signalTrace0);
        view.addView(signalTrace1);
        view.addView(signalTrace2);

        setContentView(view);

    }

}
