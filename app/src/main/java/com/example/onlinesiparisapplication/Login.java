package com.example.onlinesiparisapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onlinesiparisapplication.ui.LoginActivity;
import com.example.onlinesiparisapplication.ui.SessionManagement;
import com.example.onlinesiparisapplication.ui.SignupActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button btnSignIn,btnSignUp;
    private void checkSession(){
        //check if user logged in
        //if user is logged in ---> move to mainActivity
        SessionManagement sessionManagement = new SessionManagement(Login.this);
        int userID = sessionManagement.getSession();

        if(userID != -1){
            //user id logged in and so moved to mainActivity
            moveToMainoffline();
        }else {
            //do nothing
        }

    }
    private void moveToMainoffline() {
        Intent intent = new Intent(Login.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignIn=findViewById(R.id.btn_SignIn);
        btnSignUp=findViewById(R.id.btn_SignUP);

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        checkSession();
    }

    @Override
    public void onClick(View v) {
        int ID=v.getId();
        Intent i;
        switch (ID){
            case R.id.btn_SignIn:
                i =new Intent(this, LoginActivity.class);
                startActivity(i);
                break;

            case R.id.btn_SignUP:
                i=new Intent(this, SignupActivity.class);
                startActivity(i);
                break;
    }
}
}