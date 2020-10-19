package com.volleyball.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.volleyball.R;
import com.volleyball.activities.TeamScoreDetailsActivity;
import com.volleyball.models.GetAllSchedulePojo;

import java.util.List;

public class GuestMatchesAdaper extends BaseAdapter {
    ProgressDialog progressDialog;
    List<GetAllSchedulePojo> getAllSchedule;
    Context cnt;
    public GuestMatchesAdaper(List<GetAllSchedulePojo> ar, Context cnt)
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
        View obj2=obj1.inflate(R.layout.list_guest_schedules,null);

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


        CardView cv_team_name=(CardView)obj2.findViewById(R.id.cv_team_name);
        cv_team_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, TeamScoreDetailsActivity.class);
                intent.putExtra("t1id",getAllSchedule.get(pos).getTeam1_id());
                intent.putExtra("team1",getAllSchedule.get(pos).getTeam1_name());
                intent.putExtra("t1logo",getAllSchedule.get(pos).getTeam1_logo());
                intent.putExtra("team1_score",getAllSchedule.get(pos).getTeam1_score());
                intent.putExtra("t2id",getAllSchedule.get(pos).getTeam2_id());
                intent.putExtra("team2",getAllSchedule.get(pos).getTeam2_name());
                intent.putExtra("t2logo",getAllSchedule.get(pos).getTeam2_logo());
                intent.putExtra("team2_score",getAllSchedule.get(pos).getTeam2_score());
                intent.putExtra("result",getAllSchedule.get(pos).getResult());
                intent.putExtra("sid",getAllSchedule.get(pos).getS_id());
                cnt.startActivity(intent);
            }
        });

        return obj2;
    }

}