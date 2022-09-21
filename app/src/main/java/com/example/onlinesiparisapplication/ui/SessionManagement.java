package com.example.onlinesiparisapplication.ui;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        String SHARED_PREF_NAME = "session";
        String SESSION_KEY = "session_user_id";
        String SESSION_USER_KEY = "session_user_name";

        public SessionManagement(Context context){
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        public void saveSession(int id,String username){
            //save session of user whenever user is logged in
            editor.putInt(SESSION_KEY,id).commit();
            editor.putString(SESSION_USER_KEY,username).commit();
        }

        public int getSession() {
            //return user when session is saved
            return sharedPreferences.getInt(SESSION_KEY,-1);
        }
        public String getUserSession() {
            //return user whase session is saved
            return sharedPreferences.getString(SESSION_USER_KEY,"-1");
        }
        public void removeSession(){
            editor.putInt(SESSION_KEY,-1).commit();
            editor.putString(SESSION_USER_KEY,"-1").commit();
        }
    }