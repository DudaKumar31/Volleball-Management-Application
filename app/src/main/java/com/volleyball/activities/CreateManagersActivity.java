package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.volleyball.R;

public class CreateManagersActivity extends AppCompatActivity {
    EditText et_name,et_email,et_phno,et_pwd;
    Button btn_Upload_img,btn_Submit;
    private static final int REQUEST_GALLERY_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_managers);
        getSupportActionBar().setTitle("Create Managers");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_name=(EditText)findViewById(R.id.et_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_phno=(EditText)findViewById(R.id.et_phno);
        et_pwd=(EditText)findViewById(R.id.et_pwd);

        btn_Upload_img=(Button)findViewById(R.id.btn_Upload_img);
        btn_Submit=(Button)findViewById(R.id.btn_Submit);
        btn_Upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);

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