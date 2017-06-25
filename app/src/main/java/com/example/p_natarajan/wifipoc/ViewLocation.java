package com.example.p_natarajan.wifipoc;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class ViewLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);

        Uri data = getIntent().getData();
        String scheme = data.getScheme(); // "http"
        String host = data.getHost(); // "twitter.com"
        List<String> params = data.getPathSegments();
        String first = params.get(0); // "status"
        String second = params.get(1); // "1234"
        String third = params.get(0); // "status"

        Toast.makeText(getApplicationContext(),first + " " + second + " " + third,Toast.LENGTH_SHORT).show();

    }
}
