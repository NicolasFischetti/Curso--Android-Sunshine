package com.example.android.sunshine;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat{

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.newxml);

    private void setPreferenceSummary(Preference preference,  object value) {

            String value = value.toString();
            String key = preference.getKey();

        }

    }

}
