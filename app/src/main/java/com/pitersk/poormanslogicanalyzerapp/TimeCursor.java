package com.pitersk.poormanslogicanalyzerapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by piter on 2017-05-27.
 */

public class TimeCursor extends View {

    private float timeOffset = 0.0f;
    Paint linePaint;
    TextView timeTextView;
    public GestureDetector scrollGestureDetector;
    private float xCursorPosition = 50.0f;

    public TimeCursor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setBackgroundColor(Color.TRANSPARENT);
        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setPathEffect(new DashPathEffect(new float[]{100,100}, 0));
        linePaint.setStrokeWidth(2);

        scrollGestureDetector = new GestureDetector(context, new mScrollGestureListener());

    }

    public TimeCursor(Context context){
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
        if(timeTextView == null) timeTextView = (TextView)getRootView().findViewById(R.id.cursor_one_time_text);

        canvas.drawLine(xCursorPosition, 0.0f, xCursorPosition, getHeight(), linePaint);

        timeTextView.setText(String.format("X = %.2f us", timeOffset));
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        scrollGestureDetector.onTouchEvent(event);
//        return true;
//    }

    private class mScrollGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if(xCursorPosition - distanceX > 0.0f && xCursorPosition - distanceX < getRootView().getWidth())
                xCursorPosition -= distanceX;
            invalidate();
            return true;
        }
    }

    void setTimeOffset( float timeOffset) {
        this.timeOffset = timeOffset;
    }

}
