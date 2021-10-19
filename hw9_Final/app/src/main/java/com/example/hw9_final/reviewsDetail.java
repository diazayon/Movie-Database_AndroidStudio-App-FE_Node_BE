package com.example.hw9_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.borjabravo.readmoretextview.ReadMoreTextView;

public class reviewsDetail extends AppCompatActivity {

    private TextView reviewName;
    private TextView rating;
    private TextView content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        reviewName = findViewById(R.id.reviewNamedetail);
        rating = findViewById(R.id.ratingsdetail);
        content = findViewById(R.id.reviewcontentdetail);

        Intent myIntent = getIntent();
        String author1 = myIntent.getStringExtra("author");
        String rating1 = myIntent.getStringExtra("rating");
        String content1 = myIntent.getStringExtra("content");

        reviewName.setText(author1);
        rating.setText(rating1);
        content.setText(content1);

    }
}
