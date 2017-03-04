package com.pitersk.poormanslogicanalyzerapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Vector;

/**
 * Created by piter on 2017-03-04.
 */

public class SignalTrace extends View {

    private float timeScale = 1.0f;
    private int middlePoint = 0;
    private Paint linePaint;
    private Vector<Byte> signalVector;

    public SignalTrace(Context context, AttributeSet attributeSet,
                       Vector<Byte> integerVector, int traceNumber, Paint paint) {

        super(context, attributeSet);
        linePaint = paint;
        signalVector = integerVector;

    }

    private byte getBit ( byte _byte ,int position ){
        return  ( byte)((_byte >> position) & 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float xPosition = 0f;

        for (Byte sample : signalVector) {

        }
        canvas.drawLine();

    }


}
