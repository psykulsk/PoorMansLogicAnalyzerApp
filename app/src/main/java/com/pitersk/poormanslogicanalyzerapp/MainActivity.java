package com.pitersk.poormanslogicanalyzerapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import java.util.Set;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;

    private final static int REQUEST_ENABLE_BT = 1;

    private final static String deviceName = "PIOTREK-NEW";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Phone does not support bluetooth
        if (bluetoothAdapter == null) {
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        if(!bluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT);
        }

        Set<BluetoothDevice> bluetoothDevices =  bluetoothAdapter.getBondedDevices();

        for( BluetoothDevice btDevice : bluetoothDevices){
            if( deviceName == btDevice.getName()) {
                bluetoothDevice = btDevice;
                break;
            }
        }



        Vector<Byte> signalMockup = new Vector<>(100);
        for(int i=0; i<10; i++){
            signalMockup.add((byte) 1);
            signalMockup.add((byte) 1);
            signalMockup.add((byte) 7);
            signalMockup.add((byte) 5);
            signalMockup.add((byte) 11);
            signalMockup.add((byte) 2);
            signalMockup.add((byte) 4);
            signalMockup.add((byte) 1);
        }

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);

        Vector<SignalTrace> signalTraceVector = new Vector<>(10);

        signalTraceVector.add(new SignalTrace(getApplicationContext(), signalMockup, 0, paint));
        signalTraceVector.add(new SignalTrace(getApplicationContext(), signalMockup, 1, paint));
        signalTraceVector.add(new SignalTrace(getApplicationContext(), signalMockup, 2, paint));
        signalTraceVector.add(new SignalTrace(getApplicationContext(), signalMockup, 0, paint));



        MultipleTraceView multipleTraceView = new MultipleTraceView(getApplicationContext(),signalTraceVector);


        setContentView(multipleTraceView);

    }

}
