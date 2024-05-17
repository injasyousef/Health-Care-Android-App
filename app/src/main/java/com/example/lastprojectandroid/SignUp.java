package com.example.lastprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {

    private EditText txt_signup_id,txt_signup_name,txt_signup_password,txt_signup_confirmpassword;
    private String photoPath=null,email;
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sign_up);
        txt_signup_id=findViewById(R.id.txt_signup_id);
        txt_signup_name=findViewById(R.id.txt_signup_name);
        txt_signup_password=findViewById(R.id.txt_signup_password);
        txt_signup_confirmpassword=findViewById(R.id.txt_signup_confirmpassword);
        email=txt_signup_id.getText().toString()+"@student.birzeit.edu";
    }

    public void onClickLoginPage(View view) {
        Intent intent = new Intent (SignUp.this , LogIn.class);
        startActivity (intent);
    }
    public void btnSginin(View view) {
        String password =txt_signup_password.getText().toString();
        int check =CheckPassword(password);

        if(txt_signup_id.getText ().toString ().equals ("")){
            Toast.makeText (this, "Enter Student ID", Toast.LENGTH_SHORT).show ();
        } else if (txt_signup_name.getText ().toString ().equals ("")) {
            Toast.makeText (this, "Enter Student Name", Toast.LENGTH_SHORT).show ();
        }else if(! password.equals(txt_signup_confirmpassword.getText().toString())) {
            Toast.makeText (this, "Passwords mismatch", Toast.LENGTH_SHORT).show ();
        }else if(check == 1){
            Toast.makeText(this, "Password must be greater than 8 digits", Toast.LENGTH_SHORT).show();
        }else if(check == 2){
            Toast.makeText(this, "Passwords must contain lowercase letters ", Toast.LENGTH_SHORT).show();
        }else if(check == 3){
            Toast.makeText(this, "Passwords must contain UpperCase letters ", Toast.LENGTH_SHORT).show();
        }else if(check == 4){
            Toast.makeText(this, "Passwords must contain Number ", Toast.LENGTH_SHORT).show();
        }else if(check == 5){
            Toast.makeText(this, "Passwords must contain Special characters ", Toast.LENGTH_SHORT).show();
        }
        else {
            addStudent(txt_signup_id.getText().toString(), txt_signup_name.getText().toString(),
                    txt_signup_id.getText().toString()+"@student.birzeit.edu", txt_signup_password.getText().toString(), photoPath);
        }
    }

    private int CheckPassword(String password) {
        if(password.length() < 8){
            return 1;
        }
        if(!password.matches(".*[a-z].*")) {
            return 2;
        }

        if(!password.matches(".*[A-Z].*")) {
            return 3;
        }

        if(!password.matches(".*\\d.*")) {
            return 4;
        }

        if(!password.matches(".*[@#$%^&+=!].*")) {
            return 5;
        }
        return 6;
    }

    public void onClickPhoto(View view) {
        openGallery();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE);
    }


    private void addStudent(String studentId, String username, String email, String password, String photo) {
        String url = "http://10.0.2.2:5000/create_student";

        RequestQueue queue = Volley.newRequestQueue(SignUp.this);

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("student_id", studentId);
            jsonParams.put("username", username);
            jsonParams.put("email", email);
            jsonParams.put("password", password);
            jsonParams.put("photo", photo);
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(SignUp.this, result,
                                Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            photoPath = String.valueOf(selectedImage);
        }
    }

}