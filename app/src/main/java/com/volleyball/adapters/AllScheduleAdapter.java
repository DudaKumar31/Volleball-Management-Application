package com.volleyball.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.volleyball.R;
import com.volleyball.activities.AddResultsActivity;
import com.volleyball.activities.AllPlayersActivity;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllSchedulePojo;
import com.volleyball.models.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllScheduleAdapter extends BaseAdapter {
    ProgressDialog progressDialog;
    List<GetAllSchedulePojo> getAllSchedule;
    Context cnt;
    public AllScheduleAdapter(List<GetAllSchedulePojo> ar, Context cnt)
    {
        this.getAllSchedule=ar;
        this.cnt=cnt;
    }
    @Override
    public int getCount() {
        return getAllSchedule.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup)
    {
        LayoutInflater obj1 = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2=obj1.inflate(R.layout.list_schedule,null);

        ImageView team_a_logo=(ImageView)obj2.findViewById(R.id.team_a_logo);
        Glide.with(cnt).load(getAllSchedule.get(pos).getTeam1_logo()).into(team_a_logo);

        TextView tv_team_a=(TextView)obj2.findViewById(R.id.tv_team_a);
        tv_team_a.setText(getAllSchedule.get(pos).getTeam1_name());

        ImageView team_b_logo=(ImageView)obj2.findViewById(R.id.team_b_logo);
        Glide.with(cnt).load(getAllSchedule.get(pos).getTeam2_logo()).into(team_b_logo);

        TextView tv_team_b=(TextView)obj2.findViewById(R.id.tv_team_b);
        tv_team_b.setText(getAllSchedule.get(pos).getTeam2_name());

        TextView tv_result=(TextView)obj2.findViewById(R.id.tv_result);
        tv_result.setText(getAllSchedule.get(pos).getResult());

        TextView tv_date=(TextView)obj2.findViewById(R.id.tv_date);
        tv_date.setText(getAllSchedule.get(pos).getS_date());



        Button btn_add_rt=(Button)obj2.findViewById(R.id.btn_add_rt);
        btn_add_rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(cnt, AddResultsActivity.class);
                intent.putExtra("sid",getAllSchedule.get(pos).getS_id());
                intent.putExtra("team1",getAllSchedule.get(pos).getTeam1_name());
                intent.putExtra("team1_id",getAllSchedule.get(pos).getTeam1_id());
                intent.putExtra("team2_id",getAllSchedule.get(pos).getTeam2_id());
                intent.putExtra("team1_score",getAllSchedule.get(pos).getTeam1_score());
                intent.putExtra("team2",getAllSchedule.get(pos).getTeam2_name());
                intent.putExtra("team2_score",getAllSchedule.get(pos).getTeam2_score());
                intent.putExtra("result",getAllSchedule.get(pos).getResult());
                cnt.startActivity(intent);
            }
        });

        return obj2;
    }

    public void serverData(String id){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.deleteplayer(id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    cnt.startActivity(new Intent(cnt, AllPlayersActivity.class));
                    Toast.makeText(cnt,"Deleted successfully",Toast.LENGTH_SHORT).show();
                    ((Activity)cnt).finish();

                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(cnt, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void alertDioluge(final String id){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(cnt);
        builder1.setTitle("Alert !!!");
        builder1.setMessage("Do you want to Delete the Player.");  //message we want to show the end user
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        serverData(id);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}