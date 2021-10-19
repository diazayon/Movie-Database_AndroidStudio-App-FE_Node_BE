package com.example.hw9_final.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hw9_final.R;
import com.example.hw9_final.detailspage;
import com.example.hw9_final.horizontalAdapter;
import com.example.hw9_final.ui.home.reclyclerViewData;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class watchlistAdapter extends RecyclerView.Adapter<watchlistAdapter.MyViewHolder> {
    private ArrayList<reclyclerViewData> reviewlist = new ArrayList<>();
    private Context context;

    public watchlistAdapter(Context context, ArrayList<reclyclerViewData> reviewlist) {
        this.reviewlist = reviewlist;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView url;
        private TextView type;
        private ImageView remove;
        private reclyclerViewData newdata;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            url = itemView.findViewById(R.id.imagewatchlist);
            type = itemView.findViewById(R.id.watchlisttype);
            remove = itemView.findViewById(R.id.removewatchlist);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newdata = reviewlist.get(getAdapterPosition());
                    final String contentname = newdata.getName();
                    String removed = contentname + " was removed from Watchlist";
                    Toast.makeText(context, removed, Toast.LENGTH_SHORT).show();
                }
            });

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

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.watchlist_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.type.setText(reviewlist.get(position).getContenttype());

        Glide.with(context)
                .asBitmap()
                .load(reviewlist.get(position).getUrl())
                .into(holder.url);
    }

    @Override
    public int getItemCount() {
        return reviewlist.size();
    }
}
