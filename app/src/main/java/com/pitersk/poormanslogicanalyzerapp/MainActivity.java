package com.pitersk.poormanslogicanalyzerapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;

    private final static int REQUEST_ENABLE_BT = 1;

    private final static String deviceName = "PIOTREK-NEW";


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
