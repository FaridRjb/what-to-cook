package com.faridrjb.whattocook.recyclerviewadapters

import android.content.Context
import com.faridrjb.whattocook.Food
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.faridrjb.whattocook.R
import android.content.Intent
import com.faridrjb.whattocook.activities.FoodDescActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import com.makeramen.roundedimageview.RoundedImageView
import android.widget.RelativeLayout
import android.content.SharedPreferences
import android.view.View
import android.widget.CheckBox
import java.util.ArrayList

class InitsNeededRVAdapter(
    private val context: Context,
    initsNames: ArrayList<String>,
    initsAmount: ArrayList<String>
) : RecyclerView.Adapter<InitsNeededRVAdapter.ViewHolder>() {
    private var initsNames = ArrayList<String>()
    private var initsAmount = ArrayList<String>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_inits, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initName.text = initsNames[position]
        holder.initAmount.text = initsAmount[position]
    }

    override fun getItemCount(): Int {
        return initsNames.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parentLayout: RelativeLayout
        var initName: TextView
        var initAmount: TextView

        init {
            parentLayout = itemView.findViewById(R.id.layoutInit)
            initName = itemView.findViewById(R.id.txtInit)
            initAmount = itemView.findViewById(R.id.txtAmount)
        }
    }

    init {
        this.initsNames = initsNames
        this.initsAmount = initsAmount
    }
}