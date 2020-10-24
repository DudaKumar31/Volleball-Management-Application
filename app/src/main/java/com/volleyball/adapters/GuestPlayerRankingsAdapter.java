package com.volleyball.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.volleyball.R;
import com.volleyball.models.PlayerPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GuestPlayerRankingsAdapter extends BaseAdapter {
    ProgressDialog progressDialog;
    List<PlayerPojo> playerPojo,searchplayer;
    Context cnt;
    public GuestPlayerRankingsAdapter(List<PlayerPojo> ar, Context cnt)
    {
        this.searchplayer=ar;
        this.cnt = cnt;
        this.playerPojo = new ArrayList<PlayerPojo>();
        this.playerPojo.addAll(ar);
    }
    @Override
    public int getCount() {
        return playerPojo.size();
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
        View obj2=obj1.inflate(R.layout.list_rankings,null);

        ImageView image_view=(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(playerPojo.get(pos).getP_image()).into(image_view);


        TextView tv_player_points=(TextView)obj2.findViewById(R.id.tv_player_points);
        tv_player_points.setText(playerPojo.get(pos).getPoint());


        TextView tv_player_name=(TextView)obj2.findViewById(R.id.tv_player_name);
        tv_player_name.setText(playerPojo.get(pos).getP_name());

        return obj2;
    }

    public void playerfilter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        playerPojo.clear();
        if (charText.length() == 0) {
            playerPojo.addAll(searchplayer);
        } else {
            for (PlayerPojo wp : searchplayer) {
                if (wp.getP_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    playerPojo.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}