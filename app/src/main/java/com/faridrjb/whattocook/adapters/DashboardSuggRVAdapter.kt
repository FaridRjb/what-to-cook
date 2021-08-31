package com.faridrjb.whattocook.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.faridrjb.whattocook.DashboardFragmentDirections
import com.faridrjb.whattocook.Food
import com.faridrjb.whattocook.R
import com.makeramen.roundedimageview.RoundedImageView
import java.util.ArrayList

class DashboardSuggRVAdapter(private val context: Context, val activity: Activity,val titles: List<String>, val images: List<Int>) :
    RecyclerView.Adapter<DashboardSuggRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard_sugg, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.suggTV.text = titles[position]
        holder.suggIV.setImageResource(images[position])
        holder.suggCL.setOnClickListener {
            //TODO("go to that page")
        }
        if (position == 0) {
            holder.suggCL.setPadding(0, 0, 30, 0)
        } else if (position == titles.size-1) {
            holder.suggCL.setPadding(30, 0, 0, 0)
        }
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var suggCL: ConstraintLayout
        var suggTV: TextView
        var suggIV: ImageView

        init {
            suggCL = itemView.findViewById(R.id.dashboardSuggCL)
            suggTV = itemView.findViewById(R.id.dashboardSuggTV)
            suggIV = itemView.findViewById(R.id.dashboardSuggIV)
        }
    }
}