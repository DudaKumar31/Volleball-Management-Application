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

import com.bumptech.glide.Glide;
import com.volleyball.R;
import com.volleyball.activities.AllPlayersActivity;
import com.volleyball.activities.EditPlayerActivity;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllTeamsPojo;
import com.volleyball.models.PlayerPojo;
import com.volleyball.models.ResponseData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestAllPlayersAdapter extends BaseAdapter {
    ProgressDialog progressDialog;
    List<PlayerPojo> playerPojo,searchplayer;
    Context cnt;
    public GuestAllPlayersAdapter(List<PlayerPojo> ar, Context cnt)
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
        View obj2=obj1.inflate(R.layout.list_guest_all_players,null);

        ImageView image_view=(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(playerPojo.get(pos).getP_image()).into(image_view);

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