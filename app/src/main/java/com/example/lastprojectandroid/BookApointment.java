package com.example.lastprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class BookApointment extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener{
    ListView lst_of_times;
    CustomAdapter adapter;

    private TextView doctorName,doctorMajor;

    private Student student;

    ArrayList<Time> itemList = new ArrayList<>();
    private String doctorID,studentBZUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_apointment);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.add);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        doctorName=findViewById(R.id.doctorName);
        doctorMajor=findViewById(R.id.doctorMajor);
        studentBZUID=getIntent().getStringExtra("id");

        doctorID=getIntent().getStringExtra("doctorID");
        if(doctorID.equals("2")){
            doctorName.setText("د. أحمد");
            doctorMajor.setText("طبيب عام");
        }else if(doctorID.equals("3")){
            doctorName.setText("د. محمد");
            doctorMajor.setText("أخصائي عيون");
        }else if(doctorID.equals("4")){
            doctorName.setText("المختبر");
            doctorMajor.setText("فحوصات مخبرية");
        }

        lst_of_times = findViewById(R.id.lst_of_times);



        lst_of_times.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(BookApointment.this, "Item clicked: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        readTimes(doctorID);
        readUser(studentBZUID);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent (BookApointment.this , MainActivity.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);
            return true;
        } else if (item.getItemId() == R.id.add) {
            Intent intent = new Intent (BookApointment.this , ChooseDoctor.class);
            startActivity (intent);

            return true;
        } else if (item.getItemId() == R.id.user) {
            Intent intent = new Intent (BookApointment.this , UserProfilePage.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);

            return true;
        }
        return false;
    }
    public void BackbtnBookApointment(View view) {
        Intent intent = new Intent (BookApointment.this , ChooseDoctor.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }

    public void BookAppointment(View view){
        String doctor_id=doctorID;
        String student_id=String.valueOf(student.getId());
        Date date = itemList.get(adapter.getSelectedPosition()).getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        String status="تم الحجز";
        addAppointment(student_id,doctor_id,formattedDate,status);
    }

    private void readTimes(String loginId) {
        String url = "http://10.0.2.2:5000/time/"+loginId;

        RequestQueue queue = Volley.newRequestQueue(BookApointment.this);

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
                                String timeId = jsonArray.getString(0);
                                String doctorId = jsonArray.getString(1);
                                String date= jsonArray.getString(2);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

                                Time time =new Time(Integer.parseInt(timeId),Integer.parseInt(doctorId), dateFormat.parse(date));
                                itemList.add(time);
                            }
                            adapter = new CustomAdapter(BookApointment.this, R.layout.item_list, itemList);
                            lst_of_times.setAdapter(adapter);

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

    private void addAppointment(String student_id, String doctor_id, String date, String status) {
        String url = "http://10.0.2.2:5000/create_appointment";

        RequestQueue queue = Volley.newRequestQueue(BookApointment.this);


        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("student_id", student_id);
            jsonParams.put("doctor_id", doctor_id);
            jsonParams.put("date", date);
            jsonParams.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String result = "";
                        try {
                            result = response.getString("result");
                            Toast.makeText(BookApointment.this, result, Toast.LENGTH_SHORT).show();
                            deleteTime(itemList.get(adapter.getSelectedPosition()).getTime_id());
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
        String url = "http://10.0.2.2:5000/student/" + loginId;

        RequestQueue queue = Volley.newRequestQueue(BookApointment.this);

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
                                Toast.makeText(BookApointment.this, "invalid ID", Toast.LENGTH_SHORT).show();
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

    private void deleteTime(int id) {
        String url = "http://10.0.2.2:5000/delete_time/" + id;

        RequestQueue queue = Volley.newRequestQueue(BookApointment.this);

        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        itemList.remove(adapter.getSelectedPosition());
                        adapter.clearSelection();
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookApointment.this, "Failed to delete time slot", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(request);
    }
}