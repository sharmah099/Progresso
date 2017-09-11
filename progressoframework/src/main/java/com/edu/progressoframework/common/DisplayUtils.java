package com.edu.progressoframework.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayUtils
{
    public static float spToPx(Context context, Float sp)
    {
        return dpToPx(context, sp);
    }
    
    public static float dpToPx(Context context, Float dp)
    {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return dp * scaledDensity;
    }

    /**
     * Function is used to check whether the device is tablet or not
     */
    public static boolean isDeviceIsTablet(Context context)
    {
        boolean isTablet = false;
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);

        if (diagonalInches >= 6.5) {
            isTablet = true;
        }
        return isTablet;
    }
}
