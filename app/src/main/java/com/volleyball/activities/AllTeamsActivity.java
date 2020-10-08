package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.volleyball.R;

public class AllTeamsActivity extends AppCompatActivity {
    ListView list_view;
    Button btn_add_teams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_teams);
        getSupportActionBar().setTitle("All Teams");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_add_teams=(Button)findViewById(R.id.btn_add_teams);
        btn_add_teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllTeamsActivity.this,CreateTeamActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}