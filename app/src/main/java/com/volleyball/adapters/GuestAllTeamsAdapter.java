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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.volleyball.R;
import com.volleyball.activities.AllTeamsActivity;
import com.volleyball.activities.EditTeamsActivity;
import com.volleyball.activities.GetAllPlayersActivity;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllTeamsPojo;
import com.volleyball.models.ResponseData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestAllTeamsAdapter extends BaseAdapter {
    List<GetAllTeamsPojo> getAllTeams;
    List<GetAllTeamsPojo> searchallteams;
    Context cnt;
    public GuestAllTeamsAdapter(List<GetAllTeamsPojo> ar, Context cnt)
    {
        this.searchallteams=ar;
        this.cnt = cnt;
        this.getAllTeams = new ArrayList<GetAllTeamsPojo>();
        this.getAllTeams.addAll(ar);

    }
    @Override
    public int getCount() {
        return getAllTeams.size();
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
        View obj2=obj1.inflate(R.layout.list_guest_all_teams,null);


        ImageView image_view=(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(getAllTeams.get(pos).getT_logo()).into(image_view);

        TextView tv_manager_name=(TextView)obj2.findViewById(R.id.tv_manager_name);
        tv_manager_name.setText(getAllTeams.get(pos).getT_name());

        CardView card_view=(CardView)obj2.findViewById(R.id.card_view);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(cnt, GetAllPlayersActivity.class);
                intent.putExtra("team_id",getAllTeams.get(pos).getTeam_id());
                cnt.startActivity(intent);


            }
        });



        return obj2;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        getAllTeams.clear();
        if (charText.length() == 0) {
            getAllTeams.addAll(searchallteams);
        }
        else
        {
            for (GetAllTeamsPojo wp : searchallteams)
            {
                if (wp.getT_name().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    getAllTeams.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}