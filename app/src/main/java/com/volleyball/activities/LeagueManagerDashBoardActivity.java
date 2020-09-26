package com.volleyball.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.volleyball.R;
import com.volleyball.fragments.LMHomeFragment;
import com.volleyball.fragments.LMMatchesFragment;
import com.volleyball.fragments.LMNewsFragment;

public class LeagueManagerDashBoardActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_manager_dash_board);
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
                                selectedFragment = LMHomeFragment.homeFragment();
                                break;
                            case R.id.item_matches:
                                selectedFragment = LMMatchesFragment.matchesFragment();
                                break;
                            case R.id.item_news:
                                selectedFragment = LMNewsFragment.newsFragment();
                                break;

                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, LMHomeFragment.homeFragment());
        transaction.commit();
    }
}
