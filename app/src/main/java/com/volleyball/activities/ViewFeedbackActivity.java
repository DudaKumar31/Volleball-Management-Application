package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.volleyball.R;
import com.volleyball.adapters.FeedbackAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.FeedbackPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFeedbackActivity extends AppCompatActivity {
    ListView list_view;
    List<FeedbackPojo> feedbackPojos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);
        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);
        feedbackPojos= new ArrayList<>();
        serverData();
    }
    ProgressDialog progressDialog;
    public void serverData(){
        progressDialog = new ProgressDialog(ViewFeedbackActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<FeedbackPojo>> call = service.getfeedback();
        call.enqueue(new Callback<List<FeedbackPojo>>() {
            @Override
            public void onResponse(Call<List<FeedbackPojo>> call, Response<List<FeedbackPojo>> response) {
                //Toast.makeText(GetAllSeasonsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(ViewFeedbackActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    feedbackPojos = response.body();
                    list_view.setAdapter(new FeedbackAdapter(feedbackPojos, ViewFeedbackActivity.this));

                }
            }

            @Override
            public void onFailure(Call<List<FeedbackPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ViewFeedbackActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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