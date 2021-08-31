package com.faridrjb.whattocook.adapters

import android.app.Activity
import android.content.Context
import com.faridrjb.whattocook.Food
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.faridrjb.whattocook.R
import android.widget.TextView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.faridrjb.whattocook.DashboardFragmentDirections
import java.util.ArrayList

class FavoriteFragRVAdapter(
    private val context: Context,
    val activity: Activity,
    foods: ArrayList<Food>
) :
    RecyclerView.Adapter<FavoriteFragRVAdapter.ViewHolder>() {

    private var foods = ArrayList<Food>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_food_large, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.foodName.text = foods[position].foodName
        holder.foodInits.text = foods[position].initsNeeded!!.replace("-", " \u2022 ")
        holder.foodImg.setImageResource(
            context.resources.getIdentifier(
                "drawable/" + foods[position].photo,
                null,
                context.packageName
            )
        )
        // like btn
        val preferences = context.getSharedPreferences(
            "Favorite",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = preferences.edit()
        if (preferences.getBoolean(foods[position].foodName, false)) {
            holder.likeBtn.setImageResource(R.drawable.ic_favorite_filled)
        }
        holder.likeBtn.setOnClickListener {
            if (!preferences.getBoolean(foods[position].foodName, false)) {
                editor.putBoolean(foods[position].foodName, true)
                holder.likeBtn.setImageResource(R.drawable.ic_favorite_filled)
            } else if (preferences.getBoolean(foods[position].foodName, false)) {
                editor.putBoolean(foods[position].foodName, false)
                holder.likeBtn.setImageResource(R.drawable.ic_favorite)
            }
            editor.apply()
        }
        //---------
        holder.parentLayout.setOnClickListener {
            val action =
                DashboardFragmentDirections.actionDashboardToDesc(foods[position].foodName!!)
            Navigation.findNavController(activity.findViewById(R.id.dashboardCL)).navigate(action)
        }
        if (position == foods.size - 1) {
            holder.parentLayout.setPadding(0, 0, 0, 30)
        }
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parentLayout: RelativeLayout
        var foodName: TextView
        var foodInits: TextView
        var foodImg: ImageView
        var likeBtn: ImageButton

        init {
            parentLayout = itemView.findViewById(R.id.searchItemRL)
            foodName = itemView.findViewById(R.id.searchItemTV)
            foodInits = itemView.findViewById(R.id.searchItemInitsTV)
            foodImg = itemView.findViewById(R.id.searchItemIV)
            likeBtn = itemView.findViewById(R.id.searchItemLikeBtn)
        }
    }

    init {
        this.foods = foods
    }
}