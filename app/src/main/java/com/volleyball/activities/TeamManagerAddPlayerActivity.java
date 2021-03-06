package com.volleyball.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.volleyball.R;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.ResponseData;
import com.volleyball.models.TeamsPojo;

import java.io.File;
import java.util.Arrays;
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

public class TeamManagerAddPlayerActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = TeamManagerAddPlayerActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://cegepvolleyleagues.com/";
    private Uri uri;
    EditText et_player_name, et_description, et_player_team;
    Button btn_season_img, btn_Submit;
    ProgressDialog pd;
    Spinner spin_teams;
    List<TeamsPojo> a2;
    String[] teams;
    String[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manager_add_player);

        getTeams();

        getSupportActionBar().setTitle("Add Player");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_player_name = (EditText) findViewById(R.id.et_player_name);
        spin_teams = (Spinner) findViewById(R.id.spin_teams);


        Toast.makeText(getApplicationContext(), getIntent().getStringExtra("teamname"), Toast.LENGTH_LONG).show();

        //  et_player_team=(EditText)findViewById(R.id.et_player_team);

        btn_season_img = (Button) findViewById(R.id.btn_season_img);
        btn_season_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);

            }
        });
        btn_Submit = (Button) findViewById(R.id.btn_Submit);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageToServer();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, TeamManagerAddPlayerActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, TeamManagerAddPlayerActivity.this);
                file = new File(filePath);

            } else {
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
        if (uri != null) {
            String filePath = getRealPathFromURIPath(uri, TeamManagerAddPlayerActivity.this);
            file = new File(filePath);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");

    }

    private void uploadImageToServer() {
        pd = new ProgressDialog(TeamManagerAddPlayerActivity.this);
        pd.setTitle("Loading");
        pd.show();
        Map<String, String> map = new HashMap<>();
        map.put("p_name", et_player_name.getText().toString());
        map.put("team_id", getIntent().getStringExtra("teamid"));

        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService uploadImage = retrofit.create(ApiService.class);
        Call<ResponseData> fileUpload = uploadImage.addplayer(fileToUpload, map);
        fileUpload.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                Toast.makeText(TeamManagerAddPlayerActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                startActivity(new Intent(TeamManagerAddPlayerActivity.this, TeamManagerDashBoardActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(TeamManagerAddPlayerActivity.this, "Error :" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getTeams() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<TeamsPojo>> call = apiService.getteamsspinner();
        call.enqueue(new Callback<List<TeamsPojo>>() {
            @Override
            public void onResponse(Call<List<TeamsPojo>> call, Response<List<TeamsPojo>> response) {
                a2 = response.body();
                Log.d("TAG", "Response = " + a2);
                teams = new String[a2.size() + 1];
                ids = new String[a2.size() + 1];
                teams[0] = "Select Teams";
                ids[0] = "-1";
                for (int i = 0; i < a2.size(); i++) {
                    teams[i + 1] = a2.get(i).getT_name();
                    ids[i + 1] = a2.get(i).getTeam_id();
                }
                ArrayAdapter aa = new ArrayAdapter(TeamManagerAddPlayerActivity.this, android.R.layout.simple_spinner_item, teams);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_teams.setAdapter(aa);
                int index = Arrays.asList(teams).indexOf(getIntent().getStringExtra("teamname"));
                spin_teams.setSelection(index);
                Toast.makeText(getApplicationContext(), " " + index, Toast.LENGTH_LONG).show();
                spin_teams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        if (pos > 0) {

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<TeamsPojo>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
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