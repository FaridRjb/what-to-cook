package com.faridrjb.whattocook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.faridrjb.whattocook.Food;
import com.faridrjb.whattocook.FoodsChecker;
import com.faridrjb.whattocook.R;
import com.faridrjb.whattocook.ScreenUtility;
import com.faridrjb.whattocook.data.DatabaseHelper;
import com.faridrjb.whattocook.recyclerviewadapters.PosFavRVAdapter;

import java.util.ArrayList;


public class PosFavActivity extends AppCompatActivity {

    private static final String GO_FOR = "IntentToPosFav";
    private String CAME_FOR;

    RecyclerView posFavRV;
    PosFavRVAdapter adapter;
    ArrayList<Food> foods = new ArrayList<>();

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_fav);

        posFavRV = findViewById(R.id.posFavRV);
        dbHelper = new DatabaseHelper(this);
        TextView title = findViewById(R.id.posFavTitle);

        CAME_FOR = getIntent().getStringExtra(GO_FOR);
        if (CAME_FOR == null) return;

        if (CAME_FOR.equals("PossibleFoods")) {
            title.setText("چیزایی که میتونی درست کنی");
            loadPossibles();
        } else if (CAME_FOR.equals("Favorite")) {
            title.setText("مورد علاقه ها");
            loadFavorites();
        }

        adapter = new PosFavRVAdapter(this, foods);
        posFavRV.setAdapter(adapter);
        ScreenUtility screenUtility = new ScreenUtility(this);
        int spanCount = (int) (screenUtility.getWidth() / 132);
        posFavRV.setLayoutManager(new GridLayoutManager(this, spanCount));

        (findViewById(R.id.icon_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CAME_FOR.equals("Favorite")) {
            loadFavorites();
            adapter.notifyDataSetChanged();
        }
    }

    private void loadPossibles() {
        ArrayList<String> allInits = new ArrayList<>();
        String[] a = getResources().getStringArray(R.array.hoboobaat_names);
        String[] b = getResources().getStringArray(R.array.ghallaat_names);
        String[] c = getResources().getStringArray(R.array.labaniaat_names);
        String[] d = getResources().getStringArray(R.array.miveh_sabzi_names);
        String[] e = getResources().getStringArray(R.array.chaashni_names);
        String[] f = getResources().getStringArray(R.array.kareh_roghan_names);
        String[] g = getResources().getStringArray(R.array.goosht_names);
        String[] h = getResources().getStringArray(R.array.others_names);
        String[][] total = {a, b, c, d, e, f, g, h};
        for (String[] category : total) {
            for (String item : category) {
                allInits.add(item);
            }
        }
        SharedPreferences preferences = getSharedPreferences("Storage", MODE_PRIVATE);
        ArrayList<String> initsInStorage = new ArrayList<>();
        for (String item : allInits) {
            if (preferences.getBoolean(item, false)) {
                initsInStorage.add(item);
            }
        }
        foods.clear();
        foods = new FoodsChecker().possibleFoods(this, initsInStorage, dbHelper.getFood(""));
    }

    private void loadFavorites() {
        SharedPreferences preferences = getSharedPreferences("Favorite", Context.MODE_PRIVATE);
        ArrayList<Food> foodList = new ArrayList<>();
        foodList = dbHelper.getFood("");
        foods.clear();
        for (int i = 0; i < foodList.size(); i++) {
            if (preferences.getBoolean(foodList.get(i).getFoodName(), false)) {
                foods.add(foodList.get(i));
            }
        }
    }
}