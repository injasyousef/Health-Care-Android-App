package com.example.lastprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class contactUsPage extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_contact_us_page);

        BottomNavigationView bottomNavigationView
                = findViewById(R.id.bottom_navigation);


        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent (contactUsPage.this , MainActivity.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);
            return true;
        } else if (item.getItemId() == R.id.add) {
            Intent intent = new Intent (contactUsPage.this , ChooseDoctor.class);
            startActivity (intent);


            return true;
        } else if (item.getItemId() == R.id.user) {
            Intent intent = new Intent (contactUsPage.this , UserProfilePage.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            startActivity (intent);

            return true;
        }
        return false;
    }

    public void btnBackContactPage(View view) {
        Intent intent =new Intent (contactUsPage.this , MainActivity.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity (intent);
    }

    public void btnfacbooke(View view) {
            String url = "https://www.facebook.com/profile.php?id=100064674463194";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }

    public void btngoogle(View view) {
        String url = "https://www.birzeit.edu/ar/about/president-office/vps/vp-admin-financial/dyr-lkhdmt-ltby";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void btnritaj(View view) {
        String url = "https://ritaj.birzeit.edu/appointments/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

}
