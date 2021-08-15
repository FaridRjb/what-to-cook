package com.faridrjb.whattocook

import android.app.Activity
import android.util.DisplayMetrics

class ScreenUtility(activity: Activity) {

    val width: Float

    init {
        val display = activity.windowManager.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        val density = activity.resources.displayMetrics.density
        width = metrics.widthPixels / density
    }
}