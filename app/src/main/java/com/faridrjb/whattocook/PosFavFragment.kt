package com.faridrjb.whattocook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.faridrjb.whattocook.data.DatabaseHelper
import com.faridrjb.whattocook.databinding.FragmentPosFavBinding
import com.faridrjb.whattocook.adapters.PosFavRVAdapter
import java.util.ArrayList

class PosFavFragment : Fragment() {

    private var _binding: FragmentPosFavBinding? = null
    private val binding get() = _binding!!

    val args: PosFavFragmentArgs by navArgs()
    private var CAME_FOR: String? = null
    var adapter: PosFavRVAdapter? = null
    var foods = ArrayList<Food>()
    var dbHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPosFavBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())
        dbHelper!!.openDataBase()
        CAME_FOR = args.cameFor
        if (CAME_FOR == null) return
        if (CAME_FOR == "PossibleFoods") {
            binding.posFavTitle.text = "چیزایی که میتونی درست کنی"
            loadPossibles()
        } else if (CAME_FOR == "Favorite") {
            binding.posFavTitle.text = "مورد علاقه ها"
            loadFavorites()
        }
        adapter = PosFavRVAdapter(requireContext(), requireActivity(), foods)
        binding.posFavRV.adapter = adapter
        val screenUtility = ScreenUtility(requireActivity())
        val spanCount = (screenUtility.width / 132).toInt()
        binding.posFavRV.layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.backBtn.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.actionPosFavToDashboard)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        if (CAME_FOR == "Favorite") {
            loadFavorites()
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadPossibles() {
        binding.notFoundTV.visibility = View.GONE
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
        val preferences =
            requireContext().getSharedPreferences("Storage", AppCompatActivity.MODE_PRIVATE)
        val initsInStorage = ArrayList<String>()
        for (item in allInits) {
            if (preferences.getBoolean(item, false)) {
                initsInStorage.add(item)
            }
        }
        foods.clear()
        foods =
            FoodsChecker().possibleFoods(requireContext(), initsInStorage, dbHelper!!.getFood(""))
        if (foods.size == 0) binding.notFoundTV.visibility = View.VISIBLE
    }

    private fun loadFavorites() {
        binding.notFoundTV.visibility = View.GONE
        val preferences =
            requireContext().getSharedPreferences("Favorite", AppCompatActivity.MODE_PRIVATE)
        var foodList = ArrayList<Food>()
        foodList = dbHelper!!.getFood("")
        foods.clear()
        for (i in foodList.indices) {
            if (preferences.getBoolean(foodList[i].foodName, false)) {
                foods.add(foodList[i])
            }
        }
        if (foods.size == 0) binding.notFoundTV.visibility = View.VISIBLE
    }
}