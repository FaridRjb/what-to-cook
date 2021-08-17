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
import com.faridrjb.whattocook.databinding.ActivitySearchBinding
import java.util.ArrayList

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    var dbHelper: DatabaseHelper? = null
    var foodList: ArrayList<Food>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbHelper = DatabaseHelper(this)
        foodList = dbHelper!!.getFood("")
        refreshDisplay()
        binding.searchBar.backBtn.setOnClickListener(View.OnClickListener { finish() })
        binding.searchBar.inputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                foodList = dbHelper!!.getFood(s.toString())
                refreshDisplay()
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun refreshDisplay() {
        binding.searchRV.adapter = SearchRVAdapter(this, foodList!!)
        binding.searchRV.layoutManager = LinearLayoutManager(this)
    }
}