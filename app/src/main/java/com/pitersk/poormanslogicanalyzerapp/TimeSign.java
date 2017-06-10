package com.pitersk.poormanslogicanalyzerapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by piter on 2017-05-27.
 */

public class TimeSign extends View {

    Paint linePaint;

    public TimeSign(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setBackgroundColor(Color.TRANSPARENT);
        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setPathEffect(new DashPathEffect(new float[]{100,100}, 0));
        linePaint.setStrokeWidth(2);
    }

    public TimeSign(Context context){
        super(context);
        this.setBackgroundColor(Color.TRANSPARENT);
        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setPathEffect(new DashPathEffect(new float[]{100,100}, 0));
        linePaint.setStrokeWidth(2);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvas.save();
        drawTimeSign(canvas);

        canvas.restore();


    }

    void drawTimeSign(Canvas canvas){
        canvas.drawLine(getWidth()/2, 0.0f, getWidth()/2, getHeight(), linePaint);
    }

}
