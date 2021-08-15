package com.faridrjb.whattocook.fragments

import android.annotation.SuppressLint
import android.content.Context
import com.faridrjb.whattocook.Food
import com.faridrjb.whattocook.recyclerviewadapters.FavoriteFragRVAdapter
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.faridrjb.whattocook.R
import android.content.Intent
import com.faridrjb.whattocook.activities.PosFavActivity
import com.faridrjb.whattocook.data.DatabaseHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.SharedPreferences
import android.view.View
import androidx.fragment.app.Fragment
import java.util.ArrayList

class FavoriteFragment : Fragment() {

    var foodList: ArrayList<Food>? = null
    var favorites: ArrayList<Food>? = null
    var adapter: FavoriteFragRVAdapter? = null
    var notFound: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite, container, false)
        val favMore = rootView.findViewById<TextView>(R.id.favMore)
        favMore.setOnClickListener {
            val intent = Intent(activity, PosFavActivity::class.java)
            intent.putExtra("IntentToPosFav", "Favorite")
            startActivity(intent)
        }
        val dbHelper = DatabaseHelper(context)
        notFound = rootView.findViewById(R.id.favFragNotFound)
        foodList = ArrayList()
        foodList = dbHelper.getFood("")
        favorites = ArrayList()
        loadFavorites()
        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView2)
        adapter = FavoriteFragRVAdapter(requireContext(), favorites!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        return rootView
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        favorites!!.clear()
        loadFavorites()
        adapter!!.notifyDataSetChanged()
    }

    private fun loadFavorites() {
        val preferences = requireContext().getSharedPreferences("Favorite", Context.MODE_PRIVATE)
        notFound!!.visibility = View.GONE
        for (i in foodList!!.indices) {
            if (preferences.getBoolean(foodList!![i].foodName, false)) {
                favorites!!.add(foodList!![i])
                if (favorites!!.size == 5) break // just show 5 items
            }
        }
        if (favorites!!.size == 0) {
            notFound!!.visibility = View.VISIBLE
        }
    }
}