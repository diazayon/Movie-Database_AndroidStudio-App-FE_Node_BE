package com.example.hw9_final.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.hw9_final.R;
import com.example.hw9_final.detailspage;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class recyclerViewTop extends RecyclerView.Adapter<recyclerViewTop.ViewHolder> {
    private ArrayList<reclyclerViewData> toparray = new ArrayList<>();
    private Context topcontext;


    public recyclerViewTop(Context topcontext, ArrayList<reclyclerViewData> toparray) {
        this.toparray = toparray;
        this.topcontext = topcontext;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(topcontext)
                .asBitmap()
                .load(toparray.get(position).getUrl())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return toparray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        ImageView image;
        ImageView menuitem;
        Boolean watchlistvalue = true;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewTop);
            menuitem = itemView.findViewById(R.id.menuitem);
            menuitem.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reclyclerViewData newdata = toparray.get(getAdapterPosition());
                    Intent myIntent = new Intent(topcontext, detailspage.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    final String id = newdata.getId();
                    myIntent.putExtra("id",id);
                    final String contenttype = newdata.getContenttype();
                    myIntent.putExtra("contenttype",contenttype);
                    Log.d("clicky", "onClick: " + toparray);
                    Log.d("clickyposition", "onClick: " + getAdapterPosition());
                    topcontext.startActivity(myIntent);
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
            if(watchlistvalue){
                popupMenu.getMenu().findItem(R.id.watchlist).setVisible(true);
                popupMenu.getMenu().findItem(R.id.removewatchlist).setVisible(false);
            } else{
                popupMenu.getMenu().findItem(R.id.watchlist).setVisible(false);
                popupMenu.getMenu().findItem(R.id.removewatchlist).setVisible(true);
            }
            popupMenu.show();

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            reclyclerViewData newdata = toparray.get(getAdapterPosition());
            final String titlename = newdata.getName();
            final String id = newdata.getId();
            final String url = newdata.getUrl();
            final String type = newdata.getContenttype().toLowerCase();
            Set<String> hash_Set = new HashSet<String>();
            hash_Set.add(titlename);
            hash_Set.add(id);
            hash_Set.add(url);
            String tmdb =  "http://www.themoviedb.org/"+ type +"/"+ id +"?language=en-US";
            String facebook = "http://www.facebook.com/sharer/sharer.php?u=https://www.themoviedb.org/"+ type +"/"+ id + "?language=en-US";
            String twitter = "http://twitter.com/intent/tweet?url=https://www.themoviedb.org/"+ type + "/" + id +"?language=en-US";

            switch (item.getItemId()) {
                case R.id.tmdb:
                    Uri uri = Uri.parse(tmdb);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    topcontext.startActivity(intent);
                    return true;

                case R.id.facebook:
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebook));
                    facebookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    topcontext.startActivity(facebookIntent);
                    return true;

                case R.id.twitter:
                    Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter));
                    twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    topcontext.startActivity(twitterIntent);
                    return true;

                case R.id.watchlist:
                    SharedPreferences sharedPreferences = topcontext.getSharedPreferences("MySharedPref",topcontext.MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putStringSet(id, hash_Set);
                    myEdit.commit();
                    String s2 = titlename + " was added to Watchlist";
                    Toast.makeText(topcontext, s2, Toast.LENGTH_LONG).show();
                    watchlistvalue = false;
                    return true;

                case R.id.removewatchlist:
                    @SuppressLint("WrongConstant") SharedPreferences sh2 = topcontext.getSharedPreferences("MySharedPref", topcontext.MODE_APPEND);
                    String s22 = titlename + " was removed to Watchlist";
                    Toast.makeText(topcontext, s22, Toast.LENGTH_LONG).show();
                    watchlistvalue = true;
                    return true;

                default:
                    return false;
            }
        }
    }

}
