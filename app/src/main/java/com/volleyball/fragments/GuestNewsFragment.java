package com.volleyball.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.volleyball.R;
import com.volleyball.adapters.AllNewsAdapter;
import com.volleyball.adapters.GuestAllNewsAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllNewsPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestNewsFragment extends Fragment {
    View view;
    ListView list_view;
    ProgressDialog progressDialog;
    List<GetAllNewsPojo> al;


    public static GuestNewsFragment guestNewsFragment() {
        GuestNewsFragment fragment = new GuestNewsFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_l_m_news, container, false);
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
                    list_view.setAdapter(new GuestAllNewsAdapter(teams,getActivity()));
                }
            }
            @Override
            public void onFailure(Call<List<GetAllNewsPojo>> call, Throwable t) {
                pd.dismiss();
            }
        });
    }

}
