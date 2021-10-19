package com.example.hw9_final;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.example.hw9_final.ui.home.reclyclerViewData;
import com.example.hw9_final.ui.home.recyclerViewTop;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class detailspage extends AppCompatActivity {
    private ArrayList<String> toparray;
    private TextView title;
    private TextView year;
    private TextView genres;
    private YouTubePlayerView ytkey;
    private ReadMoreTextView overview;
    private ImageView facebook;
    private ImageView twitter;
    private String itemID;
    private String facebookurl;
    private String Twitterurl;
    private String contenttype;
    private TextView reviewstext;
    private TextView rectext;
    private ArrayList<reviewData> reviewlist;
    private RecyclerView recyclerView;
    private RecyclerView recommendpicks;
    private RecyclerView castView;
    private ArrayList<reclyclerViewData> picks;
    private ArrayList<reclyclerViewData> picks2;
    private ArrayList<reclyclerViewData> cast;
    private ArrayList<reclyclerViewData> casttv;
    private ImageView addicon;
    private ImageView removeicon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent myIntent = getIntent();
        String itemIDreceived = myIntent.getStringExtra("id");
        String contenttypereceived = myIntent.getStringExtra("contenttype");
        String cnttype = contenttypereceived.toLowerCase();
        itemID = itemIDreceived;
        contenttype = contenttypereceived;
        Log.d("intent", "onCreate: " + itemID);
        Log.d("intent", "onCreate: " + contenttype);

        toparray = new ArrayList<>();
        title = findViewById(R.id.title_detail);
        year = findViewById(R.id.year);
        genres = findViewById(R.id.genre);
        reviewstext = findViewById(R.id.reviewstext);
        rectext = findViewById(R.id.pickstitle);
        ytkey = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(ytkey);
        overview = findViewById(R.id.overviewtrim);
        facebook = findViewById(R.id.facebookbutton);
        twitter = findViewById(R.id.twitterbutton);
        reviewlist = new ArrayList<>();
        picks = new ArrayList<>();
        picks2 = new ArrayList<>();
        cast = new ArrayList<>();
        casttv = new ArrayList<>();
        recyclerView = findViewById(R.id.reviews);

        facebookurl = "http://www.facebook.com/sharer/sharer.php?u=https://www.themoviedb.org/"+ cnttype + "/"+ itemID + "?language=en-US";
        Twitterurl = "http://twitter.com/intent/tweet?url=https://www.themoviedb.org/"+ cnttype + "/" + itemID +"?language=en-US";

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookurl));
                facebookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(facebookIntent);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Twitterurl));
                twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(twitterIntent);
            }
        });

        if (contenttype.equals("MOVIE")) {
            det(itemID);
            vid(itemID);
            rev(itemID);
            picks(itemID);
            cast(itemID);

        } else if (contenttype.equals("TV")) {
            dettv(itemID);
            vidtv(itemID);
            revtv(itemID);
            pickstv(itemID);
            castTV(itemID);
        }
    }

    private void castTV(String itemID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://dda571hw9backend.us-west-1.elasticbeanstalk.com/tvdetails/cast/" + itemID;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject Object = null;
                for (int i = 0; i < 6; i++) {
                    try {
                        Object = response.getJSONObject(i);
                        reclyclerViewData review = new reclyclerViewData();
                        review.setUrl(Object.getString("profile_path"));
                        review.setName(Object.getString("name"));
                        cast.add(review);

                    } catch (JSONException e) {e.printStackTrace();}
                }
                setuprecyclerviewcast(cast);


                //Log.d("tag", "onErrorResponse: " + reviewlist);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void cast(String itemID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://dda571hw9backend.us-west-1.elasticbeanstalk.com/moviedetails/cast/" + itemID;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject Object = null;
                for (int i = 0; i < 6; i++) {
                    try {
                        Object = response.getJSONObject(i);
                        reclyclerViewData review = new reclyclerViewData();
                        review.setUrl(Object.getString("profile_path"));
                        review.setName(Object.getString("name"));
                        cast.add(review);

                    } catch (JSONException e) {e.printStackTrace();}
                }
                setuprecyclerviewcast(cast);


                //Log.d("tag", "onErrorResponse: " + reviewlist);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }
    private void setuprecyclerviewcast(ArrayList<reclyclerViewData> cast) {
        castAdapter adapter = new castAdapter(this, cast);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        castView = findViewById(R.id.cast);
        castView.setLayoutManager(layoutManager);
        castView.setAdapter(adapter);
    }

    private void pickstv(String itemID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://dda571hw9backend.us-west-1.elasticbeanstalk.com/tvdetails/rec/" + itemID;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() == 0){
                    rectext.setVisibility(View.INVISIBLE);
                }

                JSONObject Object = null;
                for (int i = 0; i < 10; i++) {
                    try {
                        Object = response.getJSONObject(i);
                        reclyclerViewData review = new reclyclerViewData();
                        review.setId(Object.getString("id"));
                        review.setUrl(Object.getString("poster_path"));
                        review.setContenttype(Object.getString("contenttype"));
                        picks.add(review);

                    } catch (JSONException e) {e.printStackTrace();}
                }
                setuprecyclerviewpickstv(picks);


                //Log.d("tag", "onErrorResponse: " + reviewlist);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }
    private void setuprecyclerviewpickstv(ArrayList<reclyclerViewData> picks) {
        horizontalAdapter adapter = new horizontalAdapter(this, picks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recommendpicks = findViewById(R.id.picks);
        recommendpicks.setLayoutManager(layoutManager);
        recommendpicks.setAdapter(adapter);
    }

    private void revtv(String itemID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://dda571hw9backend.us-west-1.elasticbeanstalk.com/tvdetails/rev/" + itemID;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() == 0){
                    reviewstext.setVisibility(View.INVISIBLE);
                }

                JSONObject Object = null;
                for (int i = 0; i < 3; i++) {
                    try {
                        Object = response.getJSONObject(i);
                        reviewData review = new reviewData();
                        review.setAuthor(Object.getString("author"));
                        review.setContent(Object.getString("content"));
                        review.setRating(Object.getString("rating"));
                        reviewlist.add(review);

                    } catch (JSONException e) {e.printStackTrace();}
                }
                setuprecyclerview(reviewlist);

                //Log.d("tag", "onErrorResponse: " + reviewlist);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void vidtv(String itemID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://dda571hw9backend.us-west-1.elasticbeanstalk.com/tvdetails/vid/" + itemID;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String title1 = null;
                try {
                    title1 = response.getString("key");
                    String finalTitle = title1;
                    ytkey.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = finalTitle;
                            youTubePlayer.cueVideo(videoId, 0);

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void dettv(String itemID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://dda571hw9backend.us-west-1.elasticbeanstalk.com/tvdetails/det/" + itemID;
        loadingDialog loadingDialog = new loadingDialog(this);
        loadingDialog.startLoadingDialog();

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingDialog.dismissDialog();
                String title1 = null;
                String overview1 = null;
                String release_date1 = null;
                String genres1 = null;
                try {
                    title1 = response.getString("title");
                    overview1 = response.getString("overview");
                    release_date1 = response.getString("release_date");
                    genres1 = response.getString("genres");

                    title.setText(title1);
                    year.setText(release_date1);
                    genres.setText(genres1);
                    overview.setText(overview1);

                    String addtext = title1 + " was added to the Watchlist";
                    String removetext = title1 + " was removed from the Watchlist";

                    addicon = findViewById(R.id.addwatchlistbutton);
                    removeicon = findViewById(R.id.removewatchlistbutton);

                    addicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addicon.setVisibility(View.INVISIBLE);
                            removeicon.setVisibility(View.VISIBLE);
                            Toast.makeText(detailspage.this, addtext, Toast.LENGTH_SHORT).show();
                        }
                    });

                    removeicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeicon.setVisibility(View.INVISIBLE);
                            addicon.setVisibility(View.VISIBLE);
                            Toast.makeText(detailspage.this, removetext, Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void picks(String itemID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://dda571hw9backend.us-west-1.elasticbeanstalk.com/moviedetails/rec/" + itemID;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() == 0){
                    rectext.setVisibility(View.INVISIBLE);
                }

                JSONObject Object = null;
                for (int i = 0; i < 10; i++) {
                    try {
                        Object = response.getJSONObject(i);
                        reclyclerViewData review = new reclyclerViewData();
                        review.setId(Object.getString("id"));
                        review.setUrl(Object.getString("poster_path"));
                        review.setContenttype(Object.getString("contenttype"));
                        picks.add(review);

                    } catch (JSONException e) {e.printStackTrace();}
                }
                setuprecyclerviewpicks(picks);

                //Log.d("tag", "onErrorResponse: " + reviewlist);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }
    private void setuprecyclerviewpicks(ArrayList<reclyclerViewData> picks) {
        horizontalAdapter adapter = new horizontalAdapter(this, picks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recommendpicks = findViewById(R.id.picks);
        recommendpicks.setLayoutManager(layoutManager);
        recommendpicks.setAdapter(adapter);
    }

    private void rev(String itemID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://dda571hw9backend.us-west-1.elasticbeanstalk.com/moviedetails/rev/" + itemID;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() == 0){
                    reviewstext.setVisibility(View.INVISIBLE);
                }

                JSONObject Object = null;
                for (int i = 0; i < 3; i++) {
                    try {
                        Object = response.getJSONObject(i);
                        reviewData review = new reviewData();
                        review.setAuthor(Object.getString("author"));
                        review.setContent(Object.getString("content"));
                        review.setRating(Object.getString("rating"));
                        reviewlist.add(review);

                    } catch (JSONException e) {e.printStackTrace();}
                }
                setuprecyclerview(reviewlist);

                //Log.d("tag", "onErrorResponse: " + reviewlist);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void setuprecyclerview(List<reviewData> reviewlist) {
        reviewsAdapter adapter = new reviewsAdapter(this, reviewlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void vid(String id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://dda571hw9backend.us-west-1.elasticbeanstalk.com/moviedetails/vid/" + id;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String title1 = null;
                try {
                    title1 = response.getString("key");
                    String finalTitle = title1;
                    ytkey.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = finalTitle;
                            youTubePlayer.cueVideo(videoId, 0);

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void det(String id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://dda571hw9backend.us-west-1.elasticbeanstalk.com/moviedetails/det/" + id;
        loadingDialog loadingDialog = new loadingDialog(this);
        loadingDialog.startLoadingDialog();

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingDialog.dismissDialog();

                String title1 = null;
                String overview1 = null;
                String release_date1 = null;
                String genres1 = null;
                try {
                    title1 = response.getString("title");
                    overview1 = response.getString("overview");
                    release_date1 = response.getString("release_date");
                    genres1 = response.getString("genres");

                    title.setText(title1);
                    year.setText(release_date1);
                    genres.setText(genres1);
                    overview.setText(overview1);

                    String addtext = title1 + " was added to the Watchlist";
                    String removetext = title1 + " was removed from the Watchlist";

                    addicon = findViewById(R.id.addwatchlistbutton);
                    removeicon = findViewById(R.id.removewatchlistbutton);

                    addicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addicon.setVisibility(View.INVISIBLE);
                            removeicon.setVisibility(View.VISIBLE);
                            Toast.makeText(detailspage.this, addtext, Toast.LENGTH_SHORT).show();
                        }
                    });

                    removeicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeicon.setVisibility(View.INVISIBLE);
                            addicon.setVisibility(View.VISIBLE);
                            Toast.makeText(detailspage.this, removetext, Toast.LENGTH_SHORT).show();
                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

}
