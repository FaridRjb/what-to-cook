package com.faridrjb.whattocook;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtility {

    private Activity Activity;

    private float width;

    public ScreenUtility(Activity activity) {
        this.Activity = activity;

        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float density = activity.getResources().getDisplayMetrics().density;
        width = metrics.widthPixels / density;
    }

    public float getWidth() {
        return width;
    }
}
