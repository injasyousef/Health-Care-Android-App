package com.example.lastprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

public class UserProfilePage extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener{

    private Student student;
    private String bzuID;
    private TextView userName,userId;
    private ImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_user_profile_page);

        BottomNavigationView bottomNavigationView
                = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.user);
        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        userName=findViewById(R.id.userName);
        userId=findViewById(R.id.userId);
        profile_image=findViewById(R.id.profile_image);
        bzuID=getIntent().getStringExtra("id");
        readUser(bzuID);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent (UserProfilePage.this , MainActivity.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);
            return true;
        } else if (item.getItemId() == R.id.add) {
            Intent intent = new Intent (UserProfilePage.this , ChooseDoctor.class);
            startActivity (intent);


            return true;
        } else if (item.getItemId() == R.id.user) {


            return true;
        }
        return false;
    }

    private void readUser(String loginId) {
        String url = "http://10.0.2.2:5000/student/" + loginId;

        RequestQueue queue = Volley.newRequestQueue(UserProfilePage.this);

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
                                userName.setText(student.getUsername());
                                userId.setText(String.valueOf(student.getBzu_id()));
                                setImageFromPath(jsonArray.getString(5));
                            } else {
                                Toast.makeText(UserProfilePage.this, "invalid ID", Toast.LENGTH_SHORT).show();
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

    public void btnSetting(View view) {
        Intent intent = new Intent (UserProfilePage.this ,SettingsPage.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }

    private void setImageFromPath(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            Log.d("Path", String.valueOf(imagePath.length()));
            Uri imageUri = Uri.parse(imagePath);
            Glide.with(UserProfilePage.this)
                    .load(imageUri)
                    .into(profile_image);
        } else {
            Log.e("UserProfilePage", "Empty or null image path provided.");
        }
    }



}