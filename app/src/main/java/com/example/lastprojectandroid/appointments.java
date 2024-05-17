package com.example.lastprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class appointments extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener{

    private String stdID;
    private ArrayList<Appointment> appointments=new ArrayList<>();
    private Map<String,String> doctors=new HashMap<>();

    AppointmentsAdapter adapter;
    private String ID;
    private RecyclerView recyclerViewAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_appointments);
        BottomNavigationView bottomNavigationView
                = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        stdID=getIntent().getStringExtra("id2");

        recyclerViewAppointments = findViewById(R.id.recyclerViewAppointments);
        recyclerViewAppointments.setLayoutManager(new LinearLayoutManager(this));


        // Retrieve appointments and doctors data
        readAppointments(stdID);
        readDoctors();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent (appointments.this , MainActivity.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));

            startActivity (intent);
            return true;
        } else if (item.getItemId() == R.id.add) {
            Intent intent = new Intent (appointments.this , ChooseDoctor.class);
            startActivity (intent);


            return true;
        } else if (item.getItemId() == R.id.user) {
            Intent intent = new Intent (appointments.this , UserProfilePage.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));

            startActivity (intent);

            return true;
        }
        return false;
    }
    public void Backbtnappointments(View view) {
        Intent intent = new Intent (appointments.this , MainActivity.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }

    private void readAppointments(String loginId) {
        String url = "http://10.0.2.2:5000/appointment/"+loginId;

        RequestQueue queue = Volley.newRequestQueue(appointments.this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONArray jsonArray = response.getJSONArray(i);
                                String appointmentId = jsonArray.getString(0);
                                String studentId = jsonArray.getString(1);
                                String doctorId = jsonArray.getString(2);
                                String date = jsonArray.getString(3);
                                String status = jsonArray.getString(4);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

                                Appointment appointment = new Appointment(Integer.parseInt(appointmentId), Integer.parseInt(studentId), Integer.parseInt(doctorId),
                                        dateFormat.parse(date), status);
                                appointments.add(appointment);
                            }

                             adapter = new AppointmentsAdapter(appointments, doctors);
                            recyclerViewAppointments.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            // Now appointments ArrayList contains all the appointments
                            // You can use the ArrayList for further processing or display

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
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

    private void readDoctors() {
        String url = "http://10.0.2.2:5000/doctors";

        RequestQueue queue = Volley.newRequestQueue(appointments.this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONArray jsonArray = response.getJSONArray(i);
                                String id = jsonArray.getString(0);
                                String docID = jsonArray.getString(1);
                                String username = jsonArray.getString(2);

                                doctors.put(id,username);
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

    public void Clear(View view){
        deleteAppointment(stdID);
    }

    private void deleteAppointment(String id) {
        String url = "http://10.0.2.2:5000/delete_appointment/" + id;

        RequestQueue queue = Volley.newRequestQueue(appointments.this);

        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        appointments.clear();
                        adapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors
                        Toast.makeText(appointments.this, "Failed to delete appointment", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(request);
    }

}