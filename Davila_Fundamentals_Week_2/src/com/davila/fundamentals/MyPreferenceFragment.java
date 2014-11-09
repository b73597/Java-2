package com.davila.fundamentals;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;

public class MyPreferenceFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		//Defining PreferenceChangeListener
		OnPreferenceChangeListener onPreferenceChangeListener = new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				OnPreferenceChangeListener listener = (OnPreferenceChangeListener) getActivity();
				listener.onPreferenceChange(preference, newValue);
				((EDavilaSearchRecipes) getActivity()).loadDataUsingPreference(newValue
						.toString());
				return true;
			}
		};

		//Getting the ListPreference from the Preference Resource
		ListPreference listPreference = (ListPreference) getPreferenceManager()
				.findPreference(EDavilaSearchRecipes.SHARED_PREFERENCE_VALUE);

		// Setting Preference change listener for the ListPreference
		listPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);
	}

	public interface PreferenceChanger {
		public void loadDataUsingPreference(String value);
	}

}
