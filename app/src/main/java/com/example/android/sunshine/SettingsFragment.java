package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by omeraksu on 10.12.2017.
 */

// COMPLETED (4) Create SettingsFragment and extend PreferenceFragmentCompat
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    // COMPLETED (10) Implement OnSharedPreferenceChangeListener from SettingsFragment

    // COMPLETED (8) Create a method called setPreferenceSummary that accepts a Preference and an Object and sets the summary of the preference
    private void setPreferenceSummary(Preference preference,Object value) {
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value.toString());
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(value.toString());
        }
    }


    // COMPLETED (13) Unregister SettingsFragment (this) as a SharedPreferenceChangedListener in onStop
    @Override
    public void onStop() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    // COMPLETED (12) Register SettingsFragment (this) as a SharedPreferenceChangedListener in onStart
    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }


    // COMPLETED (5) Override onCreatePreferences and add the preference xml file using addPreferencesFromResource
    @Override
    public void onCreatePreferences(Bundle savedInstanceState,String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);

        // Do step 9 within onCreatePreference
        // COMPLETED (9) Set the preference summary on each preference that isn't a CheckBoxPreference
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();

        int count = prefScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            if (!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(p.getKey(),"");
                setPreferenceSummary(p,value);
            }
        }
    }

    // COMPLETED (11) Override onSharedPreferenceChanged to update non CheckBoxPreferences when they are changed
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String s) {
        Preference preference = findPreference(s);
        if (null != preference) {
            if (!(preference instanceof CheckBoxPreference)) {
                setPreferenceSummary(preference,sharedPreferences.getString(s,""));
            }
        }
    }

}
