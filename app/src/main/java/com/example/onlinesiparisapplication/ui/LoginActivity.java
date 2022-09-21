package com.example.onlinesiparisapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinesiparisapplication.MainActivity;
import com.example.onlinesiparisapplication.R;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button btnLogin;
    TextView tvRegister;
    DBHelper DB;
    private static final String usern = "Username";
    private static final int id =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        username=(EditText) findViewById(R.id.username1);
        password=(EditText)findViewById(R.id.et_LoginPass);
        btnLogin=(Button) findViewById(R.id.btn_Login);
        tvRegister=(TextView) findViewById(R.id.tv_Register);
        DB = new DBHelper(this);


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkSession();
    }
    private void checkSession(){
        //check if user logged in
        //if user is logged in ---> move to mainActivity
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userID = sessionManagement.getSession();

        if(userID != -1){
            //user id logged in and so moved to mainActivity
            moveToMainoffline();
        }else {
            //do nothing
        }
    }

    private void moveToMainoffline() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void saveinfo(String user){
        int id = DB.getUserId(user);
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        sessionManagement.saveSession(id,user);
    }

    public void Login(View view) {
        //1.log in to app and save session of user
        //2.move to mainActivity
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if(user.equals("") ||pass.equals(""))
            Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
        else {
            Boolean checkuserpass = DB.checkusernamepassword(user, pass);
            if (checkuserpass == true) {
                Toast.makeText(LoginActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                saveinfo(user);
                moveToMainoffline();

            } else {
                Toast.makeText(LoginActivity.this, "Invalid Credenti", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

