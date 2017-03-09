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



        Vector<Byte> signalMockup = new Vector<>(100);
        for(int i=0; i<10; i++){
            signalMockup.add((byte) 1);
            signalMockup.add((byte) 1);
            signalMockup.add((byte) 7);
            signalMockup.add((byte) 5);
            signalMockup.add((byte) 11);
            signalMockup.add((byte) 2);
            signalMockup.add((byte) 4);
            signalMockup.add((byte) 1);
        }

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);

        Vector<SignalTrace> signalTraceVector = new Vector<>(10);

        signalTraceVector.add(new SignalTrace(getApplicationContext(), signalMockup, 0, paint));
        signalTraceVector.add(new SignalTrace(getApplicationContext(), signalMockup, 1, paint));
        signalTraceVector.add(new SignalTrace(getApplicationContext(), signalMockup, 2, paint));
        signalTraceVector.add(new SignalTrace(getApplicationContext(), signalMockup, 0, paint));



        MultipleTraceView multipleTraceView = new MultipleTraceView(getApplicationContext(),signalTraceVector);






        setContentView(multipleTraceView);

    }

}
