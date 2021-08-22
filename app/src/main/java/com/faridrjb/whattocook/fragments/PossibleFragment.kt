package com.faridrjb.whattocook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.faridrjb.whattocook.DashboardFragmentDirections
import com.faridrjb.whattocook.Food
import com.faridrjb.whattocook.FoodsChecker
import com.faridrjb.whattocook.R
import com.faridrjb.whattocook.data.DatabaseHelper
import com.faridrjb.whattocook.databinding.FragmentPossibleBinding
import com.faridrjb.whattocook.adapters.PossibleRVAdapter
import java.util.ArrayList

class PossibleFragment : Fragment() {

    private var _binding: FragmentPossibleBinding? = null
    private val binding get() = _binding!!

    var db: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        db = DatabaseHelper(requireContext())
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPossibleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.posMore.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardToPosFav("PossibleFoods")
            Navigation.findNavController(binding.root).navigate(action)
        }
        loadPossibleList()
    }

    override fun onResume() {
        super.onResume()
        loadPossibleList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadPossibleList() {
        binding.posNotFoundTV.visibility = View.GONE
        val allInits = ArrayList<String>()
        val a = resources.getStringArray(R.array.hoboobaat_names)
        val b = resources.getStringArray(R.array.ghallaat_names)
        val c = resources.getStringArray(R.array.labaniaat_names)
        val d = resources.getStringArray(R.array.miveh_sabzi_names)
        val e = resources.getStringArray(R.array.chaashni_names)
        val f = resources.getStringArray(R.array.kareh_roghan_names)
        val g = resources.getStringArray(R.array.protein_names)
        val h = resources.getStringArray(R.array.others_names)
        val j = resources.getStringArray(R.array.khoshkbar_names)
        val total = arrayOf(a, b, c, d, e, f, g, h, j)
        for (category in total) {
            for (item in category) {
                allInits.add(item)
            }
        }
        val preferences = activity?.getSharedPreferences("Storage", AppCompatActivity.MODE_PRIVATE)
        val initsInStorage = ArrayList<String>()
        for (item in allInits) {
            if (preferences!!.getBoolean(item, false)) {
                initsInStorage.add(item)
            }
        }
        var possibleFoods = ArrayList<Food>()
        val firstFivePossibleFoods = ArrayList<Food>()
        possibleFoods =
            FoodsChecker().possibleFoods(requireContext(), initsInStorage, db!!.getFood(""))
        for (i in possibleFoods.indices) { // just show first 5 items
            firstFivePossibleFoods.add(possibleFoods[i])
            if (firstFivePossibleFoods.size == 5) break
        }
        if (firstFivePossibleFoods.size == 0) binding.posNotFoundTV.visibility = View.VISIBLE
        binding.possibleRV.adapter =
            PossibleRVAdapter(requireContext(), requireActivity(), firstFivePossibleFoods)
        binding.possibleRV.layoutManager = LinearLayoutManager(requireContext())
    }
}