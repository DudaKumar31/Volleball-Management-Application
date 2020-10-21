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
import com.volleyball.adapters.AllNewsAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllNewsPojo;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TMNewsFragment extends Fragment {
    ListView list_view;
    ProgressDialog progressDialog;
    List<GetAllNewsPojo> al;
    View view;


    public static TMNewsFragment tmNewsFragment() {
        TMNewsFragment fragment = new TMNewsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_t_m_news, container, false);
        list_view=(ListView)view.findViewById(R.id.list_view);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("News");
        loadAllNews();

        return view;
    }
    ProgressDialog pd;
    private void loadAllNews(){
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Please wait,Data is being loaded.");
        pd.show();
        ApiService api = RetroClient.getApiService();
        Call<List<GetAllNewsPojo>> call = api.getnews();
        call.enqueue(new Callback<List<GetAllNewsPojo>>() {
            @Override
            public void onResponse(Call<List<GetAllNewsPojo>> call, Response<List<GetAllNewsPojo>> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    List<GetAllNewsPojo> teams=response.body();
                    list_view.setAdapter(new AllNewsAdapter(teams,getActivity()));
                }
            }
            @Override
            public void onFailure(Call<List<GetAllNewsPojo>> call, Throwable t) {
                pd.dismiss();
            }
        });
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
