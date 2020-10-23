package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.volleyball.R;
import com.volleyball.adapters.GuestMatchesAdaper;

import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllSchedulePojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulebySeasonActivity extends AppCompatActivity {
    ListView list_view;
    List<GetAllSchedulePojo> getSeasonTeamsPojos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduleby_season);

        getSupportActionBar().setTitle("Schedule");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);
        getSeasonTeamsPojos= new ArrayList<>();
        serverData();
    }
    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(SchedulebySeasonActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<GetAllSchedulePojo>> call = service.getschedulebyseason(getIntent().getStringExtra("id"));
        call.enqueue(new Callback<List<GetAllSchedulePojo>>() {
            @Override
            public void onResponse(Call<List<GetAllSchedulePojo>> call, Response<List<GetAllSchedulePojo>> response) {
                //Toast.makeText(GetAllSeasonsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null && response.body().size()==0){
                    Toast.makeText(SchedulebySeasonActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }

                else {
                    getSeasonTeamsPojos = response.body();
                    list_view.setAdapter(new GuestMatchesAdaper(getSeasonTeamsPojos, SchedulebySeasonActivity.this));

                }
            }

            @Override
            public void onFailure(Call<List<GetAllSchedulePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SchedulebySeasonActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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