package com.example.onlinesiparisapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlinesiparisapplication.MainActivity;
import com.example.onlinesiparisapplication.R;

public class SignupActivity extends AppCompatActivity {
    EditText etRegisterUN,etRegisterPass,etConfirm;
    Button btnRegister,signin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etRegisterUN=(EditText) findViewById(R.id.et_RegisterUN);
        etRegisterPass=(EditText) findViewById(R.id.et_RegisterPass);
        etConfirm=(EditText) findViewById(R.id.et_RegisterConfirm);
        btnRegister=(Button) findViewById(R.id.btn_Register);
        signin =(Button) findViewById(R.id.btnsingin);
        DB = new DBHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String user = etRegisterUN.getText().toString();
                String pass = etRegisterPass.getText().toString();
                String repass = etConfirm.getText().toString();

                if(user.equals("") || pass.equals("") || repass.equals(""))
                    Toast.makeText(SignupActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else {
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(!checkuser){
                            Boolean insert = DB.insertData(user, pass);
                            if(insert){
                                Toast.makeText(SignupActivity.this, "Registered succcessfully", Toast.LENGTH_SHORT).show();
                                saveinfo(user);
                                moveToMainoffline();
                            }else {
                                Toast.makeText(SignupActivity.this,"Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignupActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignupActivity.this,"Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void saveinfo(String user){

        int id = DB.getUserId(user);
        SessionManagement sessionManagement = new SessionManagement(SignupActivity.this);
        sessionManagement.saveSession(id,user);

    }
    private void moveToMainoffline() {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}