package com.volleyball.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.volleyball.R;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tv_sign_in,tv_forgot_pass;
    Button btn_login,btn_guest;
    TextInputEditText et_uname,et_pwd;
    ProgressDialog pd;
    String[] Role;
    AutoCompleteTextView spin_drop_down;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        Role = new String[] {"League Manager", "Team Manager"};
        spin_drop_down = findViewById(R.id.spin_drop_down);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), R.layout.dropdown_menu_popup_item, Role);
        spin_drop_down.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LoginActivity.this, ""+spin_drop_down.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        spin_drop_down.setAdapter(adapter);

        tv_sign_in=(TextView)findViewById(R.id.tv_sign_in);
        tv_forgot_pass=(TextView)findViewById(R.id.tv_forgot_pass);


        et_uname=(TextInputEditText)findViewById(R.id.et_uname);
        et_pwd=(TextInputEditText)findViewById(R.id.et_pwd);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_guest=(Button)findViewById(R.id.btn_guest);
        btn_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, UserDashBoardActivity.class);
                startActivity(intent);

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spin_drop_down.getText().toString().equals("Team Manager")){
                    /*Intent intent=new Intent(LoginActivity.this, UserDashBoardActivity.class);
                    startActivity(intent);*/
                    managerloginData();
                } else if (spin_drop_down.getText().toString().equals("League Manager")) {
                    /*Intent intent=new Intent(LoginActivity.this, LeagueManagerDashBoardActivity.class);
                    startActivity(intent);*/
                    leagueManagerloginData();
                }

                else{
                    Toast.makeText(LoginActivity.this, "Please choose Logintype", Toast.LENGTH_SHORT).show();
                }



            }
        });

        /*Typeface custom_font = Typeface.createFromAsset(LoginActivity.this.getAssets(), "fonts/Lato-Medium.ttf");
        tv_sign_in.setTypeface(custom_font);
        tv_forgot_pass.setTypeface(custom_font);
        btn_login.setTypeface(custom_font);
        tv_terms_cond.setTypeface(custom_font);*/


    }
    public  void leagueManagerloginData() {
        pd= new ProgressDialog(LoginActivity.this);
        pd.setTitle("Please wait, data is being submit...");
        pd.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.adminLogin(et_uname.getText().toString(),et_pwd.getText().toString());
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor et=sharedPreferences.edit();
                    et.putString("uname",et_uname.getText().toString());
                    et.commit();
                    Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, LeagueManagerDashBoardActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public  void managerloginData() {
        pd= new ProgressDialog(LoginActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.managerlogin(et_uname.getText().toString(),et_pwd.getText().toString());
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor et=sharedPreferences.edit();
                    et.putString("uname",et_uname.getText().toString());
                    et.commit();
                    Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, TeamManagerDashBoardActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
