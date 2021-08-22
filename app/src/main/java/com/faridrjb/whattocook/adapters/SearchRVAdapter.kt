package com.faridrjb.whattocook.adapters

import android.app.Activity
import android.content.Context
import com.faridrjb.whattocook.Food
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.faridrjb.whattocook.R
import android.content.Intent
import android.widget.TextView
import com.makeramen.roundedimageview.RoundedImageView
import android.widget.RelativeLayout
import android.view.View
import androidx.navigation.Navigation
import com.faridrjb.whattocook.DashboardFragmentDirections
import com.faridrjb.whattocook.SearchFragmentDirections
import java.util.ArrayList

class SearchRVAdapter(private val context: Context, val activity: Activity, foods: ArrayList<Food>) :
    RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {

    private var foods = ArrayList<Food>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.foodName.text = foods[position].foodName
        holder.foodImg.setImageResource(
            context.resources.getIdentifier(
                "drawable/" + foods[position].photo,
                null,
                context.packageName
            )
        )
        holder.parentLayout.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchToDesc(foods[position].foodName!!)
            Navigation.findNavController(activity.findViewById(R.id.searchCL)).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parentLayout: RelativeLayout
        var foodName: TextView
        var foodImg: RoundedImageView

        init {
            parentLayout = itemView.findViewById(R.id.layoutFoodItem)
            foodName = itemView.findViewById(R.id.txtFood)
            foodImg = itemView.findViewById(R.id.imgFood)
        }
    }

    init {
        this.foods = foods
    }
}