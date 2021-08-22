package com.faridrjb.whattocook.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.faridrjb.whattocook.R
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.CheckBox
import java.util.ArrayList

class StorageItemsAdapter(context: Context, itemList: ArrayList<String>) :
    RecyclerView.Adapter<StorageItemsAdapter.ViewHolder>() {

    private var itemList = ArrayList<String>()
    private val preferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_storage, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkBox.text = itemList[position]
        holder.checkBox.isChecked = preferences.getBoolean(itemList[position], false)
        holder.checkBox.setOnClickListener {
            Log.i("Shit", "onClick")
            editor.putBoolean(
                itemList[position],
                !preferences.getBoolean(holder.checkBox.text.toString(), false)
            )
            editor.commit()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox

        init {
            checkBox = itemView.findViewById(R.id.checkBox)
        }
    }

    init {
        this.itemList = itemList
        preferences = context.getSharedPreferences("Storage", Context.MODE_PRIVATE)
        editor = preferences.edit()
    }
}