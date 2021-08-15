package com.faridrjb.whattocook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;

import com.faridrjb.whattocook.Food;
import com.faridrjb.whattocook.recyclerviewadapters.SearchRVAdapter;
import com.faridrjb.whattocook.R;
import com.faridrjb.whattocook.data.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    ImageButton back;
    TextInputEditText inputSearch;
    ArrayList<Food> foodList;
    RecyclerView searchList;
    SearchRVAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        searchList = findViewById(R.id.searchList);
        foodList = dbHelper.getFood("");

        refreshDisplay();

        back = findViewById(R.id.icon_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inputSearch = findViewById(R.id.inputSearch);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                foodList = dbHelper.getFood(String.valueOf(s));
                refreshDisplay();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void refreshDisplay() {
        rvAdapter = new SearchRVAdapter(this, foodList);
        searchList.setAdapter(rvAdapter);
        searchList.setLayoutManager(new LinearLayoutManager(this));
    }
}