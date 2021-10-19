package com.example.hw9_final.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hw9_final.MainActivity;
import com.example.hw9_final.R;
import com.example.hw9_final.detailspage;
import com.example.hw9_final.loadingDialog;
import com.example.hw9_final.ui.notifications.NotificationsViewModel;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList<reclyclerViewData> toparray;
    private ArrayList<reclyclerViewData> poparray;
    private ArrayList<reclyclerViewData> toparraytv;
    private ArrayList<reclyclerViewData> poparraytv;
    private Button movieselect;
    private Button tvselect;
    private RelativeLayout movielayout;
    private RelativeLayout tvlayout;
    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toparray = new ArrayList<>();
        poparray = new ArrayList<>();
        toparraytv = new ArrayList<>();
        poparraytv = new ArrayList<>();
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.creditmovie);
        final TextView textView2 = root.findViewById(R.id.credittv);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                textView2.setText(s);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.themoviedb.org/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.themoviedb.org/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        movieselect = (Button)root.findViewById(R.id.movie_layout);
        tvselect = (Button)root.findViewById(R.id.tv_layout);
        movielayout = (RelativeLayout)root.findViewById(R.id.movies_layout);
        tvlayout = (RelativeLayout)root.findViewById(R.id.tvshow_layout);

        movieselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movielayout.setVisibility(View.VISIBLE);
                tvlayout.setVisibility(View.INVISIBLE);
                movieselect.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                tvselect.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.blue_hw9));
            }
        });

        tvselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movielayout.setVisibility(View.INVISIBLE);
                tvlayout.setVisibility(View.VISIBLE);
                tvselect.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                movieselect.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.blue_hw9));
            }
        });
        loadingDialog loadingDialog = new loadingDialog(getActivity());
        loadingDialog.startLoadingDialog();


        String url1 ="http://dda571hw9backend.us-west-1.elasticbeanstalk.com/movies/trending";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url1, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loadingDialog.dismissDialog();

                // we are creating array list for storing our image urls.
                ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

                // initializing the slider view.
                SliderView sliderView = root.findViewById(R.id.slider);

                // adding the urls inside array list
                for (int i = 0; i < 6; i++) {
                    try {

                        JSONObject urlObject = response.getJSONObject(i);

                        SliderData view = new SliderData();
                        view.setId(urlObject.getString("id"));
                        view.setImgUrl(urlObject.getString("poster_path"));
                        view.setContenttype(urlObject.getString("contenttype"));
                        sliderDataArrayList.add(view);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // passing this array list inside our adapter class.
                SliderAdapter adapter = new SliderAdapter(getActivity().getApplicationContext(), sliderDataArrayList);

                // below method is used to set auto cycle direction in left to
                // right direction you can change according to requirement.
                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

                // below method is used to
                // setadapter to sliderview.
                sliderView.setSliderAdapter(adapter);

                // below method is use to set
                // scroll time in seconds.
                sliderView.setScrollTimeInSec(3);

                // to set it scrollable automatically
                // we use below method.
                sliderView.setAutoCycle(true);

                // to start autocycle below method is used.
                sliderView.startAutoCycle();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);

        String url2 ="http://dda571hw9backend.us-west-1.elasticbeanstalk.com/tv/trending";

        JsonArrayRequest jsonArrayRequesttv = new JsonArrayRequest(Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                // we are creating array list for storing our image urls.
                ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

                // initializing the slider view.
                SliderView sliderView = root.findViewById(R.id.slider2);

                for (int i = 0; i < 6; i++) {
                    try {

                        JSONObject urlObject = response.getJSONObject(i);

                        SliderData view = new SliderData();
                        view.setId(urlObject.getString("id"));
                        view.setImgUrl(urlObject.getString("poster_path"));
                        view.setContenttype(urlObject.getString("contenttype"));
                        sliderDataArrayList.add(view);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // passing this array list inside our adapter class.
                SliderAdapter adapter = new SliderAdapter(getActivity().getApplicationContext(), sliderDataArrayList);

                // below method is used to set auto cycle direction in left to
                // right direction you can change according to requirement.
                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

                // below method is used to
                // setadapter to sliderview.
                sliderView.setSliderAdapter(adapter);

                // below method is use to set
                // scroll time in seconds.
                sliderView.setScrollTimeInSec(3);

                // to set it scrollable automatically
                // we use below method.
                sliderView.setAutoCycle(true);

                // to start autocycle below method is used.
                sliderView.startAutoCycle();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequesttv);


        String url3 ="http://dda571hw9backend.us-west-1.elasticbeanstalk.com/movies/toprated";

        JsonArrayRequest jsonArrayRequestmovietop = new JsonArrayRequest(Request.Method.GET, url3, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < 10; i++) {
                    try {
                        JSONObject urlObject = response.getJSONObject(i);
                        reclyclerViewData view = new reclyclerViewData();
                        view.setId(urlObject.getString("id"));
                        view.setUrl(urlObject.getString("poster_path"));
                        view.setContenttype(urlObject.getString("contenttype"));
                        view.setName(urlObject.getString("title"));
                        toparray.add(view);
                    } catch (JSONException e) {e.printStackTrace();}
                }
                LinearLayoutManager layoutManagermovietop = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerViewmovietop = root.findViewById(R.id.toprated);
                recyclerViewmovietop.setLayoutManager(layoutManagermovietop);
                recyclerViewTop adaptermovie = new recyclerViewTop(getActivity().getApplicationContext(), toparray);
                recyclerViewmovietop.setAdapter(adaptermovie);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequestmovietop);

        String url4 ="http://dda571hw9backend.us-west-1.elasticbeanstalk.com/movies/popular";

        JsonArrayRequest jsonArrayRequestmoviepop = new JsonArrayRequest(Request.Method.GET, url4, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < 10; i++) {
                    try {
                        JSONObject urlObject = response.getJSONObject(i);
                        reclyclerViewData view = new reclyclerViewData();
                        view.setId(urlObject.getString("id"));
                        view.setUrl(urlObject.getString("poster_path"));
                        view.setContenttype(urlObject.getString("contenttype"));
                        view.setName(urlObject.getString("title"));
                        poparray.add(view);
                    } catch (JSONException e) {e.printStackTrace();}
                }
                LinearLayoutManager layoutManagermoviepop = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerViewmoviepop = root.findViewById(R.id.popular);
                recyclerViewmoviepop.setLayoutManager(layoutManagermoviepop);
                recyclerViewTop adaptermoviepop = new recyclerViewTop(getActivity().getApplicationContext(), poparray);
                recyclerViewmoviepop.setAdapter(adaptermoviepop);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequestmoviepop);

        String url5 ="http://dda571hw9backend.us-west-1.elasticbeanstalk.com/tv/toprated";

        JsonArrayRequest jsonArrayRequesttvtop = new JsonArrayRequest(Request.Method.GET, url5, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < 10; i++) {
                    try {
                        JSONObject urlObject = response.getJSONObject(i);
                        reclyclerViewData view = new reclyclerViewData();
                        view.setId(urlObject.getString("id"));
                        view.setUrl(urlObject.getString("poster_path"));
                        view.setContenttype(urlObject.getString("contenttype"));
                        view.setName(urlObject.getString("name"));
                        toparraytv.add(view);
                    } catch (JSONException e) {e.printStackTrace();}
                }
                LinearLayoutManager layoutManagertvtop = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerViewtvtop = root.findViewById(R.id.toprated2);
                recyclerViewtvtop.setLayoutManager(layoutManagertvtop);
                recyclerViewTop adaptertvtop = new recyclerViewTop(getActivity().getApplicationContext(), toparraytv);
                recyclerViewtvtop.setAdapter(adaptertvtop);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequesttvtop);

        String url6 ="http://dda571hw9backend.us-west-1.elasticbeanstalk.com/tv/popular";

        JsonArrayRequest jsonArrayRequesttvpop = new JsonArrayRequest(Request.Method.GET, url6, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < 10; i++) {
                    try {
                        JSONObject urlObject = response.getJSONObject(i);
                        reclyclerViewData view = new reclyclerViewData();
                        view.setId(urlObject.getString("id"));
                        view.setUrl(urlObject.getString("poster_path"));
                        view.setContenttype(urlObject.getString("contenttype"));
                        view.setName(urlObject.getString("name"));
                        poparraytv.add(view);
                    } catch (JSONException e) {e.printStackTrace();}
                }
                LinearLayoutManager layoutManagertvpop = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerViewtvpop = root.findViewById(R.id.popular2);
                recyclerViewtvpop.setLayoutManager(layoutManagertvpop);
                recyclerViewTop adaptertvpop = new recyclerViewTop(getActivity().getApplicationContext(), poparraytv);
                recyclerViewtvpop.setAdapter(adaptertvpop);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequesttvpop);

//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            getMenuInflater().inflate(R.menu.details, menu);
//            return true;
//        }


        return root;
    }
}