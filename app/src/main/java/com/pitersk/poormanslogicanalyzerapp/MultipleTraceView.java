package com.pitersk.poormanslogicanalyzerapp;

import android.content.Context;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Vector;

/**
 * Created by piter on 2017-03-09.
 */

/*
*  View that gathers all SignalTrace views.
* */

public class MultipleTraceView extends LinearLayout {

    private GestureDetector gestureDetector;

    public MultipleTraceView(Context context,Vector<SignalTrace> signalTraceVector) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(Color.WHITE);
        this.setWeightSum(signalTraceVector.size());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        lp.weight = 1;
        lp.setMargins(0,2,0,2);

        for( SignalTrace trace : signalTraceVector){
            trace.setBackgroundColor(Color.BLACK);
            trace.setLayoutParams(lp);
            this.addView(trace);
        }

        gestureDetector = new GestureDetector(context, new MultipleTraceView.mGestureListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Let the gestureDetector inspect all events;
        gestureDetector.onTouchEvent(event);

        return true;
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

}
