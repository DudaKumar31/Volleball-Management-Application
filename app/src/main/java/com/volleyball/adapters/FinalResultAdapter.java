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
import com.volleyball.models.FinalResultPojo;
import com.volleyball.models.GetAllSchedulePojo;
import com.volleyball.models.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalResultAdapter extends BaseAdapter {
    List<FinalResultPojo> finalResultPojos;
    Context cnt;
    public FinalResultAdapter(List<FinalResultPojo> ar, Context cnt)
    {
        this.finalResultPojos=ar;
        this.cnt=cnt;
    }
    @Override
    public int getCount() {
        return finalResultPojos.size();
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
        View obj2=obj1.inflate(R.layout.list_final_result,null);

        ImageView ivLogo=(ImageView)obj2.findViewById(R.id.ivLogo);
        Glide.with(cnt).load(finalResultPojos.get(pos).getT_logo()).into(ivLogo);

        TextView tvLoss=(TextView)obj2.findViewById(R.id.tvLoss);
        tvLoss.setText(finalResultPojos.get(pos).getL());

        TextView tvWin=(TextView)obj2.findViewById(R.id.tvWin);
        tvWin.setText(finalResultPojos.get(pos).getW());

        TextView tvTeamName=(TextView)obj2.findViewById(R.id.tvTeamName);
        tvTeamName.setText(finalResultPojos.get(pos).getT_name());


        int value = Integer.parseInt(finalResultPojos.get(pos).getW())+Integer.parseInt(finalResultPojos.get(pos).getL());
        String result = String. valueOf(value) ;


        TextView tvPlayedMatches=(TextView)obj2.findViewById(R.id.tvPlayedMatches);
        tvPlayedMatches.setText(result);

        //Toast.makeText(cnt, ""+finalResultPojos.get(pos).getT_name(), Toast.LENGTH_SHORT).show();


        return obj2;
    }

}