package com.pitersk.poormanslogicanalyzerapp;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Vector;

/**
 * Created by piter on 2017-03-09.
 */

public class MultipleTraceView extends LinearLayout {

    public MultipleTraceView(Context context,Vector<SignalTrace> signalTraceVector) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(Color.YELLOW);
        this.setWeightSum(signalTraceVector.size());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        lp.weight = 1;
        lp.setMargins(0,0,0,5);

        for( SignalTrace trace : signalTraceVector){
            trace.setBackgroundColor(Color.BLACK);
            trace.setLayoutParams(lp);
            this.addView(trace);
        }

    }

}
