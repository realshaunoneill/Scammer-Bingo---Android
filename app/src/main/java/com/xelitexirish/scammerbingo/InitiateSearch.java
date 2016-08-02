package com.xelitexirish.scammerbingo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;


public class InitiateSearch {

    public static void handleSearchBar(final Context context, final CardView search, final Toolbar toolbarMain, final View view, final EditText editText, final ImageView back, final ImageView clear, final DrawerLayout mDrawerLayout) {
        final Animation fade_in = AnimationUtils.loadAnimation(context.getApplicationContext(), android.R.anim.fade_in);
        final Animation fade_out = AnimationUtils.loadAnimation(context.getApplicationContext(), android.R.anim.fade_out);
        final DrawerArrowDrawable toolbarDrawable = new DrawerArrowDrawable(context);
        final ValueAnimator toolbarAnimator = ValueAnimator.ofFloat(0f, 1f);
        toolbarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                toolbarDrawable.setProgress((Float)animation.getAnimatedValue());
            }
        });

        final DrawerArrowDrawable searchbarDrawable = new DrawerArrowDrawable(context);
        final ValueAnimator searchbarAnimator = ValueAnimator.ofFloat(0f, 1f);
        searchbarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                searchbarDrawable.setProgress((Float)animation.getAnimatedValue());
            }
        });

        toolbarMain.setNavigationIcon(toolbarDrawable);
        back.setImageDrawable(searchbarDrawable);

        if (search.getVisibility() == View.VISIBLE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final Animator animatorHide = ViewAnimationUtils.createCircularReveal(search,
                        search.getWidth() - (int) convertDpToPixel(56, context),
                        toolbarMain.getHeight() / 2,
                        (float) Math.hypot(search.getWidth(), search.getHeight()),
                        0);
                animatorHide.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        editText.startAnimation(fade_out);
                        clear.startAnimation(fade_out);
                        toolbarMain.setNavigationIcon(toolbarDrawable);
                        toolbarAnimator.reverse();
                        searchbarAnimator.reverse();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.INVISIBLE);
                        search.setVisibility(View.GONE);
                        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorHide.setDuration(300);
                animatorHide.start();
            } else {
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
                view.setVisibility(View.INVISIBLE);
                search.setVisibility(View.GONE);
                toolbarMain.setNavigationIcon(toolbarDrawable);
                toolbarAnimator.reverse();
                searchbarAnimator.reverse();
                editText.startAnimation(fade_out);
                clear.startAnimation(fade_out);
                search.startAnimation(fade_out);
            }
            toolbarMain.getMenu().clear();
            toolbarMain.inflateMenu(R.menu.menu_main);
            editText.setText("");
            search.setEnabled(false);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final Animator animator = ViewAnimationUtils.createCircularReveal(search,
                        search.getWidth() - (int) convertDpToPixel(56, context),
                        toolbarMain.getHeight() / 2,
                        0,
                        (float) Math.hypot(search.getWidth(), search.getHeight()));
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        toolbarMain.getMenu().clear();
                        view.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.INVISIBLE);
                        clear.setVisibility(View.INVISIBLE);
                        toolbarAnimator.start();
                        searchbarAnimator.start();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        editText.setVisibility(View.VISIBLE);
                        clear.setVisibility(View.VISIBLE);
                        editText.startAnimation(fade_in);
                        clear.startAnimation(fade_in);
                        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                search.setVisibility(View.VISIBLE);
                if (search.getVisibility() == View.VISIBLE) {
                    animator.setDuration(300);
                    animator.start();
                    search.setEnabled(true);
                }
            } else {
                toolbarAnimator.start();
                searchbarAnimator.start();
                search.setVisibility(View.VISIBLE);
                search.setEnabled(true);
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                editText.startAnimation(fade_in);
                clear.startAnimation(fade_in);
                search.startAnimation(fade_in);
            }
        }
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }
}
