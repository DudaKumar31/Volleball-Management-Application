package com.volleyball.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.volleyball.R;
import com.volleyball.fragments.GuestMoreFragment;
import com.volleyball.fragments.GuestHomeFragment;
import com.volleyball.fragments.GuestMatchesFragment;
import com.volleyball.fragments.GuestNewsFragment;

public class UserDashBoardActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);
        bottomNavigation();
    }
    private void bottomNavigation() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setSelectedItemId(R.id.item_home);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.item_home:
                                selectedFragment = GuestHomeFragment.guestHomeFragment();
                                break;
                            case R.id.item_matches:
                                selectedFragment = GuestMatchesFragment.guestMatchesFragment();
                                break;
                            case R.id.item_news:
                                selectedFragment = GuestNewsFragment.guestNewsFragment();
                                break;
                            case R.id.item_settings:
                                selectedFragment = GuestMoreFragment.guestMoreFragment();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, GuestHomeFragment.guestHomeFragment());
        transaction.commit();
    }
}