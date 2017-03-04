package com.pitersk.poormanslogicanalyzerapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by piter on 2017-03-02.
 */

public class BouncingBall extends View {
    private int xMin = 0;          // This view's bounds
    private int xMax;
    private int yMin = 0;
    private int yMax;
    private float ballRadius = 30; // Ball's radius
    private float ballX = ballRadius + 20;  // Ball's center (x,y)
    private float ballY = ballRadius + 40;
    private double ballSpeedX = 5;  // Ball's speed (x,y)
    private double ballSpeedY = 3;
    private double gravityY = 5;
    private double borderBounceFactor = 0.75;
    private RectF ballBounds;      // Needed for Canvas.drawOval
    private Paint paint;           // The paint (e.g. style, color) used for drawing

    // For touch inputs - previous touch (x, y)
    private float previousX;
    private float previousY;

    public BouncingBall(Context context) {
        super(context);
        ballBounds = new RectF();
        paint = new Paint();
        // To enable touch mode
        this.setFocusableInTouchMode(true);
    }

    public BouncingBall(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        Random generator = new Random();
        ballSpeedX = generator.nextInt(10)-5;
        ballSpeedY = generator.nextInt(10)-5;
        ballBounds = new RectF();
        paint = new Paint();
        // To enable touch mode
        this.setFocusableInTouchMode(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //Draw the ball

        ballBounds.set(ballX - ballRadius, ballY - ballRadius, ballX + ballRadius, ballY + ballRadius);
        paint.setColor(Color.GREEN);
        canvas.drawOval(ballBounds, paint);

        // Update the postion of the ball, including collision detectin and reaction.
        update();

        // Delay

/*        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Log.println(Log.DEBUG, "Boucning ball tag:", "Interrupted sleep");
        }*/

        invalidate();
    }

    // Detect collision and update the position of the ball
    private void update() {
        // Get new (x,y) position
        ballSpeedY += gravityY;
        ballX += ballSpeedX;
        ballY += ballSpeedY;
        // Detect collision and react
        if (ballX + ballRadius > xMax) {
            ballSpeedX = -borderBounceFactor*ballSpeedX;
            ballX = xMax - ballRadius;
        } else if (ballX - ballRadius < xMin) {
            ballSpeedX = -borderBounceFactor*ballSpeedX;
            ballX = xMin + ballRadius;
        }
        if (ballY + ballRadius > yMax) {
            ballSpeedY = -borderBounceFactor*ballSpeedY;
            ballY = yMax - ballRadius;
        } else if (ballY - ballRadius < yMin) {
            ballSpeedY = -borderBounceFactor*ballSpeedY;
            ballY = yMin + ballRadius;
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        // Set the movement bounds for the ball
        xMax = w - 1;
        yMax = h - 1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        float deltaX, deltaY;
        float scalingFactor = 0.1f;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // Modify rotational angles according to movement
                deltaX = currentX - previousX;
                deltaY = currentY - previousY;
                ballSpeedX += deltaX * scalingFactor;
                ballSpeedY += deltaY * scalingFactor;
        }
        // Save current x, y
        previousX = currentX;
        previousY = currentY;
        return true;  // Event handled
    }

}
