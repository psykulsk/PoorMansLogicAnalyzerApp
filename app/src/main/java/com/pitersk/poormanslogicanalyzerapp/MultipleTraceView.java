package com.pitersk.poormanslogicanalyzerapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.Vector;

/**
 * Created by piter on 2017-03-09.
 */

/*
*  View that gathers all SignalTrace views.
* */

public class MultipleTraceView extends LinearLayout {

    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    private float mScaleFactor = 0.05f;

    private int numberOfSignals = 4;

    public MultipleTraceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(Color.WHITE);
        gestureDetector = new GestureDetector(context, new MultipleTraceView.mGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(context, new MultipleTraceView.mScaleGestureListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Let the gestureDetector inspect all events;
        gestureDetector.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);

        return true;
    }


    public void setSignalTraces(byte[] newData){
        this.setWeightSum(numberOfSignals);
        this.removeAllViews();
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        Byte[] data = new Byte[newData.length];
        int j = 0;
        for( byte b : newData) data[j++] = b;
        Vector<Byte> dataVector = new Vector<Byte>(Arrays.asList(data));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,0,1);
        layoutParams.setMargins(0,2,0,2);

        for(int i = 0; i < numberOfSignals; ++i){
            SignalTrace signalTrace = new SignalTrace(getContext(), dataVector, i, paint );
            signalTrace.setLayoutParams(layoutParams);
            signalTrace.setBackgroundColor(Color.BLACK);
            this.addView(signalTrace);
        }
        this.invalidate();
    }

    private class mGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            for(int i=0; i < MultipleTraceView.this.getChildCount(); ++i){
                SignalTrace signalTraceView = (SignalTrace)MultipleTraceView.this.getChildAt(i);
                signalTraceView.modifyOffset(-distanceX);
                signalTraceView.invalidate();
            }
            return true;
        }
    }

    private class mScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            mScaleFactor *= detector.getScaleFactor();
            // Don't let the object get too small or too large.
            mScaleFactor= Math.max(0.001f, Math.min(mScaleFactor, 5.0f));

            for(int i=0; i < MultipleTraceView.this.getChildCount(); ++i){
                SignalTrace signalTraceView = (SignalTrace)MultipleTraceView.this.getChildAt(i);
                signalTraceView.setTimeScale(mScaleFactor);
                signalTraceView.invalidate();
            }
            return true;
        }
    }



}
