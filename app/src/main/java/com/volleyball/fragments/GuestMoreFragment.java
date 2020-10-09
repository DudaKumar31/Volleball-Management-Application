package com.volleyball.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.volleyball.R;
import com.volleyball.activities.GetAllSeasonsActivity;

public class GuestMoreFragment extends Fragment {
    CardView card_browse_team,card_season;
    View view;


    public static GuestMoreFragment guestMoreFragment() {
        GuestMoreFragment fragment = new GuestMoreFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_guest_more, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("More");

        card_season=(CardView)view.findViewById(R.id.card_season);

        card_season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), GetAllSeasonsActivity.class));
            }
        });

        card_browse_team= (CardView)view.findViewById(R.id.card_browse_team);

        card_browse_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Coming soon",Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
