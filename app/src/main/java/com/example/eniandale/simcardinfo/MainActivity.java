package com.example.eniandale.simcardinfo;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.Manifest.permission;
import android.content.pm.PackageManager;

public class MainActivity extends AppCompatActivity {

    private TextView alltext;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alltext  = (TextView)findViewById(R.id.text1);
        alltext.setMovementMethod(new ScrollingMovementMethod());

        if(checkPermission()){
            realMainActivity();
        }
        else{
            requestPermission();
        }

    }


    private void realMainActivity(){
        TelephonyManager manage = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        String simCountryIso = manage.getSimCountryIso();
        String simOperator = manage.getSimOperator();
        String simOperatorName = manage.getSimOperatorName();
        String simSerialNumber = manage.getSimSerialNumber();
        String simSubscriberId = manage.getSubscriberId();
        int simState = manage.getSimState();
        String sSimStateString = "Not Defined";
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                sSimStateString = "ABSENT";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                sSimStateString = "NETWORK_LOCKED";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                sSimStateString = "PIN_REQUIRED";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                sSimStateString = "PUK_REQUIRED";
                break;
            case TelephonyManager.SIM_STATE_READY:
                sSimStateString = "STATE_READY";
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                sSimStateString = "STATE_UNKNOWN";
                break;
        }
        alltext.append(
                "\nSim CountryIso: " + simCountryIso +
                        "\nSim Operator: " + simOperator +
                        "\nSim OperatorName: " + simOperatorName +
                        "\nSim SerialNumber: " + simSerialNumber +
                        "\nSim SubscriberId: " + simSubscriberId +
                        "\nSim StateString: " + sSimStateString);
    }



    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), permission.READ_PHONE_STATE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), permission.ACCESS_COARSE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{permission.READ_PHONE_STATE, permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean readStateAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean locationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (readStateAccepted && locationAccepted) {
                        //do the programm code
                        realMainActivity();
                    }

                    else{
                        alltext.setText("I need both permissions");
                    }
                    break;
                }
        }
    }
}
