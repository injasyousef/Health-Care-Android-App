package com.example.lastprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;

public class ChooseDoctor extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener{

    private String ID;
    private Student student;
    private TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_choose_doctor2);
        BottomNavigationView bottomNavigationView
                = findViewById(R.id.bottom_navigation);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.add);
        ID=getIntent().getStringExtra("id");
        txtWelcome=findViewById(R.id.txtWelcome);
        readUser(ID);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent (ChooseDoctor.this , MainActivity.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);
            return true;
        } else if (item.getItemId() == R.id.add) {

            return true;
        } else if (item.getItemId() == R.id.user) {
            Intent intent = new Intent (ChooseDoctor.this , UserProfilePage.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);

            return true;
        }
        return false;
    }

    public void GenaralDocbtn(View view) {
        Intent intent = new Intent (ChooseDoctor.this , BookApointment.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        intent.putExtra("doctorID", String.valueOf(2));
        startActivity (intent);
    }

    public void EyeDocbtn(View view) {
        Intent intent = new Intent (ChooseDoctor.this , BookApointment.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        intent.putExtra("doctorID", String.valueOf(3));
        startActivity (intent);
    }

    public void LabDoctor(View view) {
        Intent intent = new Intent (ChooseDoctor.this , BookApointment.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        intent.putExtra("doctorID", String.valueOf(4));
        startActivity (intent);
    }
    private void readUser(String loginId) {
        String url = "http://10.0.2.2:5000/student/" + loginId;

        RequestQueue queue = Volley.newRequestQueue(ChooseDoctor.this);

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
                                txtWelcome.setText("مرحبا "+student.getUsername());
                            } else {
                                Toast.makeText(ChooseDoctor.this, "invalid ID", Toast.LENGTH_SHORT).show();
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