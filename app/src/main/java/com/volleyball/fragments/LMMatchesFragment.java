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
import com.volleyball.adapters.GuestMatchesAdaper;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllSchedulePojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LMMatchesFragment extends Fragment {
    ListView list_view;
    List<GetAllSchedulePojo> schedulePojos;
    View view;
    ProgressDialog progressDialog;


    public static LMMatchesFragment matchesFragment() {
        LMMatchesFragment fragment = new LMMatchesFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_l_m_matches, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Matches");
        list_view=(ListView)view.findViewById(R.id.list_view);
        schedulePojos= new ArrayList<>();
        loadAllMatches();
        return view;
    }

    ProgressDialog pd;
    private void loadAllMatches(){
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Please wait,Data is being loaded.");
        pd.show();
        ApiService api = RetroClient.getApiService();
        Call<List<GetAllSchedulePojo>> call = api.getMatchResults();
        call.enqueue(new Callback<List<GetAllSchedulePojo>>() {
            @Override
            public void onResponse(Call<List<GetAllSchedulePojo>> call, Response<List<GetAllSchedulePojo>> response) {
                pd.dismiss();
                //Toast.makeText(GetAllSeasonsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(getActivity(),"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    schedulePojos = response.body();
                    //  Toast.makeText(getContext(), schedulePojos.toString(), Toast.LENGTH_SHORT).show();
                    list_view.setAdapter(new GuestMatchesAdaper(schedulePojos, getActivity()));

                }
            }
            @Override
            public void onFailure(Call<List<GetAllSchedulePojo>> call, Throwable t) {
                //pd.dismiss();
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

            }
        });
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
