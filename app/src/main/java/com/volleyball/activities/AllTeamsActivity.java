package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.volleyball.R;
import com.volleyball.adapters.AllTeamsAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllTeamsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTeamsActivity extends AppCompatActivity {
    ListView list_view;
    Button btn_add_teams;
    List<GetAllTeamsPojo> getAllTeams;
    GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_teams);
        getSupportActionBar().setTitle("All Teams");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_add_teams=(Button)findViewById(R.id.btn_add_teams);
        btn_add_teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllTeamsActivity.this,CreateTeamActivity.class);
                startActivity(intent);
                finish();
            }
        });
        list_view=(ListView)findViewById(R.id.list_view);
        getAllTeams= new ArrayList<>();
        serverData();


    }
    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(AllTeamsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<GetAllTeamsPojo>> call = service.getteams();
        call.enqueue(new Callback<List<GetAllTeamsPojo>>() {
            @Override
            public void onResponse(Call<List<GetAllTeamsPojo>> call, Response<List<GetAllTeamsPojo>> response) {
                //Toast.makeText(GetAllTeamsActivity.this,"response   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(AllTeamsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    getAllTeams = response.body();
                    list_view.setAdapter(new AllTeamsAdapter(getAllTeams, AllTeamsActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<GetAllTeamsPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AllTeamsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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