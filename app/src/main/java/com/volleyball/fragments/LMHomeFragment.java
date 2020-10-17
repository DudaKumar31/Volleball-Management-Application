package com.volleyball.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.volleyball.R;
import com.volleyball.activities.AllManagersActivity;
import com.volleyball.activities.AllNewsActivity;
import com.volleyball.activities.AllScheduleActivity;
import com.volleyball.activities.AllSeasonsActivity;
import com.volleyball.activities.AllTeamsActivity;
import com.volleyball.activities.CreateManagersActivity;
import com.volleyball.activities.LoginActivity;

public class LMHomeFragment extends Fragment {
    CardView cd_create_seasons,cd_create_managers,cd_create_team,card_schedule,cd_manage_news;
    View view;


    public static LMHomeFragment homeFragment() {
        LMHomeFragment fragment = new LMHomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_lm_home, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");

        cd_create_seasons=(CardView)view.findViewById(R.id.cd_create_seasons);
        cd_create_managers=(CardView)view.findViewById(R.id.cd_create_managers);
        cd_create_team=(CardView)view.findViewById(R.id.cd_create_team);
        card_schedule=(CardView)view.findViewById(R.id.card_schedule);
        cd_manage_news=(CardView)view.findViewById(R.id.cd_manage_news);
        cd_create_seasons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), AllSeasonsActivity.class);
                startActivity(intent);
            }
        });
        cd_create_managers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), AllManagersActivity.class);
                startActivity(intent);
            }
        });

        cd_create_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), AllTeamsActivity.class);
                startActivity(intent);
            }
        });
        card_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), AllScheduleActivity.class);
                startActivity(intent);
            }
        });

        cd_manage_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getContext(), AllNewsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

