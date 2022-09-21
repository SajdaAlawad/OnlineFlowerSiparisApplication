package com.example.onlinesiparisapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.onlinesiparisapplication.model.OnlineSiparisModel;

public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Receipt Application");

        OnlineSiparisModel onlineSiparisModel = getIntent().getParcelableExtra("OnlineSiparisModel");
        ActionBar actionBarr = getSupportActionBar();
        actionBarr.setTitle(onlineSiparisModel.getName());
        //actionBar.setSubtitle(onlineSiparisModel.getAddress());
        actionBarr.setDisplayHomeAsUpEnabled(false);

        TextView buttonDone = findViewById(R.id.buttonDone);
        //when i click to button will close this page
        buttonDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}