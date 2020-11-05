package com.volleyball.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.volleyball.R;
import com.volleyball.activities.DetailsofFeedbackActivity;
import com.volleyball.models.FeedbackPojo;

import java.util.List;

public class FeedbackAdapter extends BaseAdapter {
    ProgressDialog progressDialog;
    List<FeedbackPojo> feedbackPojos;
    Context cnt;
    public FeedbackAdapter(List<FeedbackPojo> ar, Context cnt)
    {
        this.feedbackPojos=ar;
        this.cnt=cnt;
    }
    @Override
    public int getCount() {
        return feedbackPojos.size();
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
        View obj2=obj1.inflate(R.layout.list_feedback,null);



        TextView tv_email=(TextView)obj2.findViewById(R.id.tv_email);
        tv_email.setText("Email  :"+feedbackPojos.get(pos).getEmail());

        TextView tv_subject=(TextView)obj2.findViewById(R.id.tv_subject);
        tv_subject.setText("Subject  :"+feedbackPojos.get(pos).getSubject());

        TextView tv_message=(TextView)obj2.findViewById(R.id.tv_message);
        tv_message.setText("Feedback  :"+feedbackPojos.get(pos).getMsg());

        CardView card_view=(CardView)obj2.findViewById(R.id.card_view);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, DetailsofFeedbackActivity.class);
                intent.putExtra("email",feedbackPojos.get(pos).getEmail());
                intent.putExtra("Subject",feedbackPojos.get(pos).getSubject());
                intent.putExtra("Feedback",feedbackPojos.get(pos).getMsg());
                cnt.startActivity(intent);
            }
        });



        return obj2;
    }



}
