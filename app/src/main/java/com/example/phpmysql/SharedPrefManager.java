package com.example.phpmysql;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;

    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "mySharedPref";
    private static final String EMAIL_KEY = "email";
    private static final String USER_KEY = "username";
    private static final String ID_KEY = "id";

    private SharedPrefManager(Context context){
        mCtx = context;

    }
    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public boolean userLogin(int id, String username, String email){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ID_KEY, id);
        editor.putString(EMAIL_KEY, email);
        editor.putString(USER_KEY, username);
        editor.apply();
        return true;
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(USER_KEY, null)!= null){
            return true;
        }
        return false;
    }
    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public String getUserName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_KEY, "Username");
    }
    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL_KEY, "default@gmail.com");
    }
}
