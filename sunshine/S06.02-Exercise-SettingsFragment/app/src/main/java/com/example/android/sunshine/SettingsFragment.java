package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private void setPreferenceSummary (Preference preference, Object value)  {

        String stringValue = value.toString();
        String key = preference.getKey();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            // Set the summary to reflect the new value.
            if (index >= 0) {
                preference.setSummary(listPreference.getEntries()[index]);

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
        }

    }


        @Override
        public void onCreatePreferences (Bundle savedInstanceState, String rootKey){
            addPreferencesFromResource(R.xml.newxml);

            SharedPreferences sharedPreferences =  getPreferenceScreen().getSharedPreferences();
            PreferenceScreen preferenceScreen= getPreferenceScreen();

            int count = preferenceScreen.getPreferenceCount();

            for(int i=0; i < count; i++) {
                Preference p = preferenceScreen.getPreference(i);

                if(!(p instanceof CheckBoxPreference)) {
                    String value = sharedPreferences.getString(p.getKey(), "");
                    setPreferenceSummary(p, value);
                }
            }

        }

    @Override
    public void onResume() {   //toma las preferencias y se fija si cambio algo en las mismas
        super.onResume();

        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPref, String key) {

        Preference preference = findPreference(key);
        if(null != preference) {
            if(!(preference instanceof CheckBoxPreference)) {
                setPreferenceSummary(preference, sharedPref.getString(key, ""));
            }
        }


    }
}


