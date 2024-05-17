package com.example.lastprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogIn extends AppCompatActivity {

    private EditText edtLoginId, edtLoginPassword;
    boolean theme; // 1 is dark and 0 is light
    SharedPreferences themePref;
    SharedPreferences.Editor themePrefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_log_in);
        edtLoginId = findViewById(R.id.txtLoginId);
        edtLoginPassword = findViewById(R.id.txtLoginPassword);
        themePref = getSharedPreferences("Mode", Context.MODE_PRIVATE);
        theme = themePref.getBoolean("nightMode", false);

        if(theme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }
    }

    public void btnLogin(View view) {
        String loginId = edtLoginId.getText().toString();
        String password = edtLoginPassword.getText().toString();

        loginUser(loginId, password);
    }
    public void sginInLogin(View view) {
        Intent intent = new Intent (LogIn.this , SignUp.class);
        startActivity (intent);
    }

    public void resetPasswordbtn(View view){
        Intent intent = new Intent (LogIn.this , VerfyPerson.class);
        startActivity (intent);
    }

    private void loginUser(String loginId, String password) {
        String url = "http://10.0.2.2:5000/student/" + loginId;

        RequestQueue queue = Volley.newRequestQueue(LogIn.this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = response;

                            String result = jsonArray.getString(0);

                            if (!result.equals(null)) {
                                String serverPassword = jsonArray.getString(4);

                                if (password.equals(serverPassword)) {
                                    Intent intent = new Intent(LogIn.this, home.class);
                                    intent.putExtra("id",jsonArray.getString(1));
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LogIn.this, "Invalid password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LogIn.this, "invalid ID", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                    }
                }
        );
        queue.add(request);
    }


}