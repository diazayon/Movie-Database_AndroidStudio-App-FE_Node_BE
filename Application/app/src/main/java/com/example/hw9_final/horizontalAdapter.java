package com.example.hw9_final;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hw9_final.ui.home.reclyclerViewData;

import java.util.ArrayList;
import java.util.List;

public class horizontalAdapter extends RecyclerView.Adapter<horizontalAdapter.MyViewHolder> {
    private ArrayList<reclyclerViewData> reviewlist = new ArrayList<>();
    private Context context;

    public horizontalAdapter(Context context, ArrayList<reclyclerViewData> reviewlist) {
        this.reviewlist = reviewlist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(reviewlist.get(position).getUrl())
                .into(holder.url);

    }

    @Override
    public int getItemCount() {
        return reviewlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView id;
        private ImageView url;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            url = itemView.findViewById(R.id.recommended);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reclyclerViewData newdata = reviewlist.get(getAdapterPosition());
                    Intent myIntent = new Intent(context, detailspage.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    final String id = newdata.getId();
                    myIntent.putExtra("id",id);
                    final String contenttype = newdata.getContenttype();
                    myIntent.putExtra("contenttype",contenttype);
                    Log.d("clicky", "onClick: " + reviewlist);
                    Log.d("clickyposition", "onClick: " + getAdapterPosition());
                    context.startActivity(myIntent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
