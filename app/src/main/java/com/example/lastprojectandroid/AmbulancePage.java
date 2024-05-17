package com.example.lastprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AmbulancePage extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_ambulance_page);
        BottomNavigationView bottomNavigationView
                = findViewById(R.id.bottom_navigation);


        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent (AmbulancePage.this , MainActivity.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);
            return true;
        } else if (item.getItemId() == R.id.add) {
            Intent intent = new Intent (AmbulancePage.this , ChooseDoctor.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);


            return true;
        } else if (item.getItemId() == R.id.user) {
            Intent intent = new Intent (AmbulancePage.this , UserProfilePage.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);

            return true;
        }
        return false;
    }
    public void BackbtnAmb(View view) {
        Intent intent = new Intent (AmbulancePage.this , MainActivity.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }

}