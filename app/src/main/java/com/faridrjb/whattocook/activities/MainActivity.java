package com.faridrjb.whattocook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.faridrjb.whattocook.Food;
import com.faridrjb.whattocook.FoodsChecker;
import com.faridrjb.whattocook.R;
import com.faridrjb.whattocook.data.DatabaseHelper;
import com.faridrjb.whattocook.fragments.FavoriteFragment;
import com.faridrjb.whattocook.recyclerviewadapters.PossibleRVAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    TextInputEditText inputSearch;
    FloatingActionButton floatingButton;
    Toolbar toolbar;
    ImageButton infoBtn;
    TextView posMore;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB
        db = new DatabaseHelper(this);
        try {
            db.createDataBase();
        } catch (IOException e) {
            Toast.makeText(this, "Wrong 1", Toast.LENGTH_SHORT).show();
        }
        db.openDataBase();
        //---

        // New Comer
        SharedPreferences newComerPref = getSharedPreferences("First Time", MODE_PRIVATE);
        if (newComerPref.getBoolean("First time", true)) {

            startActivity(new Intent(MainActivity.this, IntroSliderActivity.class));
            finish();
        }
        //----------

        // Toolbar
        toolbar = findViewById(R.layout.app_bar_main);
        setSupportActionBar(toolbar);
        infoBtn = findViewById(R.id.infoBtn);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });
        //--------

        // Init
        inputSearch = findViewById(R.id.inputSearch);
        floatingButton = findViewById(R.id.floatingButton);
        posMore = findViewById(R.id.posMore);
        //-----

        // Favorite Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.favFragContainer, new FavoriteFragment()).commit();
        //------------------

        inputSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                    inputSearch.clearFocus();
                }
            }
        });
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCircularReveal();
                floatingButton.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoom_out));
                startActivity(new Intent(MainActivity.this, StorageActivity.class));
            }
        });
        posMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PosFavActivity.class);
                intent.putExtra("IntentToPosFav", "PossibleFoods");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        loadPossibleList();
        super.onResume();
    }

    private void loadPossibleList() {
        ArrayList<String> allInits = new ArrayList<>();
        String[] a = getResources().getStringArray(R.array.hoboobaat_names);
        String[] b = getResources().getStringArray(R.array.ghallaat_names);
        String[] c = getResources().getStringArray(R.array.labaniaat_names);
        String[] d = getResources().getStringArray(R.array.miveh_sabzi_names);
        String[] e = getResources().getStringArray(R.array.chaashni_names);
        String[] f = getResources().getStringArray(R.array.kareh_roghan_names);
        String[] g = getResources().getStringArray(R.array.protein_names);
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
        ArrayList<Food> possibleFoods = new ArrayList<>();
        ArrayList<Food> firstFivePossibleFoods = new ArrayList<>();
        possibleFoods = new FoodsChecker().possibleFoods(this, initsInStorage, db.getFood(""));
        for (int i = 0; i < possibleFoods.size(); i++) { // just show first 5 items
            firstFivePossibleFoods.add(possibleFoods.get(i));
            if (firstFivePossibleFoods.size() == 5) break;
        }
        RecyclerView resList = findViewById(R.id.possibleList);
        PossibleRVAdapter possiblervAdapter = new PossibleRVAdapter(this, firstFivePossibleFoods);
        resList.setAdapter(possiblervAdapter);
        resList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void startCircularReveal() {
        final RelativeLayout revealLayout = findViewById(R.id.layoutReveal);
            int centerX = (floatingButton.getRight() + floatingButton.getLeft()) / 2;
            int centerY = (floatingButton.getBottom() + floatingButton.getTop()) / 2;
            float endRadius = (float) Math.hypot(revealLayout.getWidth(), revealLayout.getHeight());
        revealLayout.setVisibility(View.VISIBLE);
        Animator revealAnimator = ViewAnimationUtils.createCircularReveal(revealLayout,
                centerX, centerY, 0, endRadius);
        revealAnimator.setDuration(200).start();
        revealAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                revealLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}