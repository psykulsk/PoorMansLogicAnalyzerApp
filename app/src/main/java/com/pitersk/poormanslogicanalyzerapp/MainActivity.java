package com.pitersk.poormanslogicanalyzerapp;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SignalTraceFragment fragment = new SignalTraceFragment();
            transaction.add(R.id.main_fragment_container, fragment);
            transaction.commit();
        }
    }

}
