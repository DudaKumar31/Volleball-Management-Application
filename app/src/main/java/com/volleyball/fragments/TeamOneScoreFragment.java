package com.volleyball.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.volleyball.R;
import com.volleyball.activities.TeamScoreDetailsActivity;
import com.volleyball.adapters.TeamScoresAdapter;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.PlayerScoreModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamOneScoreFragment extends Fragment {
    View view;
    Button firstButton;
    ListView list_view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_one_score, container, false);

        list_view=(ListView)view.findViewById(R.id.list_scoreview);

        String team=((TeamScoreDetailsActivity) getActivity()).t1;
        String sid=((TeamScoreDetailsActivity) getActivity()).sid;
        getPlayersScore(team,sid);
        // Toast.makeText(getActivity(),team+sid,Toast.LENGTH_LONG).show();
        return view;
    }

    ProgressDialog pd;
    private void getPlayersScore(String team,String sid){
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Please wait,Data is being loaded.");
        pd.show();
        ApiService api = RetroClient.getApiService();
        Call<List<PlayerScoreModel>> call = api.getPlayersScore(sid,team);
        call.enqueue(new Callback<List<PlayerScoreModel>>() {
            @Override
            public void onResponse(Call<List<PlayerScoreModel>> call, Response<List<PlayerScoreModel>> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    List<PlayerScoreModel> teams=response.body();
                    if(teams!=null) {
                        // Toast.makeText(getActivity(),teams.toString(),Toast.LENGTH_LONG).show();
                        list_view.setAdapter(new TeamScoresAdapter(teams, getActivity()));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<PlayerScoreModel>> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
}