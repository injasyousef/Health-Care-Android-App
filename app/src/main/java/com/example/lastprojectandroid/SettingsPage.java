package com.example.lastprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsPage extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener {
    SwitchCompat dark_light_switch;
    boolean theme; // 1 is dark and 0 is light
    SharedPreferences themePref;
    SharedPreferences.Editor themePrefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_settings_page);
        dark_light_switch = findViewById(R.id.dark_light_switch);
        BottomNavigationView bottomNavigationView
                = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.user);
        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        themePref = getSharedPreferences("Mode", Context.MODE_PRIVATE);
        theme = themePref.getBoolean("nightMode", false);


        dark_light_switch.setChecked(theme);


        dark_light_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update the theme based on the switch state
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            // Save the current theme state in SharedPreferences
            themePrefEditor = themePref.edit();
            themePrefEditor.putBoolean("nightMode", isChecked);
            themePrefEditor.apply();
        });


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent (SettingsPage.this , MainActivity.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);
            return true;
        } else if (item.getItemId() == R.id.add) {
            Intent intent = new Intent (SettingsPage.this , ChooseDoctor.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);


            return true;
        } else if (item.getItemId() == R.id.user) {
            Intent intent = new Intent (SettingsPage.this , UserProfilePage.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);

            return true;
        }
        return false;
    }

    public void BackBtnSetting(View view) {
        Intent intent = new Intent (SettingsPage.this , UserProfilePage.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }

    public void resetPassword(View view) {
        Intent intent = new Intent (SettingsPage.this , ChangPassword.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);

    }

    public void changePhotobtn(View view) {
        Intent intent = new Intent (SettingsPage.this , ChagePhoto.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }
}