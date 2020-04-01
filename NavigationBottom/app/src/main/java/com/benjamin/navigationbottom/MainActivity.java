package com.benjamin.navigationbottom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.itemOne);
        badgeDrawable.setVisible(true);
    }
}
