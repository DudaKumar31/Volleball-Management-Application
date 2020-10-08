package com.volleyball.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.volleyball.CreateTeamActivity;
import com.volleyball.R;
import com.volleyball.activities.AllSeasonsActivity;
import com.volleyball.activities.AllTeamsActivity;
import com.volleyball.activities.CreateManagersActivity;
import com.volleyball.activities.CreateSeasonsActivity;
import com.volleyball.activities.LoginActivity;

public class LMHomeFragment extends Fragment {
    CardView cd_create_seasons,cd_create_managers,cd_create_team;
    View view;
    Button btn_logout;


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
        btn_logout=(Button)view.findViewById(R.id.btn_logout) ;
        cd_create_team=(CardView)view.findViewById(R.id.cd_create_team);
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

                Intent intent=new Intent(getContext(), CreateManagersActivity.class);
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

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}

