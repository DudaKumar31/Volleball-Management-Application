package com.volleyball.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.volleyball.R;
import com.volleyball.adapters.AllTeamsAdapter;
import com.volleyball.adapters.GuestAllTeamsAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllTeamsPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestAllTeamsActivity extends AppCompatActivity {
    ListView list_view;
    List<GetAllTeamsPojo> getAllTeams;
    EditText et_search;
    GuestAllTeamsAdapter guestAllTeamsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_all_teams);

        getSupportActionBar().setTitle("All Teams");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);
        getAllTeams= new ArrayList<>();
        serverData();
        teamsSearch();




    }
    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(GuestAllTeamsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<GetAllTeamsPojo>> call = service.getteams();
        call.enqueue(new Callback<List<GetAllTeamsPojo>>() {
            @Override
            public void onResponse(Call<List<GetAllTeamsPojo>> call, Response<List<GetAllTeamsPojo>> response) {
                //Toast.makeText(GetAllTeamsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(GuestAllTeamsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    getAllTeams = response.body();
                    guestAllTeamsAdapter = new GuestAllTeamsAdapter(getAllTeams, GuestAllTeamsActivity.this);
                    list_view.setAdapter(guestAllTeamsAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<GetAllTeamsPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GuestAllTeamsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void teamsSearch(){
        et_search = (EditText)findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = et_search.getText().toString().toLowerCase(Locale.getDefault());
                guestAllTeamsAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
