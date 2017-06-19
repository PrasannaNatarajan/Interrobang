package com.example.p_natarajan.wifipoc;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    TextView t1,t2,t3;
    Button button;

    public static Map<String,Integer> wifiDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);
        t3 = (TextView) findViewById(R.id.textView3);

        button = (Button) findViewById(R.id.button2);
        wifiDetails = new HashMap<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager myWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                boolean wasEnabled = myWifiManager.isWifiEnabled();
                if (!wasEnabled)
                    myWifiManager.setWifiEnabled(true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("pras","inside permission");
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0x12345);
                }

                if (myWifiManager.isWifiEnabled()) {
                    if (myWifiManager.startScan()) {
                        // List available APs
                        Log.d("pras", "inside scan");
                        List<ScanResult> scans = myWifiManager.getScanResults();
                        Log.d("pras", "" + (scans == null));
                        Log.d("pras", "" + scans.isEmpty());
                        if (scans != null && !scans.isEmpty()) {
                            int i=0;
                            for (ScanResult scan : scans) {
                                int level = WifiManager.calculateSignalLevel(scan.level, 20);
                                //Other code
                                Log.d("wifi", level + "");

                                Log.d("pras",scan.SSID);

                                wifiDetails.put(scan.BSSID,level);
                            }
                        } else {
                            Log.d("pras", "inside else");
                        }
                    }
                }


                Set<Map.Entry<String, Integer>> set = wifiDetails.entrySet();
                List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
                Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
                {
                    public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
                    {
                        return (o2.getValue()).compareTo( o1.getValue() );
                    }
                } );
                for(Map.Entry<String, Integer> entry:list){
                    Log.d("Pras",entry.getKey()+" ==== "+entry.getValue());
                }

                t1.setText(list.get(0).getKey()+" ==== "+list.get(0).getValue());
                t2.setText(list.get(1).getKey()+" ==== "+list.get(1).getValue());
                t3.setText(list.get(2).getKey()+" ==== "+list.get(2).getValue());




            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0x12345) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            WifiManager myWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            boolean wasEnabled = myWifiManager.isWifiEnabled();
            if (!wasEnabled)
                myWifiManager.setWifiEnabled(true);
            if (myWifiManager.isWifiEnabled()) {
                if (myWifiManager.startScan()) {
                    // List available APs
                    Log.d("pras", "this inside scan");
                    List<ScanResult> scans = myWifiManager.getScanResults();
                    Log.d("pras", "" + (scans == null));
                    Log.d("pras", "" + scans.isEmpty());
                    if (scans != null && !scans.isEmpty()) {
                        for (ScanResult scan : scans) {
                            int level = WifiManager.calculateSignalLevel(scan.level, 20);
                            //Other code
                            Log.d("wifi", level + "this");
                        }
                    } else {
                        Log.d("pras", "this inside else");
                    }
                }
            }

        }

    }

}
