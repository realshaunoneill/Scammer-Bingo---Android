package com.xelitexirish.scammerbingo.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

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

    public static void saveButtonTextsSet(Context context, ArrayList<String> buttonTexts){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        StringBuilder stringBuilder = new StringBuilder();
        for (int x = 0; x < buttonTexts.size(); x++){
          stringBuilder.append(buttonTexts.get(x)).append(",");
        }

        editor.putString("BUTTON_TEXTS", stringBuilder.toString());
        editor.apply();
    }

    public static String[] getButtonTexts(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String textsString = preferences.getString("BUTTON_TEXTS", "null");
        if(!textsString.equals("null")){
            return textsString.split(",");
        }else return null;
    }

    public static void resetButtonTexts(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("BUTTON_TEXTS");
        editor.apply();
    }

    /**
     * Ads
     */
    public static void enableAds(Context context, boolean enable){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("ENABLE_APP_ADS", enable);
        editor.apply();
    }

    public static boolean areAdsEnabled(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("ENABLE_APP_ADS", true);
    }

}
