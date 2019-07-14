package com.example.phpmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button loginButton;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, Profile.class));
            return;
        }
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading >>>");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }
    private void userLogin(){
        final String userName = username.getText().toString().trim();
        final String passWord = password.getText().toString().trim();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(!obj.getBoolean("error")){
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(obj.getInt("id"), obj.getString("username"), obj.getString("email"));
                        Toast.makeText(getApplicationContext(), "user login successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        finish();

                    }else{
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("username", userName);
                params.put("password", passWord);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
