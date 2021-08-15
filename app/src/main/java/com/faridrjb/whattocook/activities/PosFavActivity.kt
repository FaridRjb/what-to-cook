package com.faridrjb.whattocook.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.faridrjb.whattocook.recyclerviewadapters.PosFavRVAdapter
import com.faridrjb.whattocook.Food
import com.faridrjb.whattocook.data.DatabaseHelper
import android.os.Bundle
import com.faridrjb.whattocook.R
import android.widget.TextView
import com.faridrjb.whattocook.activities.PosFavActivity
import com.faridrjb.whattocook.ScreenUtility
import androidx.recyclerview.widget.GridLayoutManager
import android.content.SharedPreferences
import android.view.View
import com.faridrjb.whattocook.FoodsChecker
import java.util.ArrayList

class PosFavActivity : AppCompatActivity() {

    private var CAME_FOR: String? = null
    var posFavRV: RecyclerView? = null
    var adapter: PosFavRVAdapter? = null
    var foods = ArrayList<Food>()
    var dbHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pos_fav)

        posFavRV = findViewById(R.id.posFavRV)
        dbHelper = DatabaseHelper(this)
        dbHelper!!.openDataBase()
        val title = findViewById<TextView>(R.id.posFavTitle)
        CAME_FOR = intent.getStringExtra(GO_FOR)
        if (CAME_FOR == null) return
        if (CAME_FOR == "PossibleFoods") {
            title.text = "چیزایی که میتونی درست کنی"
            loadPossibles()
        } else if (CAME_FOR == "Favorite") {
            title.text = "مورد علاقه ها"
            loadFavorites()
        }
        adapter = PosFavRVAdapter(this, foods)
        posFavRV!!.adapter = adapter
        val screenUtility = ScreenUtility(this)
        val spanCount = (screenUtility.width / 132).toInt()
        posFavRV!!.layoutManager = GridLayoutManager(this, spanCount)
        findViewById<View>(R.id.icon_back).setOnClickListener { finish() }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        if (CAME_FOR == "Favorite") {
            loadFavorites()
            adapter!!.notifyDataSetChanged()
        }
    }

    private fun loadPossibles() {
        val posNotFoundTV = findViewById<TextView>(R.id.notFoundTV)
        posNotFoundTV.visibility = View.GONE
        val allInits = ArrayList<String>()
        val a = resources.getStringArray(R.array.hoboobaat_names)
        val b = resources.getStringArray(R.array.ghallaat_names)
        val c = resources.getStringArray(R.array.labaniaat_names)
        val d = resources.getStringArray(R.array.miveh_sabzi_names)
        val e = resources.getStringArray(R.array.chaashni_names)
        val f = resources.getStringArray(R.array.kareh_roghan_names)
        val g = resources.getStringArray(R.array.protein_names)
        val h = resources.getStringArray(R.array.others_names)
        val total = arrayOf(a, b, c, d, e, f, g, h)
        for (category in total) {
            for (item in category) {
                allInits.add(item)
            }
        }
        val preferences = getSharedPreferences("Storage", MODE_PRIVATE)
        val initsInStorage = ArrayList<String>()
        for (item in allInits) {
            if (preferences.getBoolean(item, false)) {
                initsInStorage.add(item)
            }
        }
        foods.clear()
        foods = FoodsChecker().possibleFoods(this, initsInStorage, dbHelper!!.getFood(""))
        if (foods.size == 0) posNotFoundTV.visibility = View.VISIBLE
    }

    private fun loadFavorites() {
        val favNotFoundTV = findViewById<TextView>(R.id.notFoundTV)
        favNotFoundTV.visibility = View.GONE
        val preferences = getSharedPreferences("Favorite", MODE_PRIVATE)
        var foodList = ArrayList<Food>()
        foodList = dbHelper!!.getFood("")
        foods.clear()
        for (i in foodList.indices) {
            if (preferences.getBoolean(foodList[i].foodName, false)) {
                foods.add(foodList[i])
            }
        }
        if (foods.size == 0) favNotFoundTV.visibility = View.VISIBLE
    }

    companion object {
        private const val GO_FOR = "IntentToPosFav"
    }
}