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
import com.volleyball.activities.AllNewsActivity;
import com.volleyball.activities.AllSeasonsActivity;
import com.volleyball.activities.NewsDetailsActivity;
import com.volleyball.api.ApiService;
import com.volleyball.api.RetroClient;
import com.volleyball.models.GetAllNewsPojo;
import com.volleyball.models.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllNewsAdapter extends BaseAdapter {
    ProgressDialog progressDialog;
    List<GetAllNewsPojo> getAllNewsPojos;
    Context cnt;
    public AllNewsAdapter(List<GetAllNewsPojo> ar, Context cnt)
    {
        this.getAllNewsPojos=ar;
        this.cnt=cnt;
    }
    @Override
    public int getCount() {
        return getAllNewsPojos.size();
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
        View obj2=obj1.inflate(R.layout.list_all_news,null);

        ImageView iv_image_view=(ImageView)obj2.findViewById(R.id.iv_image_view);
        Glide.with(cnt).load(getAllNewsPojos.get(pos).getImage()).into(iv_image_view);

        TextView tv_title=(TextView)obj2.findViewById(R.id.tv_title);
        tv_title.setText(getAllNewsPojos.get(pos).getTitle());


        iv_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, NewsDetailsActivity.class);
                intent.putExtra("title",getAllNewsPojos.get(pos).getTitle());
                intent.putExtra("image",getAllNewsPojos.get(pos).getImage());
                intent.putExtra("id",getAllNewsPojos.get(pos).getId());
                intent.putExtra("description",getAllNewsPojos.get(pos).getMsg());
                cnt.startActivity(intent);
                //((Activity)cnt).finish();

            }
        });

        ImageView im_delete=(ImageView)obj2.findViewById(R.id.im_delete);
        im_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDioluge(getAllNewsPojos.get(pos).getId());



            }
        });

        return obj2;
    }

    public void serverData(String id){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.deletenews(id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    cnt.startActivity(new Intent(cnt, AllNewsActivity.class));
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
        builder1.setMessage("Do you want to Delete the News.");  //message we want to show the end user
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