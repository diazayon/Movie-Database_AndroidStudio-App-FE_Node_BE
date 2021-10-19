package com.example.hw9_final.ui.dashboard;

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
import com.example.hw9_final.R;
import com.example.hw9_final.detailspage;
import com.example.hw9_final.ui.home.reclyclerViewData;

import java.util.ArrayList;

public class RecyclerViewSearchNo extends RecyclerView.Adapter<RecyclerViewSearchNo.ViewHolder> {
    private ArrayList<reclyclerViewData> searchresults = new ArrayList<>();
    private Context searchcontext;

    public RecyclerViewSearchNo(Context searchcontext, ArrayList<reclyclerViewData> searchresults) {
        this.searchresults = searchresults;
        this.searchcontext = searchcontext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nocards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(searchresults.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return searchresults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemtitle);

        }
    }
}
