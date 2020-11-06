package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.volleyball.R;

public class DetailsofFeedbackActivity extends AppCompatActivity {
    TextView tv_email,tv_subject,tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsof_feedback);

        getSupportActionBar().setTitle("Feedback Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_email=(TextView)findViewById(R.id.tv_email);
        tv_subject=(TextView)findViewById(R.id.tv_subject);
        tv_message=(TextView)findViewById(R.id.tv_message);

        tv_email.setText("Email  :"+getIntent().getStringExtra("email"));
        tv_subject.setText("Subject  :"+getIntent().getStringExtra("Subject"));
        tv_message.setText("Feedback  :"+getIntent().getStringExtra("Feedback"));


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