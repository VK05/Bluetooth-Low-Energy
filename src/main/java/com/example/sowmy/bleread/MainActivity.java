package com.example.sowmy.bleread;

import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

    private BluetoothAdapter mBluetoothAdapter;
    private Button changeStatus;
    private TextView status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference to the button
        changeStatus = (Button) findViewById(R.id.changeStatus);
        changeStatus.setOnClickListener(this);

        //reference to the text view
        status = (TextView) findViewById(R.id.status);

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE is not supported", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //check if adatpter is available, please note if you running
        //this application in emulator currently there is no support for bluetooth
        if(mBluetoothAdapter == null){
            status.setText("Bluetooth adapter not found");
            changeStatus.setText("Bluetooth Disabled");
            changeStatus.setEnabled(false);
        }
        //check the status and set the button text accordingly
        else {
            if (mBluetoothAdapter.isEnabled()) {
                status.setText("Bluetooth is currently switched ON");
                changeStatus.setText("Switch OFF Bluetooth");
                Intent intent = new Intent(this, DeviceScanActivity.class);
                startActivity(intent);
                finish();
            }else{
                status.setText("Bluetooth is currently switched OFF");
                changeStatus.setText("Switch ON Bluetooth");
            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.changeStatus:
                //disable the bluetooth adapter
                if (mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.disable();
                    status.setText("Bluetooth is currently switched OFF");
                    changeStatus.setText("Switch ON Bluetooth");

                }
                //enable the bluetooth adapter
                else{
                    status.setText("Bluetooth is currently switched ON");
                    changeStatus.setText("Switch OFF Bluetooth");
                    Intent intent = new Intent(this, DeviceScanActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            // More buttons go here (if any) ...
        }

    }
}
