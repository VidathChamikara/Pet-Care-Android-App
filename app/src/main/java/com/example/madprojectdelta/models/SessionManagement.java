package com.example.madprojectdelta.models;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManagement(Context context)
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(Admin user){
        //save session of user
        String username = user.getUsername();
        editor.putString(SESSION_KEY, username).commit();

    }

    public String getSession() {
        //return user who's session is saved
        return sharedPreferences.getString(SESSION_KEY, "E");
    }

    public void removeSession() {
        //assign 0 as user is not logged in
        editor.putString(SESSION_KEY, "E").commit();
    }

}