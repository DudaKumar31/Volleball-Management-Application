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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.volleyball.R;
import com.volleyball.activities.LoginActivity;
import com.volleyball.activities.TMEditProfileActivity;
import com.volleyball.adapters.GetAllSeasonsAdapter;
import com.volleyball.adapters.GuestAllSeasonsAdapter;
import com.volleyball.adapters.GuestMatchesAdaper;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllSchedulePojo;
import com.volleyball.models.GetAllSeasonsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestHomeFragment extends Fragment {
    ListView list_view;
    List<GetAllSeasonsPojo> seasonPojos;
    View view;
    ProgressDialog progressDialog;

    public static GuestHomeFragment guestHomeFragment() {
        GuestHomeFragment fragment = new GuestHomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_guest_home, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Seasons");
        list_view=(ListView)view.findViewById(R.id.list_view);
        seasonPojos= new ArrayList<>();
        loadAllSeasons();
        return view;
    }
    ProgressDialog pd;
    private void loadAllSeasons(){
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Please wait,Data is being loaded.");
        pd.show();
        ApiService api = RetroClient.getApiService();
        Call<List<GetAllSeasonsPojo>> call = api.getseasons();
        call.enqueue(new Callback<List<GetAllSeasonsPojo>>() {
            @Override
            public void onResponse(Call<List<GetAllSeasonsPojo>> call, Response<List<GetAllSeasonsPojo>> response) {
                pd.dismiss();
                //Toast.makeText(GetAllSeasonsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(getActivity(),"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    seasonPojos = response.body();
                    //  Toast.makeText(getContext(), schedulePojos.toString(), Toast.LENGTH_SHORT).show();
                    list_view.setAdapter(new GuestAllSeasonsAdapter(seasonPojos, getActivity()));

                }
            }
            @Override
            public void onFailure(Call<List<GetAllSeasonsPojo>> call, Throwable t) {
                //pd.dismiss();
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
