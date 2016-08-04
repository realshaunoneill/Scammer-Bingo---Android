package com.xelitexirish.scammerbingo.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHandler {

    public static boolean areSoundsEnabled(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("ENABLE_SOUNDS", true);
    }

    public static boolean enableAutoReset(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("ENABLE_AUTO_RESET", false);
    }
}
