package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.volleyball.R;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllSeasonsPojo;
import com.volleyball.models.ResponseData;
import com.volleyball.models.TeamsPojo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddScheduleActivity extends AppCompatActivity {
    EditText etSDate;
    Spinner spin_teamone,spin_teamtwo,spin_selectseason;
    private int mYear, mMonth, mDay;
    List<GetAllSeasonsPojo> a2;
    //String[] seasons;
    Button btn_schduleSubmit;
    String teamone_one_ID,team_two_ID,team_seasons_Id,team_one_Logo,team_two_Logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        getSupportActionBar().setTitle("Add Schedule");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        etSDate =(EditText)findViewById(R.id.etSDate);
        etSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });
        btn_schduleSubmit =(Button) findViewById(R.id.btn_schduleSubmit);
        btn_schduleSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spin_teamone.getSelectedItem().toString().equals(spin_teamtwo.getSelectedItem().toString())){
                    Toast.makeText(AddScheduleActivity.this,"Both teams are same.",Toast.LENGTH_SHORT).show();
                }else if(etSDate.getText().toString().isEmpty()){
                    Toast.makeText(AddScheduleActivity.this,"Please select schedule date.",Toast.LENGTH_SHORT).show();
                }else{
                    addSchedule();
                }

            }
        });
        loadAllTeams();
        loadallSeasons();
    }
    String mdate="";
    private void setDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        etSDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        mdate=year+"-"+ (monthOfYear + 1)+"-"+dayOfMonth;

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    ProgressDialog pd;
    private void addSchedule(){
        pd = new ProgressDialog(AddScheduleActivity.this);
        pd.setTitle("Please wait,Data is being submitted.");
        pd.show();
        ApiService api = RetroClient.getApiService();
        Call<ResponseData> call = api.addSchedule(teamone_one_ID,spin_teamone.getSelectedItem().toString(),team_one_Logo,team_two_ID,spin_teamtwo.getSelectedItem().toString(),team_two_Logo,mdate,team_seasons_Id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    ResponseData rm=response.body();
                    if(rm.status.equals("true")){
                        Toast.makeText(AddScheduleActivity.this,rm.message,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddScheduleActivity.this,AllScheduleActivity.class));
                        finish();
                    }else{
                        Toast.makeText(AddScheduleActivity.this,rm.message,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
            }
        });
    }

    private void loadAllTeams(){
        ApiService api = RetroClient.getApiService();
        Call<List<TeamsPojo>> call = api.getmatchteams();
        call.enqueue(new Callback<List<TeamsPojo>>() {
            @Override
            public void onResponse(Call<List<TeamsPojo>> call, Response<List<TeamsPojo>> response) {
                if (response.isSuccessful()) {
                    final List<TeamsPojo> teams1=response.body();
                    if(teams1!=null) {
                        if(teams1.size()>0) {
                            spin_teamone = (Spinner) findViewById(R.id.spin_teamone);
                            spin_teamtwo = (Spinner) findViewById(R.id.spin_teamtwo);

                            final ArrayList<String> teams = new ArrayList<String>();
                            final ArrayList<String> teamsId = new ArrayList<String>();
                            final ArrayList<String> teamsLogo = new ArrayList<String>();

                            for (int i = 0; i < teams1.size(); i++) {
                                teams.add(teams1.get(i).getT_name());
                                teamsId.add(teams1.get(i).getTeam_id());
                                teamsLogo.add(teams1.get(i).getT_logo());

                            }
                            ArrayAdapter<String> adp = new ArrayAdapter<String>(AddScheduleActivity.this, android.R.layout.simple_spinner_dropdown_item, teams);
                            spin_teamone.setAdapter(adp);
                            spin_teamtwo.setAdapter(adp);
                            spin_teamone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    teamone_one_ID= teamsId.get(spin_teamone.getSelectedItemPosition());
                                    team_one_Logo=teamsLogo.get(spin_teamone.getSelectedItemPosition());


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            spin_teamtwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    team_two_ID= teamsId.get(spin_teamtwo.getSelectedItemPosition());
                                    team_two_Logo=teamsLogo.get(spin_teamtwo.getSelectedItemPosition());


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<TeamsPojo>> call, Throwable t) {

            }
        });
    }

    private void loadallSeasons() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<GetAllSeasonsPojo>> call = apiService.getseasons();
        call.enqueue(new Callback<List<GetAllSeasonsPojo>>() {
            @Override
            public void onResponse(Call<List<GetAllSeasonsPojo>> call, Response<List<GetAllSeasonsPojo>> response) {
                if (response.isSuccessful()) {
                    final List<GetAllSeasonsPojo> seasons=response.body();
                    if(seasons!=null) {
                        if(seasons.size()>0) {
                            spin_selectseason=(Spinner)findViewById(R.id.spin_selectseason);
                            ArrayList<String> addseasons = new ArrayList<String>();
                            final ArrayList<String> getseasonsID = new ArrayList<String>();
                            addseasons.add("Select Season");
                            getseasonsID.add("-1");
                            for (int i = 0; i < seasons.size(); i++) {
                                addseasons.add(seasons.get(i).getS_name());
                                getseasonsID.add(seasons.get(i).getId());
                            }
                            ArrayAdapter<String> adp = new ArrayAdapter<String>(AddScheduleActivity.this, android.R.layout.simple_spinner_dropdown_item, addseasons);
                            spin_selectseason.setAdapter(adp);
                            spin_selectseason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    team_seasons_Id= getseasonsID.get(spin_selectseason.getSelectedItemPosition());

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<GetAllSeasonsPojo>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(this,AllScheduleActivity.class);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}