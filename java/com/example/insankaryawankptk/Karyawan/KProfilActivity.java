package com.example.insankaryawankptk.Karyawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.insankaryawankptk.Karyawan.Fragment_Profile.MyProfileFragment;
import com.example.insankaryawankptk.Karyawan.Fragment_Profile.OtherProfileFragment;
import com.example.insankaryawankptk.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;


public class KProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        BottomNavigationView bottomNav = findViewById(R.id.btn_nav_prof);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MyProfileFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new Fragment();
                    switch (item.getItemId()) {
                        case R.id.my_profile:
                            selectedFragment = new MyProfileFragment();
                            break;
                        case R.id.other_profile:
                            selectedFragment = new OtherProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
