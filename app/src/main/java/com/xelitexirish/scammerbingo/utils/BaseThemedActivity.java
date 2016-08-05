package com.xelitexirish.scammerbingo.utils;

import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;

import com.afollestad.appthemeengine.ATEActivity;

public class BaseThemedActivity extends ATEActivity {

    @Nullable
    @Override
    public final String getATEKey() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dark_theme", false) ?
                "dark_theme" : "light_theme";
    }
}