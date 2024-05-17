package com.example.lastprojectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);
    }

    public void onClickMainPage(View view) {
        Intent intent = new Intent (home.this , MainActivity.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }
}