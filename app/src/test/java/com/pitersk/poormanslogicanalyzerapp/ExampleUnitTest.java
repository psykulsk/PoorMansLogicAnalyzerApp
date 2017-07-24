package com.pitersk.poormanslogicanalyzerapp;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getBit_isCorrect() throws Exception {
        assertEquals(true, SignalTrace.getBit((byte) 2, 1));
        assertEquals(true, SignalTrace.getBit((byte) 1, 0));
        assertEquals(true, SignalTrace.getBit((byte) 8, 3));
    }

}