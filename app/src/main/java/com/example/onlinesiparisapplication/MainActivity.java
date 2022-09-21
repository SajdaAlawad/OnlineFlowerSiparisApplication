package com.example.onlinesiparisapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.onlinesiparisapplication.adaptor.FlowerListAdapter;
import com.example.onlinesiparisapplication.model.OnlineSiparisModel;
import com.example.onlinesiparisapplication.ui.SessionManagement;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FlowerListAdapter.OnlineSiparisListClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Flower List");

        List<OnlineSiparisModel> onlineSiparisModelList = getFlowerData();

        initRecyclerView(onlineSiparisModelList);
    }
    private void initRecyclerView(List<OnlineSiparisModel> onlineSiparisModelList){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FlowerListAdapter adapter = new FlowerListAdapter(onlineSiparisModelList,this);
        recyclerView.setAdapter(adapter);
    }


    private List<OnlineSiparisModel> getFlowerData(){
        InputStream is = getResources().openRawResource(R.raw.file);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while((n= reader.read(buffer)) != -1){
                writer.write(buffer, 0,n);
            }
        }catch (Exception e){

        }
        String jsonStr = writer.toString();
        Gson gson = new Gson();
        OnlineSiparisModel[] onlineSiparisModels = gson.fromJson(jsonStr, OnlineSiparisModel[].class);
        List<OnlineSiparisModel> flowerList = Arrays.asList(onlineSiparisModels);

        return flowerList;
    }

    @Override
    public void onItemClick(OnlineSiparisModel onlineSiparisModel) {
        Intent intent = new Intent(MainActivity.this,FlowerMenuActivity.class);
        intent.putExtra("OnlineSiparisModel",onlineSiparisModel);
        startActivity(intent);
    }
    public void Logout(View view) {
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.removeSession();
        moveToLogin();
    }
    //after do logout move to login page
    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}