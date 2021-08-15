package com.faridrjb.whattocook.fragments.storage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.faridrjb.whattocook.R
import androidx.recyclerview.widget.RecyclerView
import com.faridrjb.whattocook.recyclerviewadapters.StorageItemsAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import java.text.Collator
import java.util.*

class OtherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.list_storage, container, false)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.itemList1)
        val itemList = ArrayList(Arrays.asList(*resources.getStringArray(R.array.others_names)))
        val collator = Collator.getInstance(Locale("fa", "IR"))
        collator.strength = Collator.PRIMARY
        Collections.sort(itemList, collator)
        val adapter = StorageItemsAdapter(requireContext(), itemList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        return rootView
    }
}