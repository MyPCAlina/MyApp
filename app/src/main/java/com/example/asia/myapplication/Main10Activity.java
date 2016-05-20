package com.example.asia.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main10Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        String username = getIntent().getStringExtra("Username");

        TextView tv =(TextView)findViewById(R.id.textView32);
        tv.setText(username);

    }
}

