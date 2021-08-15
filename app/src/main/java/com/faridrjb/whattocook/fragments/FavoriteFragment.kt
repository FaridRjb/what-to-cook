package com.faridrjb.whattocook.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faridrjb.whattocook.Food;
import com.faridrjb.whattocook.R;
import com.faridrjb.whattocook.activities.MainActivity;
import com.faridrjb.whattocook.activities.PosFavActivity;
import com.faridrjb.whattocook.data.DatabaseHelper;
import com.faridrjb.whattocook.recyclerviewadapters.FavoriteFragRVAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    ArrayList<Food> foodList;
    ArrayList<Food> favorites;
    FavoriteFragRVAdapter adapter;
    TextView notFound;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        TextView favMore = rootView.findViewById(R.id.favMore);
        favMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PosFavActivity.class);
                intent.putExtra("IntentToPosFav", "Favorite");
                startActivity(intent);
            }
        });

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        notFound = rootView.findViewById(R.id.favFragNotFound);
        foodList = new ArrayList<>();
        foodList = dbHelper.getFood("");
        favorites = new ArrayList<>();
        loadFavorites();
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView2);
        adapter = new FavoriteFragRVAdapter(getContext(), favorites);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        favorites.clear();
        loadFavorites();
        adapter.notifyDataSetChanged();
    }

    private void loadFavorites() {
        SharedPreferences preferences = getContext().getSharedPreferences("Favorite", Context.MODE_PRIVATE);
        notFound.setVisibility(View.GONE);
        for (int i = 0; i < foodList.size(); i++) {
            if (preferences.getBoolean(foodList.get(i).getFoodName(), false)) {
                favorites.add(foodList.get(i));
                if (favorites.size() == 5) break; // just show 5 items
            }
        }
        if (favorites.size() == 0) {
            notFound.setVisibility(View.VISIBLE);
        }
    }
}
