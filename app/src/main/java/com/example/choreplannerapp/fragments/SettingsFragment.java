package com.example.choreplannerapp.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.choreplannerapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static final String TAG = "ConfigFragment.TAG";

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefs_config, rootKey);
    }
}