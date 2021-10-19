package com.example.hw9_final;

import android.content.Context;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class castAdapter extends RecyclerView.Adapter<castAdapter.MyViewHolder> {
    private ArrayList<reclyclerViewData> reviewlist = new ArrayList<>();
    private Context context;

    public castAdapter(Context context, ArrayList<reclyclerViewData> reviewlist) {
        this.reviewlist = reviewlist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.actor.setText(reviewlist.get(position).getName());
        Glide.with(context)
                .asBitmap()
                .load(reviewlist.get(position).getUrl())
                .into(holder.url);
    }

    @Override
    public int getItemCount() {
        return reviewlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView url;
        private TextView actor;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            url = itemView.findViewById(R.id.profile_image);
            actor = itemView.findViewById(R.id.author);
        }
    }
}
