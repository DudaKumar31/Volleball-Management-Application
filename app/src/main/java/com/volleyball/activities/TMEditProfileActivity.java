package com.volleyball.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.volleyball.R;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.ResponseData;
import com.volleyball.models.TmProfilePojo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TMEditProfileActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    private static final String TAG = EditManagerssActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://cegepvolleyleagues.com/";
    private Uri uri;
    EditText et_name,et_email,et_phno,et_pwd;
    Spinner spin_teams;
    ImageView image_view;
    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog pd;
    List<TmProfilePojo> a1;
    Button btn_update;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_m_edit_profile);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences =getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");


        et_name=(EditText)findViewById(R.id.et_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_phno=(EditText)findViewById(R.id.et_phno);
        et_pwd=(EditText)findViewById(R.id.et_pwd);
        spin_teams=(Spinner)findViewById(R.id.spin_teams);
        image_view=(ImageView)findViewById(R.id.image_view);

        pd = new ProgressDialog(TMEditProfileActivity.this);
        pd.setMessage("Loading....");
        pd.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<TmProfilePojo>> call = service.getmymanagerprofile(session);

        call.enqueue(new Callback<List<TmProfilePojo>>() {
            @Override
            public void onResponse(Call<List<TmProfilePojo>> call, Response<List<TmProfilePojo>> response) {

                pd.dismiss();
                a1 = response.body();
                TmProfilePojo user = a1.get(0);

                et_name.setText(user.getName());
                et_phno.setText(user.getPhone());
                et_pwd.setText(user.getPassword());
                et_email.setText(user.getEmail());
                Glide.with(TMEditProfileActivity.this).load(user.getPhoto()).into(image_view);
                Toast.makeText(TMEditProfileActivity.this, ""+user.getTeam_id(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<TmProfilePojo>> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(TMEditProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });
        btn_update=(Button)findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImageToServer();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, TMEditProfileActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, TMEditProfileActivity.this);
                file = new File(filePath);

            }else{
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    File file;
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){
            String filePath = getRealPathFromURIPath(uri, TMEditProfileActivity.this);
            file = new File(filePath);
            // uploadImageToServer();
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");

    }

    private void uploadImageToServer(){
        pd=new ProgressDialog(TMEditProfileActivity.this);
        pd.setTitle("Loading");
        pd.show();
        Map<String, String> map = new HashMap<>();
        map.put("name",et_name.getText().toString());
        map.put("phone",et_phno.getText().toString());
        map.put("email",et_email.getText().toString());
        map.put("password",et_pwd.getText().toString());

        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService uploadImage = retrofit.create(ApiService.class);
        Call<ResponseData> fileUpload = uploadImage.updatemanagerprofile(fileToUpload, map);
        fileUpload.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                Toast.makeText(TMEditProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                finish();
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(TMEditProfileActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_LONG).show();
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