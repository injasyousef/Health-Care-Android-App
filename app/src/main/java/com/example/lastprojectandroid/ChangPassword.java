package com.example.lastprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangPassword extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener{

    private Student student;
    private String bzuID;
    private TextView txtOldPass,txtNewPass,txtConfirmNewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_chang_password);
        BottomNavigationView bottomNavigationView
                = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.user);
        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bzuID=getIntent().getStringExtra("id");
        readUser(bzuID);

        txtOldPass=findViewById(R.id.txtOldPass);
        txtNewPass=findViewById(R.id.txtNewPass);
        txtConfirmNewPass=findViewById(R.id.txtConfirmNewPass);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent (ChangPassword.this , MainActivity.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);
            return true;
        } else if (item.getItemId() == R.id.add) {
            Intent intent = new Intent (ChangPassword.this , ChooseDoctor.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);


            return true;
        } else if (item.getItemId() == R.id.user) {
            Intent intent = new Intent (ChangPassword.this , UserProfilePage.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);

            return true;
        }
        return false;
    }

    public void changePassbtn(View view){
        if(student.getPassword().equals(txtOldPass.getText().toString())){

            if(txtNewPass.getText().toString().equals(txtConfirmNewPass.getText().toString())){
                updatePassword(bzuID, txtNewPass.getText().toString());
            }else{
                Toast.makeText(ChangPassword.this, "Passwords Miss Match", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(ChangPassword.this, "The Old Password is Wrong", Toast.LENGTH_SHORT).show();

        }
    }
    public void BackbtnChangPasswod(View view) {
        Intent intent = new Intent (ChangPassword.this , SettingsPage.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }

    private void readUser(String loginId) {
        String url = "http://10.0.2.2:5000/student/" + loginId;

        RequestQueue queue = Volley.newRequestQueue(ChangPassword.this);

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
                                student=new Student(Integer.parseInt(jsonArray.getString(0)),Integer.parseInt(jsonArray.getString(1)),jsonArray.getString(2),jsonArray.getString(3),jsonArray.getString(4),jsonArray.getString(5));
                            } else {
                                Toast.makeText(ChangPassword.this, "invalid ID", Toast.LENGTH_SHORT).show();
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

    private void updatePassword(String loginId, String newPassword) {
        String updatePasswordUrl = "http://10.0.2.2:5000/update_password";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", loginId);
            jsonBody.put("new_password", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(ChangPassword.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, updatePasswordUrl, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ChangPassword.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                        Toast.makeText(ChangPassword.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }

}