package com.example.course_work_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    AddFragment addFragment = new AddFragment();
    SearchFragment searchFragment = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        placeFragment(addFragment);
        boolean showHomeFragment = getIntent().getBooleanExtra("showHomeFragment", false);

        if (showHomeFragment) {
            // If the extra is set, show the HomeFragment
            placeFragment(homeFragment);
            bottomNavigationView.setSelectedItemId(R.id.home_nav); // Set the selected item to Home
        } else {
            // Otherwise, default to AddFragment
            placeFragment(addFragment);
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.add_nav) {
                    placeFragment(addFragment);
                } else if (itemId == R.id.home_nav) {
                    placeFragment(homeFragment);
                }
                else if (itemId == R.id.search_nav) {
                    placeFragment(searchFragment);
                }
                return true;
            }
        });
    }
    private void placeFragment (Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
    }
}