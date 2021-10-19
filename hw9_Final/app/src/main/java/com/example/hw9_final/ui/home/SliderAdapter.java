package com.example.hw9_final.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hw9_final.R;
import com.example.hw9_final.detailspage;
import com.smarteist.autoimageslider.SliderViewAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
    private final ArrayList<SliderData> mSliderItems;
    private Context context;

    // Constructor
    public SliderAdapter(Context context, ArrayList<SliderData> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
        this.context = context;
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        final SliderData sliderItem = mSliderItems.get(position);

        // Glide is use to load image
        // from url in your imageview.
        Glide.with(context)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(viewHolder.imageViewBackground);
        Glide.with(context)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .into(viewHolder.imageViewBackground2);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SliderData newdata = mSliderItems.get(position);
                Intent myIntent = new Intent(context, detailspage.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                final String id = newdata.getId();
                myIntent.putExtra("id",id);
                final String contenttype = newdata.getContenttype();
                myIntent.putExtra("contenttype",contenttype);
                context.startActivity(myIntent);
            }
        });

    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder{
        // Adapter class for initializing
        // the views of our slider view.

        ImageView imageViewBackground;
        ImageView imageViewBackground2;



        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
            imageViewBackground2 = itemView.findViewById(R.id.myimage2);


        }

    }
}
