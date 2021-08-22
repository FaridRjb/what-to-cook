package com.faridrjb.whattocook

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.faridrjb.whattocook.data.DatabaseHelper
import com.faridrjb.whattocook.databinding.FragmentSearchBinding
import com.faridrjb.whattocook.adapters.SearchRVAdapter
import java.util.ArrayList

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    var dbHelper: DatabaseHelper? = null
    var foodList: ArrayList<Food>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())
        foodList = dbHelper!!.getFood("")
        refreshDisplay()
        binding.searchBar.backBtn.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.actionSearchToDashboard)
        }
        binding.searchBar.inputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                foodList = dbHelper!!.getFood(s.toString())
                refreshDisplay()
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun refreshDisplay() {
        binding.searchRV.adapter = SearchRVAdapter(requireContext(), requireActivity(), foodList!!)
        binding.searchRV.layoutManager = LinearLayoutManager(requireContext())
    }
}