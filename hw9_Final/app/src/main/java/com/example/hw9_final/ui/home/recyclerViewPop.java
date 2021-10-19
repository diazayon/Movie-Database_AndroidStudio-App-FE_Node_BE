package com.example.hw9_final.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hw9_final.R;
import com.example.hw9_final.detailspage;

import java.util.ArrayList;

public class recyclerViewPop extends RecyclerView.Adapter<recyclerViewPop.ViewHolder> {
    private ArrayList<String> poparray = new ArrayList<>();
    private Context popcontext;

    public recyclerViewPop(Context popcontext, ArrayList<String> poparray) {
        this.poparray = poparray;
        this.popcontext = popcontext;
    }

    @NonNull
    @Override
    public recyclerViewPop.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_card, parent, false);
        return new recyclerViewPop.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewPop.ViewHolder holder, int position) {

        Glide.with(popcontext)
                .asBitmap()
                .load(poparray.get(position))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return poparray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        ImageView image;
        TextView menuitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewTop);
            menuitem = itemView.findViewById(R.id.menuitem);
            menuitem.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(popcontext, detailspage.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d("clicky", "onClick: " + poparray);
                    Log.d("clickyposition", "onClick: " + getAdapterPosition());
                    popcontext.startActivity(myIntent);
                }
            });

        }

        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.details);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tmdb:
                    Uri uri = Uri.parse("http://www.themoviedb.org/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    popcontext.startActivity(intent);
                    return true;

                case R.id.facebook:
                    return true;

                case R.id.twitter:
                    return true;

                case R.id.watchlist:
                    return true;

                default:
                    return false;
            }
        }
    }
}
