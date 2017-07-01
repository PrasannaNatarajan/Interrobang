package com.example.p_natarajan.wifipoc;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.p_natarajan.wifipoc.positionDetails;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 250;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;

    public static ArrayList<positionDetails> aux[]= new ArrayList[3];

    TextView t1,t2,t3;
    Button button, upload;

    EditText XEdit,YEdit;

    //public static positionDetails[][] auxilary = new positionDetails[5][4];
    public static positionDetails data_download = new positionDetails();
    public static Map<String,Double> wifiDetails;
    DatabaseReference dbRef,upload_ref;

    double[][] mPositions;
    double[] distances;

    private GyroscopeObserver gyroscopeObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        gyroscopeObserver = new GyroscopeObserver();
        // Set the maximum radian the device should rotate to show image's bounds.
        // It should be set between 0 and π/2.
        // The default value is π/9.
        gyroscopeObserver.setMaxRotateRadian(Math.PI/9);

        PanoramaImageView panoramaImageView = (PanoramaImageView) findViewById(R.id.panorama_image_view);
        // Set GyroscopeObserver for PanoramaImageView.
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);

        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);
        t3 = (TextView) findViewById(R.id.textView3);

        XEdit = (EditText) findViewById(R.id.editX);
        YEdit = (EditText) findViewById(R.id.editY);

        upload = (Button) findViewById(R.id.button3);

        dbRef = FirebaseDatabase.getInstance().getReference("routers");
        upload_ref = FirebaseDatabase.getInstance().getReference("new");

        mPositions = new double[3][3];
        distances = new double[3];


        button = (Button) findViewById(R.id.button2);
        wifiDetails = new HashMap<>();
        String[] xyz=  new String[3];
        double[] arr = new double[3];

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Map.Entry<String, Double>> list = WifiStrength();

                t1.setText(list.get(0).getKey()+" ==== "+list.get(0).getValue());
                t2.setText(list.get(1).getKey()+" ==== "+list.get(1).getValue());
                t3.setText(list.get(2).getKey()+" ==== "+list.get(2).getValue());

                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String res = "";
                        int j=0,k=0;
                        for(DataSnapshot dbSnap : dataSnapshot.getChildren()){
                            Log.d("here",dbSnap.getKey());
                            k=0;
                            int i=0;
                            if(dbSnap.getKey().equals("00:e7:44:0d:67:ac") || dbSnap.getKey().equals("02:1a:11:f2:f7:15") || dbSnap.getKey().equals("14:f6:5a:60:7f:25") ) {
                                for (DataSnapshot MAC : dbSnap.getChildren()) {
                                    res += MAC.getValue().toString();
                                    xyz[i] = MAC.getValue().toString();
                                    i++;
                                    mPositions[j][k] = Double.parseDouble(MAC.getValue().toString());
                                    Log.d("mPositions[j][k]",dbSnap.getKey()+""+mPositions[j][k]);
                                    k++;
                                }

                                j++;

                                Log.d("j",j+"");
                                Log.d("k",k+"");

                            }


                            Toast.makeText(MainActivity.this,res,Toast.LENGTH_LONG).show();
                        }

                        // Call strength to distance converter
                        for(Map.Entry<String,Double> lis: list){
                            //if(lis.getKey().equals("")){
                                //distances[0] = calculateDistance(lis.getValue(),2400);
                            //}
                            switch (lis.getKey()){
                                case "78:a8:73:23:25:5a":
                                    distances[0] = calculateDistance(lis.getValue(),2437);
//                                    distances[0] = 1;0
                                    Log.d("distance",distances[0]+"");
                                    break;
                                case "8c:be:be:33:8e:6f":
                                    distances[1] = calculateDistance(lis.getValue(),2462);
//                                    distances[1] = 2;
                                    Log.d("distance",distances[1]+"");
                                    break;
                                case "c0:ee:fb:e1:33:9b":
                                    distances[2] = calculateDistance(lis.getValue(),2412);
//                                    distances[2] = Math.sqrt(10);
                                    Log.d("distance",distances[2]+"");
                                    break;
                            }
                        }



                        //Call Trilateration function
                        Trilateration t = new Trilateration(mPositions,distances);

                        double[] centroid = t.getLocation();


                        // fix the co-ordinates as xyz[0],xyz[1],xyz[2]
                        xyz[0] = Double.toString(centroid[0]);
                        xyz[1] = Double.toString(centroid[1]);
                        xyz[2] = Double.toString(centroid[2]);
                        sendText(xyz[0],xyz[1],xyz[2]);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                upload_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot dbSnap: dataSnapshot.getChildren()){

                            data_download = new positionDetails();
                            java.util.HashMap h = (HashMap)dbSnap.getValue();
                            Log.d("data",""+dbSnap.getValue().getClass()+ "  "+ dbSnap.getValue());
                            Log.d("data",""+h.get("x").getClass()+" : "+h.get("y").getClass());

                            data_download.setX((double)(Long)h.get("x"));
                            data_download.setY(Double.parseDouble(h.get("y")+""));
                            data_download.setZ("");
                            data_download.setList((List)h.get("list"));
                            Log.d("data",h.get("list").getClass()+"");
                            //List<Map.Entry<String,Double>> lis = new ArrayList<Map.Entry<String, Double>>();
                            Log.d("data",data_download.getList().get(0)+"   ");

                            //data_download.setX(dbSnap.getValue().get("x"));
                            //Log.d("data_download",data_download.getX()+" : "+data_download.getY()+" ");
                            aux[0].add(data_download);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                return false;
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double X = Double.parseDouble(XEdit.getText().toString().trim());
                double Y = Double.parseDouble(YEdit.getText().toString().trim());
                List<Map.Entry<String, Double>> list = WifiStrength();

                String PosID = upload_ref.push().getKey();

                positionDetails pos = new positionDetails(X,Y,"",list);
                insertNewPosition(PosID,pos);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register GyroscopeObserver.
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister GyroscopeObserver.
        gyroscopeObserver.unregister();
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

    public void sendText(String xCoord, String yCoord, String zCoord){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.setPackage("com.whatsapp");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://interro.bang/pop/"+xCoord+"/"+yCoord+"/"+zCoord+"");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public List<Map.Entry<String, Double>> WifiStrength(){
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
                        Double level = (double)scan.level;
                        Log.d("frequency",scan.frequency + "");
                        //Other code
                        Log.d("pras", level + "");

                        Log.d("pras",scan.SSID);


                        wifiDetails.put(scan.BSSID,level);
                    }
                } else {
                    Log.d("pras", "inside else");
                }
            }
        }


        Set<Map.Entry<String, Double>> set = wifiDetails.entrySet();
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Double>>()
        {
            public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        for(Map.Entry<String, Double> entry:list){
            Log.d("Pras",entry.getKey()+" ==== "+entry.getValue());
        }

        return list;
    }

    public double calculateDistance(double levelInDb, double freqInMHz)    {
        return Math.pow(10,(27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(levelInDb))/20);
    }

    /*public Double calculateDistance(double pRssi, int pTxPower){
        if(pRssi == 0)
            return -1.0;

        Double ratio = pRssi*1.0/pTxPower;

        if(ratio<1.0){
            return Math.pow(ratio, 10);
        }else {
            return ((0.89976)*Math.pow(ratio,7.7095) + 0.111);
        }
    }*/

    public void insertNewPosition(String PosID, positionDetails pos){
        upload_ref.child(PosID).setValue(pos);
    }

    public static positionDetails calculateProximity(ArrayList<Map.Entry<String,Double>>user){

        positionDetails res = new positionDetails();
        int i=0;
        for(i=0;i<aux.length;i++){
            int len = aux[0].size();
            if(len>0){
                for(int j=0;j<len;j++){
                    if(aux[i].get(j).getList().get(j).getKey() == user.get(i).getKey())
                        aux[i+1].add(aux[i].get(j));
                }
            }
            else
                break;
        }
        if(aux[i-1].size() == 1)
        {
            Log.d("print",""+aux[i-1].get(0).getX()+":"+aux[i-1].get(0).getY());
            res.setX(aux[i-1].get(0).getX());
            res.setY(aux[i-1].get(0).getY());
            res.setZ("");
            res.setList(user);
        }
        else
        {
            int closest_elem = getClosestElem(aux[i-1],user,i);
            Log.d("print",aux[i-1].get(closest_elem).getX()+" : " +aux[i-1].get(closest_elem).getY());
            res.setX(aux[i-1].get(closest_elem).getX());
            res.setY(aux[i-1].get(closest_elem).getY());
            res.setZ("");
            res.setList(user);
        }



        return res;
    }

    public static int getClosestElem(ArrayList<positionDetails> aux, ArrayList<Map.Entry<String,Double>> user, int i){
        int m,n,minIndex;
        double min;
        min = aux.get(0).getList().get(0).getValue();
        minIndex = 0;

        for(n=0;n<i;n++)
        {
            for( m=0; m<3; m++)
            {
                if(Math.abs(aux.get(n).getList().get(m).getValue() - user.get(m).getValue())<min)
                {
                    min = Math.abs(aux.get(n).getList().get(m).getValue() - user.get(m).getValue());
                    minIndex = n;
                }
            }
        }

        return minIndex;
    }



}
