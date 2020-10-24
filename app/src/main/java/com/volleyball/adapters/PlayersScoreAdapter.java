package com.volleyball.adapters;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.volleyball.R;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.PlayerPojo;
import com.volleyball.models.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersScoreAdapter extends BaseAdapter {
    List<PlayerPojo> ar;
    Context cnt;
    String sid,team_id,totalscore;

    public PlayersScoreAdapter(List<PlayerPojo> ar, Context cnt,String sid,String team_id)
    {
        this.ar=ar;
        this.cnt=cnt;
        this.sid=sid;
        this.team_id=team_id;
    }
    @Override
    public int getCount() {
        return ar.size();
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
        View obj2=obj1.inflate(R.layout.adapter_activity_players_score,null);


        ImageView player_img=(ImageView)obj2.findViewById(R.id.player_img);
        Glide.with(cnt).load(ar.get(pos).getP_image()).into(player_img);

        TextView tv_player=(TextView)obj2.findViewById(R.id.tv_player);
        tv_player.setText(ar.get(pos).getP_name());

        TextView tv_points=(TextView)obj2.findViewById(R.id.tv_points);
        tv_points.setText(ar.get(pos).getPoint());

        final EditText etScore=(EditText)obj2.findViewById(R.id.etScore);
        Button btnAdd=(Button)obj2.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                totalscore= String.valueOf(Integer.parseInt(ar.get(pos).getPoint())+Integer.parseInt(etScore.getText().toString()));

                addScore(sid,ar.get(pos).getP_name(),etScore.getText().toString(),team_id,totalscore);

                //Toast.makeText(cnt, ""+ar.get(pos).getP_name(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(cnt, ""+team_id, Toast.LENGTH_SHORT).show();
            }
        });

        return obj2;
    }

    ProgressDialog pd;
    private void addScore(String sid1,String player_name,String score,String team_name,String totalscore){
        pd = new ProgressDialog(cnt);
        pd.setTitle("Please wait,Data is being loaded.");
        pd.show();
        ApiService api = RetroClient.getApiService();
        Call<ResponseData> call = api.addPlayerScore(sid1,player_name,score,team_name,totalscore);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(cnt,"Score is updated successfully.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
            }
        });
    }



}