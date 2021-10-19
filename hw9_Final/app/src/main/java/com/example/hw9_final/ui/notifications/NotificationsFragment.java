package com.example.hw9_final.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.example.hw9_final.ui.home.recyclerViewTop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class NotificationsFragment extends Fragment {
    private ArrayList<reclyclerViewData> watchlist;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        watchlist = new ArrayList<>();
        RecyclerView watchlistview = root.findViewById(R.id.watchilistview);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(watchlistview);

        String url3 ="http://localhost:8080/movies/trending";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonArrayRequest jsonArrayRequestmovietop = new JsonArrayRequest(Request.Method.GET, url3, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < 15; i++) {
                    try {
                        JSONObject urlObject = response.getJSONObject(i);
                        reclyclerViewData view = new reclyclerViewData();
                        view.setId(urlObject.getString("id"));
                        view.setUrl(urlObject.getString("poster_path"));
                        view.setContenttype(urlObject.getString("contenttype"));
                        view.setName(urlObject.getString("title"));
                        watchlist.add(view);
                    } catch (JSONException e) {e.printStackTrace();}
                }
                GridLayoutManager layoutManagermovietop = new GridLayoutManager(getActivity().getApplicationContext(), 3);
                watchlistview.setLayoutManager(layoutManagermovietop);
                watchlistAdapter adaptermovie = new watchlistAdapter(getActivity().getApplicationContext(), watchlist);
                watchlistview.setAdapter(adaptermovie);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequestmovietop);



        return root;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN |
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosititon = target.getAdapterPosition();

            Collections.swap(watchlist, fromPosition, toPosititon);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosititon);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
}