package com.pitersk.poormanslogicanalyzerapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.GestureDetector;
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
    private float xOffset = 0;
    private int middlePoint = 0;
    private float timeUnitWidth = 1.0f;
    private  float signalHeight;
    private Paint linePaint;
    private Vector<Byte> signalVector;
    private final int traceNumber;

    private GestureDetector gestureDetector;

    public SignalTrace(Context context,
                       Vector<Byte> integerVector, int traceNum, Paint paint) {

        super(context);
        linePaint = paint;
        linePaint.setStrokeWidth(10);
        signalVector = integerVector;
        traceNumber = traceNum;
        gestureDetector = new GestureDetector(context, new mGestureListener());
        timeUnitWidth = timeScale * getWidth();
        signalHeight = signalHeightScale * getHeight();


    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvas.save();


        drawSignalTrace(canvas);

        canvas.restore();


    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        timeUnitWidth = timeScale * getWidth();
        signalHeight = signalHeightScale * getHeight();
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
            xOffset -= distanceX;
            invalidate();
            return true;
        }
    }

    private void drawSignalTrace(Canvas canvas){
        float xPosition = xOffset;

        boolean currentBit;
        boolean previousBit = false;

        /*
        * For Each sample check the bit on the traceNumber position and draw a line on a proper height.
        * */
        for (Byte sample : signalVector) {
            currentBit = getBit(sample, traceNumber);
            if(previousBit != currentBit){
                canvas.drawLine(xPosition, this.getHeight()/2 - signalHeight/2 , xPosition,
                        this.getHeight()/2 + signalHeight/2, linePaint);
            }
            if (currentBit) {
                canvas.drawLine(xPosition, this.getHeight()/2 + signalHeight/2 , xPosition + timeUnitWidth,
                        this.getHeight()/2 + signalHeight/2, linePaint);
            } else {
                canvas.drawLine(xPosition, this.getHeight()/2 - signalHeight/2, xPosition + timeUnitWidth,
                        this.getHeight()/2 - signalHeight/2, linePaint);
            }
            previousBit = currentBit;
            xPosition += timeUnitWidth;
        }
    }

    /*
    Check whether bit on certain position in given byte is set to 1
*/
    private boolean getBit(byte _byte, int position) {
        if(1 == ( byte)((_byte >> position) & 1))
            return true;
        else
            return false;
    }


}
