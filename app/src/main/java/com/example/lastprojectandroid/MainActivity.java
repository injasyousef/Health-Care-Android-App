package com.example.lastprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
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


public class MainActivity extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    private Student student;
    private String bzuID;
    private TextView txtPatiantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        BottomNavigationView bottomNavigationView
                = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        txtPatiantName=findViewById(R.id.txtPatiantName);
        bzuID=getIntent().getStringExtra("id");
        readUser(bzuID);


    }
    @Override
    protected void onResume() {
        super.onResume();
        txtPatiantName=findViewById(R.id.txtPatiantName);
        bzuID=getIntent().getStringExtra("id");
        readUser(bzuID);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            // Handle home navigation
            return true;
        } else if (item.getItemId() == R.id.add) {
            Intent intent = new Intent (MainActivity.this , ChooseDoctor.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);


            return true;
        } else if (item.getItemId() == R.id.user) {
            Intent intent = new Intent (MainActivity.this , UserProfilePage.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);

            return true;
        }
        return false;
    }
    public void Logoutbtn(View view) {
        Intent intent = new Intent (MainActivity.this , LogIn.class);
        startActivity (intent);
    }

    public void ambulancebtn(View view) {
        Intent intent = new Intent (MainActivity.this , AmbulancePage.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }
    public void appointmentsBtn(View view) {
        Intent intent = new Intent (MainActivity.this , appointments.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        intent.putExtra("id2", String.valueOf(student.getId()));
        startActivity (intent);
    }

    public void Contactusbtn(View view) {
        Intent intent = new Intent (MainActivity.this , contactUsPage.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }

    private void readUser(String loginId) {
        String url = "http://10.0.2.2:5000/student/" + loginId;

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

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
                                txtPatiantName.setText("مرحبا "+student.getUsername());
                            } else {
                                Toast.makeText(MainActivity.this, "invalid ID", Toast.LENGTH_SHORT).show();
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