package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.volleyball.R;

public class EditPlayerActivity extends AppCompatActivity {

    ImageView image_view;
    EditText et_player_name;
    Button btn_update;

    private static final String TAG = EditPlayerActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);
        getSupportActionBar().setTitle("Edit Player Information");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image_view=(ImageView)findViewById(R.id.image_view);
        et_player_name=(EditText)findViewById(R.id.et_player_name);
        btn_update=(Button)findViewById(R.id.btn_update);

        et_player_name.setText(getIntent().getStringExtra("name"));
        Glide.with(this).load(getIntent().getStringExtra("image")).into(image_view);
        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // uploadImageToServer();
                //Toast.makeText(EditPlayerActivity.this, ""+getIntent().getStringExtra("pid"), Toast.LENGTH_SHORT).show();
            }
        });
    }
}