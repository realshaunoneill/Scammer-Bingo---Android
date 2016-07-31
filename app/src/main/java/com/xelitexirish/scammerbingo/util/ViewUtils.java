package com.xelitexirish.scammerbingo.util;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewUtils {

    public static int DEFAULT_ANIMATION_DURATION = 150;

    //This class should not be instantiated
    private ViewUtils() {
    }

    @SuppressWarnings("deprecation")
    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    public static void setBackgroundColorWithAnimation(View view, int fromColor, int toColor) {
        ColorDrawable[] colors = {new ColorDrawable(fromColor), new ColorDrawable(toColor)};
        TransitionDrawable transitionDrawable = new TransitionDrawable(colors);
        setBackground(view, transitionDrawable);
        transitionDrawable.startTransition(DEFAULT_ANIMATION_DURATION);
    }

    public static void setImageBitmapWithAnimation(ImageView imageView, Bitmap bitmap) {
        final TransitionDrawable transitionDrawable =
                new TransitionDrawable(new Drawable[]{
                        new ColorDrawable(Color.TRANSPARENT),
                        new BitmapDrawable(imageView.getContext().getResources(), bitmap)
                });
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(DEFAULT_ANIMATION_DURATION);

    }

    public static void setTextColorWithAnimation(final TextView textView, int fromColor, int toColor) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(fromColor, toColor);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setDuration(DEFAULT_ANIMATION_DURATION);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setTextColor((int) animation.getAnimatedValue());
            }
        });
        anim.start();
    }

}