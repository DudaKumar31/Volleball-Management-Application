package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.volleyball.R;
import com.volleyball.adapters.PlayersScoreAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.PlayerPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPlayersScoreActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players_score);

        getSupportActionBar().setTitle("Add Players Score");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);
        loadAllPlayers();
    }

    private void loadAllPlayers(){
        pd = new ProgressDialog(AddPlayersScoreActivity.this);
        pd.setTitle("Please wait,Data is being loaded.");
        pd.show();
        ApiService api = RetroClient.getApiService();
        Call<List<PlayerPojo>> call = api.getPlayersByTeam(getIntent().getStringExtra("t_id"));
        call.enqueue(new Callback<List<PlayerPojo>>() {
            @Override
            public void onResponse(Call<List<PlayerPojo>> call, Response<List<PlayerPojo>> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    List<PlayerPojo> players=response.body();
                    if(players!=null) {
                        if (players.size() > 0) {
                            list_view.setAdapter(new PlayersScoreAdapter(players, AddPlayersScoreActivity.this,getIntent().getStringExtra("sid"),getIntent().getStringExtra("t_id")));
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<PlayerPojo>> call, Throwable t) {
                pd.dismiss();
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