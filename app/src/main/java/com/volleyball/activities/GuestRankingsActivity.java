package com.volleyball.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.volleyball.R;
import com.volleyball.adapters.GuestPlayerRankingsAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.PlayerPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestRankingsActivity extends AppCompatActivity {
    ListView list_view;
    List<PlayerPojo> playerPojos;
    EditText et_search;
    GuestPlayerRankingsAdapter guestAllPlayersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_rankings);

        getSupportActionBar().setTitle("Player Rankings");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        list_view=(ListView)findViewById(R.id.list_view);
        playerPojos= new ArrayList<>();
        serverData();

        et_search = (EditText)findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = et_search.getText().toString().toLowerCase(Locale.getDefault());
                guestAllPlayersAdapter.playerfilter(text);
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
    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(GuestRankingsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<PlayerPojo>> call = service.getplayerranking();
        call.enqueue(new Callback<List<PlayerPojo>>() {
            @Override
            public void onResponse(Call<List<PlayerPojo>> call, Response<List<PlayerPojo>> response) {

                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(GuestRankingsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    playerPojos = response.body();
                    guestAllPlayersAdapter = new GuestPlayerRankingsAdapter(playerPojos, GuestRankingsActivity.this);
                    list_view.setAdapter(guestAllPlayersAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<PlayerPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GuestRankingsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
