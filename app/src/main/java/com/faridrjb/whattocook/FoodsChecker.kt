package com.faridrjb.whattocook

import android.content.Context
import com.faridrjb.whattocook.Food
import android.content.SharedPreferences
import android.util.Log
import java.util.*

class FoodsChecker {

    private var context: Context? = null
    private var foodList = ArrayList<Food>()
    private var initsInStorage: ArrayList<String>? = null
    private val preferences: SharedPreferences? = null

    fun possibleFoods(
        context: Context?,
        initsInStorage: ArrayList<String>,
        foodList: ArrayList<Food>
    ): ArrayList<Food> {
        this.context = context
        this.initsInStorage = initsInStorage
        this.foodList = foodList
        val possibleFoods = ArrayList<Food>()
        for (i in foodList.indices) {
            if (initsInStorage.containsAll(
                    Arrays.asList(
                        *foodList[i].essInitsNeeded!!.split("-").toTypedArray()
                    )
                )
            ) {
                possibleFoods.add(foodList[i])
            }
            //            for (String init : Arrays.asList(foodList.get(i).getEssInitsNeeded().split("-"))) {
//                if (initsInStorage.contains(init)) {
//                    if (! possibleFoods.contains(foodList.get(i)))
//                        possibleFoods.add(foodList.get(i));
//                }
//            }
            // debug part
            val a = Arrays.asList(*foodList[i].essInitsNeeded!!.split("-").toTypedArray())
            for (init in a) {
                if (!initsInStorage.contains(init)) Log.i(
                    "FoodsChecker",
                    foodList[i].toString() + ": " + init
                )
            }
            //------------
        }
        Log.i("FoodsChecker", possibleFoods.size.toString())
        return possibleFoods
    }
}