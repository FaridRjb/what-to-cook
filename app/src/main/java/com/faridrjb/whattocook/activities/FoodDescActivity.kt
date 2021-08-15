package com.faridrjb.whattocook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.faridrjb.whattocook.R;
import com.faridrjb.whattocook.recyclerviewadapters.InitsNeededRVAdapter;
import com.faridrjb.whattocook.data.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;


public class FoodDescActivity extends AppCompatActivity {

    public static final String FAVORITE_PREF = "Favorite";

    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    Bundle bundle;
    String FOOD_NAME;

    RecyclerView recyclerView;
    FloatingActionButton likeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_desc);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.tvFoodName);
        setSupportActionBar(toolbar);
        ImageButton back = findViewById(R.id.icon_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //--------

        // Food Name
        bundle = getIntent().getExtras();
        if (bundle.containsKey("name")) {
            FOOD_NAME = bundle.getString("name");
            textView.setText(FOOD_NAME);
        }
        else Toast.makeText(this, "404", Toast.LENGTH_SHORT).show();
        //----------

        refreshDisplay();

        // More
        final TextView more = findViewById(R.id.txtMore);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                recyclerView.requestLayout();
                more.setVisibility(View.INVISIBLE);
            }
        });
        //-----

        addToFavourite();
    }

    private void refreshDisplay() {
        // Image
        ImageView image = findViewById(R.id.foodImageView);
        image.setImageResource(getApplicationContext().getResources().getIdentifier("drawable/"+dbHelper.getFoodPhoto(FOOD_NAME), null, getApplicationContext().getPackageName()));
        //------

        // RecyclerView - Initials List
        ArrayList<String> initsList = new ArrayList<>(Arrays.asList(dbHelper.getFoodInits(FOOD_NAME).split("-")));
        ArrayList<String> initsAmountList = new ArrayList<>(Arrays.asList(dbHelper.getFoodInitsAmount(FOOD_NAME).split("-")));
        recyclerView = findViewById(R.id.recyclerView);
        InitsNeededRVAdapter adapter = new InitsNeededRVAdapter(this, initsList, initsAmountList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (findViewById(R.id.f_d_c_sw600dp) != null)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //-----------------------------

        // Instruction
        TextView instruction = findViewById(R.id.txtInstruction);
        instruction.setText(dbHelper.getFoodInstruc(FOOD_NAME));
        //------------
    }

    private void addToFavourite() {
        likeButton = findViewById(R.id.likeButton);
        final SharedPreferences preferences = getSharedPreferences(FAVORITE_PREF, MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        if (preferences.getBoolean(FOOD_NAME, false)) {
            likeButton.setImageResource(R.drawable.ic_favorite_filled);
        }
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! preferences.getBoolean(FOOD_NAME, false)) {
                    startCircularReveal();
                    editor.putBoolean(FOOD_NAME, true);
                    likeButton.setImageResource(R.drawable.ic_favorite_filled);
                } else if (preferences.getBoolean(FOOD_NAME, false)) {
                    editor.putBoolean(FOOD_NAME, false);
                    likeButton.setImageResource(R.drawable.ic_favorite);
                }
                editor.apply();
            }
        });
    }

    private void startCircularReveal() {
        final RelativeLayout changeableLayout = findViewById(R.id.layoutChangeable);
        int centerX = (likeButton.getRight() + likeButton.getLeft()) / 2;
        int centerY = (likeButton.getBottom() + likeButton.getTop()) / 2;
        float endRadius = (float) Math.hypot(changeableLayout.getWidth(), changeableLayout.getHeight());
        changeableLayout.setVisibility(View.VISIBLE);
        changeableLayout.setAlpha(0.0f);
        changeableLayout.animate().alpha(1.0f).setDuration(350).start();
        Animator revealAnimator = ViewAnimationUtils.createCircularReveal(changeableLayout,
                centerX, centerY, endRadius, 0);
        revealAnimator.setDuration(500);
        revealAnimator.start();
        revealAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                changeableLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void onClickWeb(View view) {
        Intent browserIntent;
        switch (view.getId()) {
            case R.id.webTV2:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://parsiday.com"));
                break;
            case R.id.webTV3:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chishi.ir"));
                break;
            default:
                return;
        }
        startActivity(browserIntent);
    }
}