package com.example.lastprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

public class ChagePhoto extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener{

    private Student student;
    private String bzuID;

    private String photoPath=null;
    private static final int PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_chage_photo);

        BottomNavigationView bottomNavigationView
                = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.user);
        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bzuID=getIntent().getStringExtra("id");
        readUser(bzuID);


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent (ChagePhoto.this , MainActivity.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);
            return true;
        } else if (item.getItemId() == R.id.add) {
            Intent intent = new Intent (ChagePhoto.this , ChooseDoctor.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);


            return true;
        } else if (item.getItemId() == R.id.user) {
            Intent intent = new Intent (ChagePhoto.this , UserProfilePage.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);

            return true;
        }
        return false;
    }

    public void changePhotoBtn(View view){
        updatePhoto(bzuID, photoPath);

    }
    public void BackbtnChangPhoto(View view) {
        Intent intent = new Intent (ChagePhoto.this , SettingsPage.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }
    public void PickPhotobtn(View view){
        openGallery();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            photoPath = String.valueOf(selectedImage);
        }
    }

    private void readUser(String loginId) {
        String url = "http://10.0.2.2:5000/student/" + loginId;

        RequestQueue queue = Volley.newRequestQueue(ChagePhoto.this);

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
                                Toast.makeText(ChagePhoto.this, "invalid ID", Toast.LENGTH_SHORT).show();
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

    private void updatePhoto(String loginId, String newPhoto) {
        String updatePasswordUrl = "http://10.0.2.2:5000/update_photo";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", loginId);
            jsonBody.put("new_password", newPhoto);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(ChagePhoto.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, updatePasswordUrl, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ChagePhoto.this, "Photo updated successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                        Toast.makeText(ChagePhoto.this, "Failed to update photo", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }
}