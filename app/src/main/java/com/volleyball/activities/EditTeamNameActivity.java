package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.volleyball.R;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTeamNameActivity extends AppCompatActivity {
    TextInputEditText et_team_name;
    Button btn_update;
    ImageView team_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team_name);


        getSupportActionBar().setTitle("Edit Team Name");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        et_team_name=(TextInputEditText)findViewById(R.id.et_team_name);
        btn_update=(Button)findViewById(R.id.btn_update);
        team_logo=(ImageView)findViewById(R.id.team_logo);

        et_team_name.setText(getIntent().getStringExtra("team_name"));
        Glide.with(EditTeamNameActivity.this).load(getIntent().getStringExtra("image")).into(team_logo);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editteamname();


            }
        });
    }
    ProgressDialog pd;
    public  void editteamname() {
        pd= new ProgressDialog(EditTeamNameActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.changeteamname(et_team_name.getText().toString(),getIntent().getStringExtra("team_id"));
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(EditTeamNameActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.i("msg", "" + response.body().message);
                    //startActivity(new Intent(EditTeamNameActivity.this,TeamManagerDashBoardActivity.class));
                    finish();
                } else {
                    Toast.makeText(EditTeamNameActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(EditTeamNameActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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