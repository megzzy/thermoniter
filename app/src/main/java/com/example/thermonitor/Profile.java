package com.example.thermonitor;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
//
//
public class Profile extends AppCompatActivity implements View.OnClickListener {
    //

    int images=R.drawable.esp;

//
    private FirebaseAuth firebaseAuth;
    private TextView textViewUserE;
    private Button buttonLogOut;
    private WifiManager wifiManager;
    private List<ScanResult> results;
    private Button buttonSCN;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> mac = new ArrayList<>();
    private int size = 0;
    private ListView listView;
    private CustomAdapter adapter;

    @Override
//
    protected void onCreate(Bundle savedInstanceState) {
//
        super.onCreate(savedInstanceState);
//
        setContentView(R.layout.activity_profile);
        textViewUserE = (TextView) findViewById(R.id.textViewUserE);

        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);

        buttonLogOut.setOnClickListener(this);


        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewUserE.setText("Welcome \n" + user.getEmail());
//
        listView = findViewById(R.id.wifiList);
//
         adapter = new CustomAdapter();
//
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent0=new Intent(view.getContext(),tempread.class);
                startActivity(intent0);

            }
        });

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "Wifi is disabled ... Please turn it on", Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
        }
        buttonSCN = (Button) findViewById(R.id.buttonSCN);
        buttonSCN.setOnClickListener(this);

        listView.setAdapter(adapter);
        scanWifi();



    }

    private void scanWifi() {
        names.clear();
        registerReceiver(wifiReceiver, new IntentFilter(wifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        Toast.makeText(this, "Scanning for WIFI ...", Toast.LENGTH_SHORT).show();
    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            results = wifiManager.getScanResults();
            unregisterReceiver(this);
            for (ScanResult scanResult : results) {
                if(scanResult.SSID.equals("ESP8266")) {
                    names.add(scanResult.SSID);
                    mac.add((scanResult.BSSID));
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v == buttonLogOut) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, Login.class));
        }
        if (v == buttonSCN)
            scanWifi();

    }

    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlayout, null);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            TextView textView   = (TextView) convertView.findViewById(R.id.textView);
            TextView textView4  = (TextView) convertView.findViewById(R.id.textView4);
            imageView.setImageResource(images);
            textView.setText(names.get(position));
            textView4.setText(mac.get(position));
            return convertView;
        }
    }

}