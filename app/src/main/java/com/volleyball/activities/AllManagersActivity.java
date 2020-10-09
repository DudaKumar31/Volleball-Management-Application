package com.volleyball.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.volleyball.adapters.GetAllManagerssAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllManagersPojo;
import com.volleyball.models.GetAllSeasonsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllManagersActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<GetAllManagersPojo> a1;
    Button btn_add_managers;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_managers);

        getSupportActionBar().setTitle("Team Managers");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_add_managers=(Button)findViewById(R.id.btn_add_managers);
        btn_add_managers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllManagersActivity.this,CreateManagersActivity.class);
                startActivity(intent);
                finish();
            }
        });

        list_view=(ListView)findViewById(R.id.list_view);
        a1= new ArrayList<>();
        serverData();
    }
    public void serverData(){
        progressDialog = new ProgressDialog(AllManagersActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<GetAllManagersPojo>> call = service.getmanagers();
        call.enqueue(new Callback<List<GetAllManagersPojo>>() {
            @Override
            public void onResponse(Call<List<GetAllManagersPojo>> call, Response<List<GetAllManagersPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(AllManagersActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    a1 = response.body();
                    list_view.setAdapter(new GetAllManagerssAdapter(a1, AllManagersActivity.this));

                }
            }

            @Override
            public void onFailure(Call<List<GetAllManagersPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AllManagersActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
