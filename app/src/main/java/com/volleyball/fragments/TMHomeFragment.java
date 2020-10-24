package com.volleyball.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.volleyball.R;
import com.volleyball.activities.LoginActivity;
import com.volleyball.activities.TMEditProfileActivity;
import com.volleyball.activities.TeamManagerAddPlayerActivity;
import com.volleyball.activities.Utils;


import java.util.ArrayList;
import java.util.List;

public class TMHomeFragment extends Fragment {
    ProgressDialog progressDialog;
    View view;
    String session;
    Button btn_add_player;
    TextView tv_team_name;
    ImageView team_logo;
    SharedPreferences sharedPreferences;
    ListView list_view;

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

        list_view = (ListView) view.findViewById(R.id.list_view);
        tv_team_name=(TextView)view.findViewById(R.id.tv_team_name);
        team_logo=(ImageView)view.findViewById(R.id.team_logo);


        sharedPreferences = getActivity().getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("team_id", "def-val");

        btn_add_player=(Button)view.findViewById(R.id.btn_add_player);
        btn_add_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(no_of_players<=5) {
                    Intent intent=new Intent(getActivity(), TeamManagerAddPlayerActivity.class);
                    intent.putExtra("teamid",session);
                    intent.putExtra("teamname",tv_team_name.getText().toString());
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(),"Team is full.",Toast.LENGTH_SHORT).show();
                }




                //finish();
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
