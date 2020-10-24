package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.volleyball.R;

public class NewsDetailsActivity extends AppCompatActivity {
    ImageView iv_image_view;
    TextView tv_title,tv_descriptiop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        getSupportActionBar().setTitle("News Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_image_view=(ImageView)findViewById(R.id.iv_image_view);
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_descriptiop=(TextView)findViewById(R.id.tv_descriptiop);

        Glide.with(NewsDetailsActivity.this).load(getIntent().getStringExtra("image")).into(iv_image_view);
        tv_title.setText(getIntent().getStringExtra("title"));
        tv_descriptiop.setText(getIntent().getStringExtra("description"));
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