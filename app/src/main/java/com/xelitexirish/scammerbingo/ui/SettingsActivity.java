package com.xelitexirish.scammerbingo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.afollestad.appthemeengine.ATE;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.utils.BaseThemedActivity;

@SuppressLint("NewApi")
public class SettingsActivity extends BaseThemedActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (!ATE.config(this, "light_theme").isConfigured(4)) {
            ATE.config(this, "light_theme")
                    .activityTheme(R.style.AppTheme)
                    .primaryColorRes(R.color.colorPrimaryLightDefault)
                    .accentColorRes(R.color.colorAccentLightDefault)
                    .coloredNavigationBar(false)
                    .navigationViewSelectedIconRes(R.color.colorAccentLightDefault)
                    .navigationViewSelectedTextRes(R.color.colorAccentLightDefault)
                    .commit();
        }
        if (!ATE.config(this, "dark_theme").isConfigured(4)) {
            ATE.config(this, "dark_theme")
                    .activityTheme(R.style.AppThemeDark)
                    .primaryColorRes(R.color.colorPrimaryDarkDefault)
                    .accentColorRes(R.color.colorAccentDarkDefault)
                    .coloredNavigationBar(true)
                    .navigationViewSelectedIconRes(R.color.colorAccentDarkDefault)
                    .navigationViewSelectedTextRes(R.color.colorAccentDarkDefault)
                    .commit();

            mToolbar = (Toolbar) findViewById(R.id.toolbar_prefs);
            setSupportActionBar(mToolbar);

            if (savedInstanceState == null) {
                Fragment preferenceFragment = new SettingsFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.preference_container, preferenceFragment);
                ft.commit();
            }
        }
    }
}
