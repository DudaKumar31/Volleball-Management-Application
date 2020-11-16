package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.volleyball.MainActivity;
import com.volleyball.R;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddResultsActivity extends AppCompatActivity {
    TextView tv_team1_score,tv_team2_score;
    EditText etTeam1Score,etTeam2Score,etResult;
    Button btnAddResult,btnTeam1,btnTeam2,btnresultget;
    int t1score,t2score;
    String r,win_team_id,lose_team_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_results);
        getSupportActionBar().setTitle("Add Scores");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_team1_score=(TextView)findViewById(R.id.tv_team1_score);
        tv_team1_score.setText(getIntent().getStringExtra("team1"));

        tv_team2_score=(TextView)findViewById(R.id.tv_team2_score);
        tv_team2_score.setText(getIntent().getStringExtra("team2"));

        etTeam1Score=(EditText)findViewById(R.id.etTeam1Score);
        etTeam1Score.setText(getIntent().getStringExtra("team1_score"));

        etTeam2Score=(EditText)findViewById(R.id.etTeam2Score);
        etTeam2Score.setText(getIntent().getStringExtra("team2_score"));
        etResult=(EditText)findViewById(R.id.etResult);
        etResult.setText(getIntent().getStringExtra("result"));



        btnresultget=(Button)findViewById(R.id.btnresultget);
        btnresultget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                t1score=Integer.parseInt(etTeam1Score.getText().toString());
                t2score=Integer.parseInt(etTeam2Score.getText().toString());



                if(t1score>t2score)
                {
                    r=String.valueOf(t1score-t2score);
                    etResult.setText(tv_team1_score.getText().toString() + " Won By " + r + "Points");
                    win_team_id=getIntent().getStringExtra("team1_id");
                    lose_team_id=getIntent().getStringExtra("team2_id");
                }
                else if(t1score<t2score)
                {
                    r=String.valueOf(t2score-t1score);
                    etResult.setText(tv_team2_score.getText().toString() + " Won By " + r + "Points");
                    win_team_id=getIntent().getStringExtra("team2_id");
                    lose_team_id=getIntent().getStringExtra("team1_id");
                }

                else
                {
                    etResult.setText("Match Tied");
                }

            }
        });

        btnAddResult=(Button)findViewById(R.id.btnAddResult);
        btnAddResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddResultsActivity.this, ""+lose_team_id+""+lose_team_id, Toast.LENGTH_SHORT).show();

                addScore();
            }
        });

        btnTeam1=(Button)findViewById(R.id.btnTeam1);
        btnTeam1.setText("Add "+getIntent().getStringExtra("team1")+" Players Score");
        btnTeam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddPlayersScoreActivity.class);
                intent.putExtra("team_name",getIntent().getStringExtra("team1"));
                intent.putExtra("sid",getIntent().getStringExtra("sid"));
                intent.putExtra("t_id",getIntent().getStringExtra("team1_id"));
                startActivity(intent);
            }
        });
        btnTeam2=(Button)findViewById(R.id.btnTeam2);
        btnTeam2.setText("Add "+getIntent().getStringExtra("team2")+" Players Score");
        btnTeam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddPlayersScoreActivity.class);
                intent.putExtra("team_name",getIntent().getStringExtra("team2"));
                intent.putExtra("sid",getIntent().getStringExtra("sid"));
                intent.putExtra("t_id",getIntent().getStringExtra("team2_id"));
                startActivity(intent);
            }
        });
    }

    ProgressDialog pd;
    private void addScore(){
        pd = new ProgressDialog(AddResultsActivity.this);
        pd.setTitle("Please wait,Data is being submitted.");
        pd.show();
        ApiService api = RetroClient.getApiService();
        Call<ResponseData> call = api.addTeamResultScore(getIntent().getStringExtra("sid"),etTeam1Score.getText().toString(),etTeam2Score.getText().toString(),etResult.getText().toString(),win_team_id,lose_team_id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    ResponseData rm=response.body();
                    if(rm.status.equals("true")){
                        Toast.makeText(AddResultsActivity.this,"Score is updated successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AllScheduleActivity.class));
                        finish();
                    }else{
                        Toast.makeText(AddResultsActivity.this,"Score is already added",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(AddResultsActivity.this,AllScheduleActivity.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}