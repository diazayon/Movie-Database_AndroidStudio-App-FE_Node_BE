package com.example.hw9_final.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
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
import com.example.hw9_final.R;
import com.example.hw9_final.ui.home.reclyclerViewData;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    private ArrayList<reclyclerViewData> searchresults;
    private String url ="http://localhost:8080/query/";

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchresults = new ArrayList<>();
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        androidx.appcompat.widget.SearchView searchView = root.findViewById(R.id.searchview);
        searchView.setQueryHint("Search movies and TV");
        //searchView.setIconified(false);
        searchView.onActionViewExpanded();


        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));


        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String url_new = url + newText;
                Log.d("search", "onQueryTextChange: " + url_new);
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_new, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        searchresults.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject urlObject = response.getJSONObject(i);
                                reclyclerViewData view = new reclyclerViewData();
                                view.setId(urlObject.getString("id"));
                                view.setUrl(urlObject.getString("backdrop_path"));
                                view.setContenttype(urlObject.getString("media_type"));
                                view.setRating(urlObject.getString("rating"));
                                view.setName(urlObject.getString("name"));
                                view.setDate(urlObject.getString("date"));

                                String test = view.getName();
                                searchresults.add(view);
                                Log.d("search", "onQueryTextChange: " + test);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
                        recyclerView.setLayoutManager(layoutManager);
                        RecyclerViewSearch adapter = new RecyclerViewSearch(getActivity().getApplicationContext(), searchresults);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("tag", "onErrorResponse: here" + error.getMessage());
                        searchresults.clear();
                        reclyclerViewData view = new reclyclerViewData();
                        String no = "No results found.";
                        view.setName(no);

                        searchresults.add(view);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
                        recyclerView.setLayoutManager(layoutManager);
                        RecyclerViewSearchNo adapter = new RecyclerViewSearchNo(getActivity().getApplicationContext(), searchresults);
                        recyclerView.setAdapter(adapter);
                    }
                });
                queue.add(jsonArrayRequest);
                return false;
            }
        });

        return root;
    }
}