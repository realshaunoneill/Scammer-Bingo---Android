package com.xelitexirish.scammerbingo.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.view.View;

public class ViewUtils {

    public static int DEFAULT_ANIMATION_DURATION = 150;

    //This class should not be instantiated
    private ViewUtils() {}

    @SuppressWarnings("deprecation")
    public static void setBackground(View view, Drawable drawable){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
            view.setBackgroundDrawable(drawable);
        }else {
            view.setBackground(drawable);
        }
    }

    public static void setBackgroundColorWithAnimation(View view, int fromColor, int toColor){
        ColorDrawable[] colors = {new ColorDrawable(fromColor), new ColorDrawable(toColor)};
        TransitionDrawable transitionDrawable = new TransitionDrawable(colors);
        setBackground(view, transitionDrawable);
        transitionDrawable.startTransition(DEFAULT_ANIMATION_DURATION);
    }
}
