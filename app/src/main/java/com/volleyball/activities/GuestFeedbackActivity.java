package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.volleyball.R;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestFeedbackActivity extends AppCompatActivity {
    EditText et_name,et_email_id,et_subject,et_feedback;
    Button btn_submit;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_feedback);

        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_name=(EditText)findViewById(R.id.et_name);
        et_email_id=(EditText)findViewById(R.id.et_email_id);
        et_subject=(EditText)findViewById(R.id.et_subject);
        et_feedback=(EditText)findViewById(R.id.et_feedback);

        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_email_id.getText().toString().isEmpty()){
                    Toast.makeText(GuestFeedbackActivity.this, "Please Enter EmailId", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_subject.getText().toString().isEmpty()){
                    Toast.makeText(GuestFeedbackActivity.this, "Please Enter Subject", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_feedback.getText().toString().isEmpty()){
                    Toast.makeText(GuestFeedbackActivity.this, "Please Enter Feedback", Toast.LENGTH_SHORT).show();
                    return;
                }
                guestuserfedback();
            }
        });
    }

    public  void guestuserfedback() {
        pd= new ProgressDialog(GuestFeedbackActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.addfeedback(et_email_id.getText().toString(),et_subject.getText().toString(),et_feedback.getText().toString());
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {

                    Toast.makeText(GuestFeedbackActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(GuestFeedbackActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(GuestFeedbackActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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