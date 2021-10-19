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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewSearch extends RecyclerView.Adapter<RecyclerViewSearch.ViewHolder> {
    private ArrayList<reclyclerViewData> searchresults = new ArrayList<>();
    private Context searchcontext;

    public RecyclerViewSearch(Context searchcontext, ArrayList<reclyclerViewData> searchresults) {
        this.searchresults = searchresults;
        this.searchcontext = searchcontext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(searchresults.get(position).getName());
        holder.mediatype.setText(searchresults.get(position).getContenttype());
        holder.rating.setText(searchresults.get(position).getRating());
        holder.year.setText(searchresults.get(position).getDate());

        Glide.with(searchcontext)
                .asBitmap()
                .load(searchresults.get(position).getUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return searchresults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView mediatype;
        TextView title;
        TextView rating;
        TextView year;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.searchimage);
            mediatype = itemView.findViewById(R.id.contenttype);
            title = itemView.findViewById(R.id.itemtitle);
            rating = itemView.findViewById(R.id.itemrating);
            year = itemView.findViewById(R.id.itemyear);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reclyclerViewData newdata = searchresults.get(getAdapterPosition());
                    Intent myIntent = new Intent(searchcontext, detailspage.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    final String id = newdata.getId();
                    myIntent.putExtra("id",id);
                    final String contenttype2 = newdata.getContenttype();
                    myIntent.putExtra("contenttype",contenttype2);
                    Log.d("clicky", "onClick: " + searchresults);
                    Log.d("clickyposition", "onClick: " + getAdapterPosition());
                    searchcontext.startActivity(myIntent);
                }
            });
        }
    }
}
