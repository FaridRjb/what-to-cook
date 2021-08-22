package com.faridrjb.whattocook.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.faridrjb.whattocook.adapters.StorageItemsAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.faridrjb.whattocook.databinding.FragmentInitsCategBinding
import java.text.Collator
import java.util.*

class InitsCategFragment(var itemsArrayID: Int) : Fragment() {

    private var _binding: FragmentInitsCategBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInitsCategBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemList = ArrayList(Arrays.asList(*resources.getStringArray(itemsArrayID)))
        val collator = Collator.getInstance(Locale("fa", "IR"))
        collator.strength = Collator.PRIMARY
        Collections.sort(itemList, collator)
        val adapter = StorageItemsAdapter(requireContext(), itemList)
        binding.initsCategRV.adapter = adapter
        binding.initsCategRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}