package com.volleyball.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.volleyball.R;
import com.volleyball.fragments.LMHomeFragment;
import com.volleyball.fragments.LMMatchesFragment;
import com.volleyball.fragments.LMNewsFragment;
import com.volleyball.fragments.TMHomeFragment;
import com.volleyball.fragments.TMMatchesFragment;
import com.volleyball.fragments.TMNewsFragment;

public class TeamManagerDashBoardActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manager_dash_board);
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
                                selectedFragment = TMHomeFragment.tmHomeFragment();
                                break;
                            case R.id.item_matches:
                                selectedFragment = TMMatchesFragment.tmMatchesFragment();
                                break;
                            case R.id.item_news:
                                selectedFragment = TMNewsFragment.tmNewsFragment();
                                break;


                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, TMHomeFragment.tmHomeFragment());
        transaction.commit();
    }
}