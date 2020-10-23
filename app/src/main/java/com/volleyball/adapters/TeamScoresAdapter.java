package com.volleyball.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.volleyball.R;
import com.volleyball.models.PlayerScoreModel;

import java.util.List;

public class TeamScoresAdapter extends BaseAdapter {
    List<PlayerScoreModel> ar;
    Context cnt;
    public TeamScoresAdapter(List<PlayerScoreModel> ar, Context cnt)
    {
        this.ar=ar;
        this.cnt=cnt;
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
        View obj2=obj1.inflate(R.layout.row_score,null);

        TextView player_name=(TextView)obj2.findViewById(R.id.player_name);
        player_name.setText(ar.get(pos).getP_name());

        TextView score=(TextView)obj2.findViewById(R.id.score);
        score.setText(ar.get(pos).getScore());


        return obj2;
    }
}