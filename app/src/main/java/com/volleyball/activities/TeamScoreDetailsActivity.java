package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.volleyball.R;

import com.volleyball.fragments.TeamOneScoreFragment;

import com.volleyball.fragments.TeamTwoScoreFragment;

public class TeamScoreDetailsActivity extends AppCompatActivity {

    Button btn_team1,btn_team2;
    public  String t1="",t2="",sid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_score_details);

        getSupportActionBar().setTitle("Team Score Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView team1=(TextView)findViewById(R.id.tv_team_a);
        team1.setText(getIntent().getStringExtra("team1"));
        t1 = getIntent().getStringExtra("t1id");
        t2 = getIntent().getStringExtra("t2id");
        sid = getIntent().getStringExtra("sid");

        ImageView team_a_logo=(ImageView)findViewById(R.id.team_a_logo);
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("t1logo")).into(team_a_logo);

        ImageView team_b_logo=(ImageView)findViewById(R.id.team_b_logo);
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("t2logo")).into(team_b_logo);


        TextView team1_score=(TextView)findViewById(R.id.team1_score);
        team1_score.setText("Score Points : "+getIntent().getStringExtra("team1_score"));

        TextView team2=(TextView)findViewById(R.id.tv_team_b);
        team2.setText(getIntent().getStringExtra("team2"));

        TextView team2_score=(TextView)findViewById(R.id.team2_score);
        team2_score.setText("Score Points : "+getIntent().getStringExtra("team2_score"));

        TextView tv_result=(TextView)findViewById(R.id.tv_result);
        tv_result.setText(getIntent().getStringExtra("result"));
        btn_team1=(Button)findViewById(R.id.btn_team1);
        btn_team1.setText(getIntent().getStringExtra("team1"));
        btn_team2=(Button)findViewById(R.id.btn_team2);
        btn_team2.setText(getIntent().getStringExtra("team2"));
//7cd4c2

        btn_team1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_team1.setBackgroundColor(Color.parseColor("#7cd4c2"));
                btn_team2.setBackgroundColor(Color.parseColor("#d5d5d5"));
                loadFragment(new TeamOneScoreFragment());
            }
        });
        btn_team2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_team2.setBackgroundColor(Color.parseColor("#7cd4c2"));
                btn_team1.setBackgroundColor(Color.parseColor("#d5d5d5"));
                loadFragment(new TeamTwoScoreFragment());
            }
        });
        btn_team1.setBackgroundColor(Color.parseColor("#7cd4c2"));
        btn_team2.setBackgroundColor(Color.parseColor("#d5d5d5"));
        loadFragment(new TeamOneScoreFragment());
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
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
