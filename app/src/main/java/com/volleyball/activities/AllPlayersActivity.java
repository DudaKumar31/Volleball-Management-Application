package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.volleyball.R;
import com.volleyball.adapters.AllPlayersAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.PlayerPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllPlayersActivity extends AppCompatActivity {
    ListView list_view;
    List<PlayerPojo> playerPojos;
    Button btn_add_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_players);

    getSupportActionBar().setTitle("All Player");
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    btn_add_player=(Button)findViewById(R.id.btn_add_player);
        btn_add_player.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(AllPlayersActivity.this,AddPlayerActivity.class);
            startActivity(intent);
            finish();
        }
    });

    list_view=(ListView)findViewById(R.id.list_view);
    playerPojos= new ArrayList<>();
    serverData();


}
    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(AllPlayersActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<PlayerPojo>> call = service.getplayer();
        call.enqueue(new Callback<List<PlayerPojo>>() {
            @Override
            public void onResponse(Call<List<PlayerPojo>> call, Response<List<PlayerPojo>> response) {
                //Toast.makeText(GetAllSeasonsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(AllPlayersActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    playerPojos = response.body();
                    list_view.setAdapter(new AllPlayersAdapter(playerPojos, AllPlayersActivity.this));

                }
            }

            @Override
            public void onFailure(Call<List<PlayerPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AllPlayersActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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