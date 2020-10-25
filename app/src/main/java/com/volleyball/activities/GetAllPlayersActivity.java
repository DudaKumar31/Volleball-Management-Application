package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.volleyball.R;
import com.volleyball.adapters.GuestAllPlayersAdapter;
import com.volleyball.adapters.GuestAllTeamsAdapter;
import com.volleyball.adapters.PlayersScoreAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllTeamsPojo;
import com.volleyball.models.PlayerPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllPlayersActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog pd;
    List<PlayerPojo> playerPojos;
    GuestAllPlayersAdapter guestAllPlayersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_players);

        getSupportActionBar().setTitle("All Team Players");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);
        playerPojos= new ArrayList<>();
        serverData();

    }
    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(GetAllPlayersActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<PlayerPojo>> call = service.getPlayersByTeam(getIntent().getStringExtra("team_id"));
        call.enqueue(new Callback<List<PlayerPojo>>() {
            @Override
            public void onResponse(Call<List<PlayerPojo>> call, Response<List<PlayerPojo>> response) {
                //Toast.makeText(GetAllTeamsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(GetAllPlayersActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    playerPojos = response.body();
                    guestAllPlayersAdapter = new GuestAllPlayersAdapter(playerPojos, GetAllPlayersActivity.this);
                    list_view.setAdapter(guestAllPlayersAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<PlayerPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GetAllPlayersActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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