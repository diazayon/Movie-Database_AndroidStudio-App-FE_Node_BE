package com.example.hw9_final;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class reviewsAdapter extends RecyclerView.Adapter<reviewsAdapter.MyViewHolder> {
    private List<reviewData> reviewlist;
    private Context rcontext;

    public reviewsAdapter(Context rcontext, List<reviewData> reviewlist){
        this.reviewlist = reviewlist;
        this.rcontext= rcontext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView reviewName;
        private TextView rating;
        private ReadMoreTextView content;

        public MyViewHolder(View view){
            super(view);
            reviewName = view.findViewById(R.id.reviewName);
            rating = view.findViewById(R.id.ratings);
            content = view.findViewById(R.id.reviewcontent);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reviewData newdata = reviewlist.get(getAdapterPosition());
                    Intent myIntent = new Intent(rcontext, reviewsDetail.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    final String author = newdata.getAuthor();
                    final String rating = newdata.getRating();
                    final String content = newdata.getContent();
                    myIntent.putExtra("author", author);
                    myIntent.putExtra("rating", rating);
                    myIntent.putExtra("content", content);
                    rcontext.startActivity(myIntent);
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
        View itemView = LayoutInflater.from(rcontext).inflate(R.layout.reviews_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewsAdapter.MyViewHolder holder, int position) {
        String author = reviewlist.get(position).getAuthor();
        String ratings = reviewlist.get(position).getRating();
        String review = reviewlist.get(position).getContent();

        holder.reviewName.setText(author);
        holder.rating.setText(ratings);
        holder.content.setText(review);

    }

    @Override
    public int getItemCount() {
        return reviewlist.size();
    }
}
