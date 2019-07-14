package com.example.phpmysql;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
        }
    }
    private void logout(){
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
