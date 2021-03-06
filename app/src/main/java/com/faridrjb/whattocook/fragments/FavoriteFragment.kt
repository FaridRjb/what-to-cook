package com.faridrjb.whattocook.fragments

import android.annotation.SuppressLint
import android.content.Context
import com.faridrjb.whattocook.Food
import com.faridrjb.whattocook.adapters.FavoriteFragRVAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.faridrjb.whattocook.data.DatabaseHelper
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.faridrjb.whattocook.DashboardFragmentDirections
import com.faridrjb.whattocook.databinding.FragmentFavoriteBinding
import java.util.ArrayList

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    var foodList: ArrayList<Food>? = null
    var favorites: ArrayList<Food>? = null
    var adapter: FavoriteFragRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favMore.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardToPosFav("Favorite")
            Navigation.findNavController(binding.root).navigate(action)
        }
        val dbHelper = DatabaseHelper(requireContext())
        foodList = ArrayList()
        foodList = dbHelper.getFood("")
        favorites = ArrayList()
        loadFavorites()
        adapter = FavoriteFragRVAdapter(requireContext(), requireActivity(), favorites!!)
        binding.favRV.adapter = adapter
        binding.favRV.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        favorites!!.clear()
        loadFavorites()
        adapter!!.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadFavorites() {
        val preferences = requireContext().getSharedPreferences("Favorite", Context.MODE_PRIVATE)
        binding.favFragNotFound.visibility = View.GONE
        for (i in foodList!!.indices) {
            if (preferences.getBoolean(foodList!![i].foodName, false)) {
                favorites!!.add(foodList!![i])
                if (favorites!!.size == 5) break // just show 5 items
            }
        }
        if (favorites!!.size == 0) {
            binding.favFragNotFound.visibility = View.VISIBLE
        }
    }
}