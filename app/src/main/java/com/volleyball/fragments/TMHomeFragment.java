package com.volleyball.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.volleyball.R;
import com.volleyball.activities.LoginActivity;
import com.volleyball.activities.TMEditProfileActivity;


import java.util.ArrayList;
import java.util.List;

public class TMHomeFragment extends Fragment {
    ProgressDialog progressDialog;
    View view;

    public static TMHomeFragment tmHomeFragment() {
        TMHomeFragment fragment = new TMHomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_t_m_home, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        return view;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tm_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.myprofile:
                startActivity(new Intent(getContext(), TMEditProfileActivity.class));
                return true;

            case R.id.logout:
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
