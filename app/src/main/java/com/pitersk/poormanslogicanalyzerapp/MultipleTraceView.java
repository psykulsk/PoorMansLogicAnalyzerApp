package com.pitersk.poormanslogicanalyzerapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.sql.Time;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by piter on 2017-03-09.
 */

/*
*  View that gathers all SignalTrace views.
* */

public class MultipleTraceView extends LinearLayout {

    private GestureDetector scrollGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private TimeCursor cursorOne;
    private TimeCursor cursorTwo;
    private ToggleButton cursorSelectButton;
    private ToggleButton moveButton;
    private TextView frequencyText;

    private float mScaleFactor = 0.05f;
    private float xOffset = 0.0f;

    private int numberOfSignals = 3;

    public MultipleTraceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(Color.WHITE);
        scrollGestureDetector = new GestureDetector(context, new mScrollGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(context, new MultipleTraceView.mScaleGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initializeLayouts();

        // Different handling of touch events, depending on the moveButton state
        // When checked, user scrolls or scales the signal trace
        // Otherwise, depending on the cursorSelectButton state, one of the cursors is moved
        if (moveButton.isChecked()) {
            scaleGestureDetector.onTouchEvent(event);
            if (event.getPointerCount() == 1)
                scrollGestureDetector.onTouchEvent(event);
        } else {
            if (cursorSelectButton.isChecked()) {
                cursorOne.scrollGestureDetector.onTouchEvent(event);
            } else {
                cursorTwo.scrollGestureDetector.onTouchEvent(event);
            }
        }
        modifyCursorOne();
        modifyCursorTwo();
        // Calculated frequency message
        frequencyText.setText(String.format("f = %.2f kHz", 1000.0f * calculateFrequency()));
        return true;
    }

    // Calculates frequency based on difference in position of the two cursors and current scale
    private float calculateFrequency() {
        float timeUnitWidth = mScaleFactor * getWidth();
        float cursorOffset = cursorOne.getxCursorPosition();
        int numberOfOffsetSamplesToTheLeft = -(int) Math.floor((double) (xOffset - cursorOffset) / timeUnitWidth);
        float timeOffsetOne = ((float) numberOfOffsetSamplesToTheLeft) * SignalTrace.TIME_UNIT_US;
        timeUnitWidth = mScaleFactor * getWidth();
        cursorOffset = cursorTwo.getxCursorPosition();
        numberOfOffsetSamplesToTheLeft = -(int) Math.floor((double) (xOffset - cursorOffset) / timeUnitWidth);
        float timeOffsetTwo = ((float) numberOfOffsetSamplesToTheLeft) * SignalTrace.TIME_UNIT_US;
        return 1.0f / (Math.abs(timeOffsetOne - timeOffsetTwo));
    }

    private void initializeLayouts() {
        if (cursorOne == null) {
            cursorOne = (TimeCursor) getRootView().findViewById(R.id.cursor_one);
            cursorOne.setCursorType(TimeCursor.CursorType.ONE);
        }
        if (cursorTwo == null) {
            cursorTwo = (TimeCursor) getRootView().findViewById(R.id.cursor_two);
            cursorTwo.setCursorType(TimeCursor.CursorType.TWO);
        }
        if (cursorSelectButton == null)
            cursorSelectButton = (ToggleButton) getRootView().findViewById(R.id.cursor_selection_button);
        if (moveButton == null)
            moveButton = (ToggleButton) getRootView().findViewById(R.id.move_button);

        if (frequencyText == null)
            frequencyText = (TextView) getRootView().findViewById(R.id.frequency);
    }

    private void modifyCursorOne() {
        float timeUnitWidth = mScaleFactor * getWidth();
        float cursorOffset = cursorOne.getxCursorPosition();
        int numberOfOffsetSamplesToTheLeft = -(int) Math.floor((double) (xOffset - cursorOffset) / timeUnitWidth);
        float timeOffset = ((float) numberOfOffsetSamplesToTheLeft) * SignalTrace.TIME_UNIT_US;
        cursorOne.setTimeOffset(timeOffset);
        cursorOne.invalidate();
    }

    private void modifyCursorTwo() {
        float timeUnitWidth = mScaleFactor * getWidth();
        float cursorOffset = cursorTwo.getxCursorPosition();
        int numberOfOffsetSamplesToTheLeft = -(int) Math.floor((double) (xOffset - cursorOffset) / timeUnitWidth);
        float timeOffset = ((float) numberOfOffsetSamplesToTheLeft) * SignalTrace.TIME_UNIT_US;
        cursorTwo.setTimeOffset(timeOffset);
        cursorTwo.invalidate();
    }

    public void setSignalTraces(byte[] newData) {
        this.setWeightSum(numberOfSignals);
        this.removeAllViews();
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        Byte[] data = new Byte[newData.length];
        int j = 0;
        for (byte b : newData) data[j++] = b;
        Vector<Byte> dataVector = new Vector<Byte>(Arrays.asList(data));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
        layoutParams.setMargins(0, 2, 0, 2);

        for (int i = 0; i < numberOfSignals; ++i) {
            SignalTrace signalTrace = new SignalTrace(getContext(), dataVector, i, paint);
            signalTrace.setLayoutParams(layoutParams);
            signalTrace.setBackgroundColor(Color.BLACK);
            this.addView(signalTrace);
        }
        this.invalidate();
    }

    private class mScrollGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            for (int i = 0; i < MultipleTraceView.this.getChildCount(); ++i) {
                SignalTrace signalTraceView = (SignalTrace) MultipleTraceView.this.getChildAt(i);
                // Changing offset results in the scrolling effect
                if (xOffset - distanceX <= 0)
                    xOffset -= distanceX;
                signalTraceView.setxOffset(xOffset);
                signalTraceView.invalidate();
            }
            return true;
        }
    }

    private class mScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        private static final float MIN_SCALE_FACTOR = 0.003f;
        private static final float MAX_SCALE_FACTOR = 5.0f;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            mScaleFactor *= detector.getScaleFactor();
            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(MIN_SCALE_FACTOR, Math.min(mScaleFactor, MAX_SCALE_FACTOR));

            // Set scale for each trace view
            for (int i = 0; i < MultipleTraceView.this.getChildCount(); ++i) {
                SignalTrace signalTraceView = (SignalTrace) MultipleTraceView.this.getChildAt(i);
                signalTraceView.setTimeScale(mScaleFactor);
                signalTraceView.invalidate();
            }
            return true;
        }
    }

}
