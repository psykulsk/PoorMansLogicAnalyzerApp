package com.pitersk.poormanslogicanalyzerapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.Vector;

/**
 * Created by piter on 2017-03-04.
 */

public class SignalTrace extends View {

    private float timeScale = 0.05f;
    private float signalHeightScale = 0.8f;
    private int middlePoint = 0;
    private float timeUnitWidth = 1.0f;
    private final float signalHeight;
    private Paint linePaint;
    private Vector<Byte> signalVector;
    private final int traceNumber;

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactorX = 1.f;
    private float mScaleFactorY = 1.f;

    public SignalTrace(Context context,
                       Vector<Byte> integerVector, int traceNum, Paint paint) {

        super(context);
        linePaint = paint;
        linePaint.setStrokeWidth(10);
        signalVector = integerVector;
        traceNumber = traceNum;
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public static byte getBit(byte _byte, int position) {
        return  ( byte)((_byte >> position) & 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvas.save();
        canvas.scale(mScaleFactorX, mScaleFactorY);

        timeUnitWidth = timeScale * getWidth();
        float xPosition = 0f;

        for (Byte sample : signalVector) {
            if (1 == getBit(sample, traceNumber)) {
                canvas.drawLine(xPosition, signalHeight + 100, xPosition + timeUnitWidth, signalHeight + 100, linePaint);
            } else {
                canvas.drawLine(xPosition, signalHeight, xPosition + timeUnitWidth, signalHeight, linePaint);
            }

            xPosition += timeUnitWidth;
        }

        canvas.restore();


    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //Let the ScaleGestureDetector inspect all events;
        mScaleDetector.onTouchEvent(ev);
        return true;
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactorX *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactorX = Math.max(0.1f, Math.min(mScaleFactorX, 5.0f));

            invalidate();
            return true;
        }
    }


}
