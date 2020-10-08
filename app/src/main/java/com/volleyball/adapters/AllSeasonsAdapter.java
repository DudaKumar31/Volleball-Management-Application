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
import com.volleyball.activities.AllSeasonsActivity;
import com.volleyball.activities.EditSeasonsActivity;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllSeasonsPojo;
import com.volleyball.models.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSeasonsAdapter extends BaseAdapter {
    ProgressDialog progressDialog;
    List<GetAllSeasonsPojo> getAllSeasonsPojo;
    Context cnt;
    public AllSeasonsAdapter(List<GetAllSeasonsPojo> ar, Context cnt)
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
        View obj2=obj1.inflate(R.layout.list_all_seasons,null);

        ImageView image_view=(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(getAllSeasonsPojo.get(pos).getS_logo()).into(image_view);

        TextView tv_manager_name=(TextView)obj2.findViewById(R.id.tv_manager_name);
        tv_manager_name.setText(getAllSeasonsPojo.get(pos).getS_name());

        final ImageView im_edit=(ImageView)obj2.findViewById(R.id.im_edit);
        im_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(cnt, EditSeasonsActivity.class);
                intent.putExtra("name",getAllSeasonsPojo.get(pos).getS_name());
                intent.putExtra("image",getAllSeasonsPojo.get(pos).getS_logo());
                intent.putExtra("id",getAllSeasonsPojo.get(pos).getId());
                cnt.startActivity(intent);
                ((Activity)cnt).finish();
            }
        });

        ImageView im_delete=(ImageView)obj2.findViewById(R.id.im_delete);
        im_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(cnt, "Delete Option Clicked"+getAllSeasonsPojo.get(pos).getId(), Toast.LENGTH_SHORT).show();
                // serverData(getAllSeasonsPojo.get(pos).getId());
                alertDioluge(getAllSeasonsPojo.get(pos).getId());



            }
        });

        return obj2;
    }

    public void serverData(String id){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.deleteseason(id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    cnt.startActivity(new Intent(cnt, AllSeasonsActivity.class));
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
        builder1.setMessage("Do you want to Delete the Season.");  //message we want to show the end user
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