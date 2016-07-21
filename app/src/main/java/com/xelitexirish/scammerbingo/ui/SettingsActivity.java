package com.xelitexirish.scammerbingo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.xelitexirish.scammerbingo.R;

public class SettingsActivity extends AppCompatPreferenceActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSimplePreferencesScreen();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSimplePreferencesScreen(){
        if(isSimplePreferences(this)){

            addPreferencesFromResource(R.xml.pref_general);

            setupPreferenceClickListeners();
        }
    }

    private void setupPreferenceClickListeners(){

        findPreference("CACHE_ONLINE_LIST").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(SettingsActivity.this, "Coming soon, I promise", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        findPreference("ENABLE_SOUNDS").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if((boolean)newValue){
                    Toast.makeText(SettingsActivity.this, "Sounds: ENABLED", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SettingsActivity.this, "Sounds: DISABLED", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public boolean isMultiPane() {
        return isXLargeTablet(this);
    }

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 4;
    }

    private static boolean isSimplePreferences(Context context) {
        return Build.VERSION.SDK_INT < 11 || !isXLargeTablet(context);
    }
}
