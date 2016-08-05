package com.xelitexirish.scammerbingo.ui;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.xelitexirish.scammerbingo.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.pref_general);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
    }

}