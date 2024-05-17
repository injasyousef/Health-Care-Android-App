package com.example.lastprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
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

import java.text.ParseException;

public class BookingDetailsPage extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener{

    Appointment appointment;
    String appointmentID;
    Doctor doctor;

    Student student;
    TextView appontmentTime,appontmentDate,appontmentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_booking_details_page);
        BottomNavigationView bottomNavigationView
                = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);

        appointmentID=getIntent().getStringExtra("appintmentID");
        appontmentTime=findViewById(R.id.appontmentTime);
        appontmentDate=findViewById(R.id.appontmentDate);
        appontmentStatus=findViewById(R.id.appontmentStatus);


        readAppointments(appointmentID);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent (BookingDetailsPage.this , MainActivity.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);
            return true;
        } else if (item.getItemId() == R.id.add) {
            Intent intent = new Intent (BookingDetailsPage.this , ChooseDoctor.class);
            startActivity (intent);


            return true;
        } else if (item.getItemId() == R.id.user) {
            Intent intent = new Intent (BookingDetailsPage.this , UserProfilePage.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);

            return true;
        }
        return false;
    }
    public void BackbtnDetails(View view) {
        Intent intent = new Intent (BookingDetailsPage.this , appointments.class);
        intent.putExtra("id2", getIntent().getStringExtra("id2"));
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }

    public void BackBtnSetting(View view) {
    }
    private void readAppointments(String loginId) {
        String url = "http://10.0.2.2:5000/appointmentID/"+loginId;

        RequestQueue queue = Volley.newRequestQueue(BookingDetailsPage.this);

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
                                String appointmentId = jsonArray.getString(0);
                                String studentId = jsonArray.getString(1);
                                String doctorId = jsonArray.getString(2);
                                String date = jsonArray.getString(3);
                                String status = jsonArray.getString(4);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

                                appointment = new Appointment(Integer.parseInt(appointmentId), Integer.parseInt(studentId), Integer.parseInt(doctorId),
                                        dateFormat.parse(date), status);

                                appontmentTime.setText(date);
                                appontmentStatus.setText(status);
                                readDoctor(doctorId);
                                readUser(studentId);

                            } else {
                                Toast.makeText(BookingDetailsPage.this, "invalid ID", Toast.LENGTH_SHORT).show();
                            }

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

    private void readDoctor(String loginId) {
        String url = "http://10.0.2.2:5000/doctor/" + loginId;

        RequestQueue queue = Volley.newRequestQueue(BookingDetailsPage.this);

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
                                appontmentDate.setText(jsonArray.getString(2));
                            } else {
                                Toast.makeText(BookingDetailsPage.this, "invalid ID", Toast.LENGTH_SHORT).show();
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
    private void readUser(String loginId) {
        String url = "http://10.0.2.2:5000/studentID/" + loginId;

        RequestQueue queue = Volley.newRequestQueue(BookingDetailsPage.this);

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
                                getIntent().putExtra("id",jsonArray.getString(1));

                            } else {
                                Toast.makeText(BookingDetailsPage.this, "invalid ID", Toast.LENGTH_SHORT).show();
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