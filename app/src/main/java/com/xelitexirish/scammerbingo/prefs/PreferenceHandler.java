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

    public static boolean cacheOnlineList(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("CACHE_ONLINE_LIST", false);
    }

    public static boolean enableEasterEgg(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("ENABLE_EASTER_EGG", true);
    }
}
