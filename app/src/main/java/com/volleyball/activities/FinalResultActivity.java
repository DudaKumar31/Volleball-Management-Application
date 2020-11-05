package com.volleyball.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.volleyball.R;
import com.volleyball.adapters.AllPlayersAdapter;
import com.volleyball.adapters.FinalResultAdapter;
import com.volleyball.adapters.GetAllManagerssAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.FinalResultPojo;
import com.volleyball.models.GetAllManagersPojo;
import com.volleyball.models.GetAllSeasonsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalResultActivity extends AppCompatActivity {
    Spinner spin_selectseason;
    List<GetAllSeasonsPojo> a2;
    List<FinalResultPojo> a1;
    String team_seasons_Id;
    ProgressDialog progressDialog;
    FinalResultAdapter finalResultAdapter;
    ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);

        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadallSeasons();

        list_view=(ListView)findViewById(R.id.list_view);
        a1= new ArrayList<>();


    }

    private void loadallSeasons() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<GetAllSeasonsPojo>> call = apiService.getseasons();
        call.enqueue(new Callback<List<GetAllSeasonsPojo>>() {
            @Override
            public void onResponse(Call<List<GetAllSeasonsPojo>> call, Response<List<GetAllSeasonsPojo>> response) {
                if (response.isSuccessful()) {
                    final List<GetAllSeasonsPojo> seasons = response.body();
                    if (seasons != null) {
                        if (seasons.size() > 0) {
                            spin_selectseason = (Spinner) findViewById(R.id.spin_selectseason);
                            ArrayList<String> addseasons = new ArrayList<String>();
                            final ArrayList<String> getseasonsID = new ArrayList<String>();
                            addseasons.add("Select Season");
                            getseasonsID.add("-1");
                            for (int i = 0; i < seasons.size(); i++) {
                                addseasons.add(seasons.get(i).getS_name());
                                getseasonsID.add(seasons.get(i).getId());
                            }
                            ArrayAdapter<String> adp = new ArrayAdapter<String>(FinalResultActivity.this, android.R.layout.simple_spinner_dropdown_item, addseasons);
                            spin_selectseason.setAdapter(adp);
                            spin_selectseason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (spin_selectseason.getSelectedItem().equals("Select Season")){
                                        //Toast.makeText(FinalResultActivity.this, "", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        serverData(getseasonsID.get(spin_selectseason.getSelectedItemPosition()));

                                    }


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
    public void serverData(String season){
        progressDialog = new ProgressDialog(FinalResultActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<FinalResultPojo>> call = service.getResultsBySeason(season);
        call.enqueue(new Callback<List<FinalResultPojo>>() {
            @Override
            public void onResponse(Call<List<FinalResultPojo>> call, Response<List<FinalResultPojo>> response) {
                // Toast.makeText(FinalResultActivity.this, ""+response.body().size(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null || response.body().size() == 0){
                    Toast.makeText(FinalResultActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }
               /* if(response.body().size() == 0){
                    Toast.makeText(FinalResultActivity.this,"No Teams Found in this season ",Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(FinalResultActivity.this,FinalResultActivity.class));
                }*/
                else {
                    a1 = response.body();
                    list_view.setAdapter(new FinalResultAdapter(a1, FinalResultActivity.this));

                }
            }

            @Override
            public void onFailure(Call<List<FinalResultPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(FinalResultActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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