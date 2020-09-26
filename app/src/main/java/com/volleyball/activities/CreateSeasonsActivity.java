package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.volleyball.R;

public class CreateSeasonsActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY_CODE = 200;
    EditText et_season_name;
    Button btn_season_img,btn_Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_seasons);

        getSupportActionBar().setTitle("Create Seasons");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_season_name=(EditText)findViewById(R.id.et_season_name);
        btn_season_img=(Button)findViewById(R.id.btn_season_img);

        btn_season_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);

            }
        });

        btn_Submit = (Button)findViewById(R.id.btn_Submit);
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