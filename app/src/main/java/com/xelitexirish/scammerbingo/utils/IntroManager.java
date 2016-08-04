package com.xelitexirish.scammerbingo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class IntroManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context _context;

    private static final String KEY_IS_FIRST_LAUNCH = "KEY_IS_FIRST_LAUNCH";

    public IntroManager(Context context){
        this._context = context;
        this.preferences = context.getSharedPreferences(KEY_IS_FIRST_LAUNCH, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        this.editor.putBoolean(KEY_IS_FIRST_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return preferences.getBoolean(KEY_IS_FIRST_LAUNCH, true);
    }
}
