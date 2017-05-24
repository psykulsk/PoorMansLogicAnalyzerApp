package com.pitersk.poormanslogicanalyzerapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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

    public SignalTrace(Context context,
                       Vector<Byte> integerVector, int traceNum, Paint paint) {

        super(context);
        linePaint = paint;
        linePaint.setStrokeWidth(10);
        signalVector = integerVector;
        traceNumber = traceNum;

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


    public void setTimeScale(float timeScale) {
        this.timeScale = timeScale;
    }

    private void drawSignalTrace(Canvas canvas){
        float xPosition = 0;

        boolean currentBit;
        boolean previousBit = false;

        timeUnitWidth = timeScale * getWidth();

        /*
        * Calculations that will allow to draw on the screen only samples that can be seen.
        * Without this, with long vectors of samples, drawing all of them wouldn't be efficient.
        * */
        int numberOfDrawnSamples = (int)Math.ceil(1/(double)(timeScale))+1;
        int numberOfOffsetSamplesToTheLeft = -(int)Math.floor((double)xOffset/timeUnitWidth);

        int totalNumberOfSamples = signalVector.size();

        if(numberOfOffsetSamplesToTheLeft < 0)
            numberOfOffsetSamplesToTheLeft = 0;
        else if(numberOfOffsetSamplesToTheLeft >= totalNumberOfSamples)
            numberOfOffsetSamplesToTheLeft = totalNumberOfSamples -1;

        int indexOfLastDrawnSample = numberOfOffsetSamplesToTheLeft + numberOfDrawnSamples;

        if(indexOfLastDrawnSample >= totalNumberOfSamples)
            indexOfLastDrawnSample = totalNumberOfSamples - 1;

        /*
        * For each visible sample check the bit on the traceNumber position and draw a line on a proper height.
        * */
        for ( int index = numberOfOffsetSamplesToTheLeft; index < indexOfLastDrawnSample; index++) {
            Byte sample = signalVector.elementAt(index);
            currentBit = getBit(sample, traceNumber);
            if(previousBit != currentBit && index != 0){
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
    Checks whether bit on certain position in given byte is set to 1
*/
    public static boolean getBit(byte _byte, int position) {

        return 1 == ( byte)((_byte >> position) & 1);

    }

    public void modifyOffset(float change) {
        this.xOffset += change;
    }


}
