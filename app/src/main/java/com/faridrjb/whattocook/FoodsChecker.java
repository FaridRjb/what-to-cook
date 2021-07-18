package com.faridrjb.whattocook;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.faridrjb.whattocook.activities.MainActivity;
import com.faridrjb.whattocook.data.DatabaseHelper;

import java.util.*;

public class FoodsChecker {

    private Context context;
    private ArrayList<Food> foodList = new ArrayList<>();
    private ArrayList<String> initsInStorage;

    private SharedPreferences preferences;

    public ArrayList<Food> possibleFoods(Context context, ArrayList<String> initsInStorage, ArrayList<Food> foodList) {
        this.context = context;
        this.initsInStorage = initsInStorage;
        this.foodList = foodList;
        ArrayList<Food> possibleFoods = new ArrayList<>();
        for (int i = 0; i < foodList.size(); i++) {
            if (initsInStorage.containsAll(Arrays.asList(foodList.get(i).getInitsNeeded().split("-")))) {
                possibleFoods.add(foodList.get(i));
            }
            // debug part
            List<String> a = Arrays.asList(foodList.get(i).getInitsNeeded().split("-"));
            for (String init : a) {
                if (! initsInStorage.contains(init))
                    Log.i("FoodsChecker", foodList.get(i) + ": " + init);
            }
            //------------
        }
        Log.i("FoodsChecker", String.valueOf(possibleFoods.size()));
        return possibleFoods;
    }
}
