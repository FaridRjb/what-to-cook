package com.faridrjb.whattocook.activities

import androidx.appcompat.app.AppCompatActivity
import com.faridrjb.whattocook.data.DatabaseHelper
import android.database.sqlite.SQLiteDatabase
import android.widget.ImageButton
import com.google.android.material.textfield.TextInputEditText
import com.faridrjb.whattocook.Food
import androidx.recyclerview.widget.RecyclerView
import com.faridrjb.whattocook.recyclerviewadapters.SearchRVAdapter
import android.os.Bundle
import com.faridrjb.whattocook.R
import android.text.TextWatcher
import android.text.Editable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.ArrayList

class SearchActivity : AppCompatActivity() {

    var dbHelper: DatabaseHelper? = null
    var db: SQLiteDatabase? = null
    var back: ImageButton? = null
    var inputSearch: TextInputEditText? = null
    var foodList: ArrayList<Food>? = null
    var searchList: RecyclerView? = null
    var rvAdapter: SearchRVAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        dbHelper = DatabaseHelper(this)
        db = dbHelper!!.readableDatabase
        searchList = findViewById(R.id.searchList)
        foodList = dbHelper!!.getFood("")
        refreshDisplay()
        back = findViewById(R.id.icon_back)
        back!!.setOnClickListener(View.OnClickListener { finish() })
        inputSearch = findViewById(R.id.inputSearch)
        inputSearch!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                foodList = dbHelper!!.getFood(s.toString())
                refreshDisplay()
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun refreshDisplay() {
        rvAdapter = SearchRVAdapter(this, foodList!!)
        searchList!!.adapter = rvAdapter
        searchList!!.layoutManager = LinearLayoutManager(this)
    }
}