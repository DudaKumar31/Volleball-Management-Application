package com.volleyball.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.volleyball.R;
import com.volleyball.activities.SchedulebySeasonActivity;
import com.volleyball.models.GetAllSeasonsPojo;

import java.util.List;

public class GuestAllSeasonsAdapter extends BaseAdapter {
    List<GetAllSeasonsPojo> getAllSeasonsPojo;
    Context cnt;
    public GuestAllSeasonsAdapter(List<GetAllSeasonsPojo> ar, Context cnt)
    {
        this.getAllSeasonsPojo=ar;
        this.cnt=cnt;
    }
    @Override
    public int getCount() {
        return getAllSeasonsPojo.size();
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
        View obj2=obj1.inflate(R.layout.list_current_seasons,null);


        ImageView iv_image_view=(ImageView)obj2.findViewById(R.id.iv_image_view);
        Glide.with(cnt).load(getAllSeasonsPojo.get(pos).getS_logo()).into(iv_image_view);



        TextView tv_text=(TextView)obj2.findViewById(R.id.tv_text);
        tv_text.setText(getAllSeasonsPojo.get(pos).getS_name());

        iv_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, SchedulebySeasonActivity.class);
                intent.putExtra("id",getAllSeasonsPojo.get(pos).getId());
                cnt.startActivity(intent);
            }
        });


        return obj2;
    }



}