package com.volleyball.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.volleyball.R;
import com.volleyball.adapters.AllSeasonsAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllSeasonsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSeasonsActivity extends AppCompatActivity {
    ListView list_view;
    List<GetAllSeasonsPojo> allSeasons;
    Button btn_add_seasons;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_seasons);

        getSupportActionBar().setTitle("All Seasons");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_add_seasons=(Button)findViewById(R.id.btn_add_seasons);
        btn_add_seasons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllSeasonsActivity.this, CreateSeasonsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        list_view=(ListView)findViewById(R.id.list_view);
        allSeasons= new ArrayList<>();
        serverData();


    }
    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(AllSeasonsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<GetAllSeasonsPojo>> call = service.getseasons();
        call.enqueue(new Callback<List<GetAllSeasonsPojo>>() {
            @Override
            public void onResponse(Call<List<GetAllSeasonsPojo>> call, Response<List<GetAllSeasonsPojo>> response) {
                //Toast.makeText(GetAllSeasonsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(AllSeasonsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    allSeasons = response.body();
                    list_view.setAdapter(new AllSeasonsAdapter(allSeasons, AllSeasonsActivity.this));

                }
            }

            @Override
            public void onFailure(Call<List<GetAllSeasonsPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AllSeasonsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
