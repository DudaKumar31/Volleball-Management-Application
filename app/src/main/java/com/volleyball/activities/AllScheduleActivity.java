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

import com.volleyball.adapters.AllScheduleAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllSchedulePojo;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllScheduleActivity extends AppCompatActivity {

    ListView list_view;
    List<GetAllSchedulePojo> schedulePojos;
    Button btn_add_Schedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_schedule);
        btn_add_Schedule=(Button)findViewById(R.id.btn_add_Schedule);
        btn_add_Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllScheduleActivity.this,AddScheduleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        list_view=(ListView)findViewById(R.id.list_view);
        schedulePojos= new ArrayList<>();
        serverData();


    }
    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(AllScheduleActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<GetAllSchedulePojo>> call = service.gettestschedule();
        call.enqueue(new Callback<List<GetAllSchedulePojo>>() {
            @Override
            public void onResponse(Call<List<GetAllSchedulePojo>> call, Response<List<GetAllSchedulePojo>> response) {
                //Toast.makeText(GetAllSeasonsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(AllScheduleActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    schedulePojos = response.body();
                    list_view.setAdapter(new AllScheduleAdapter(schedulePojos, AllScheduleActivity.this));

                }
            }

            @Override
            public void onFailure(Call<List<GetAllSchedulePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AllScheduleActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

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
