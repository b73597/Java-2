package com.davila.weatherforecast;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.yummly.utils.ResponseGenerator;
import com.yummly.utils.Weather;

public class ForecastActivity extends FragmentActivity implements
		OnPreferenceChangeListener, MyPreferenceFragment.PreferenceChanger {
	SharedPreferences sharedPreference;
	String preferenceQuery;

	Button previousButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forecast);

		// Getting an instance of shared preferences, that is being used in this
		// context
		sharedPreference = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		String userChoise = sharedPreference.getString(
				MainActivity.SHARED_PREFERENCE_VALUE, MainActivity.HOURLY);

		// Initially sets the preferences value to hourly
		userChoise = MainActivity.HOURLY;

		loadDataUsingPreference(userChoise);
		
		previousButton = (Button) findViewById(R.id.buttonPrevious);
		previousButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(i);
			}
		});
	}

	/*
	 * This method will be invoked once the preference has been changed. Method
	 * will load data according to the user selection.
	 */
	@Override
	public void loadDataUsingPreference(String value) {
		if(MainActivity.HOURLY.equals(value)){
			preferenceQuery = "hourly";
		}else{
			preferenceQuery = "forecast10day";
		}
		new JSONParser().execute();
	}

	/** This method is invoked by the ListPreference on changing its value */
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		return true;
	}

	// Async Taks to retrieve and save data locally.

	private class JSONParser extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog progressDialog;

		// This method runs First, it kind of prepares the things. So we use
		// this method to display the progress Bar
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(ForecastActivity.this);
			progressDialog
					.setMessage("Retrieving Data from UWeather for location "
							+ Weather.getCurrentWeatherLocation());
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		// This method runs (Secondly) during the execution, at the background,
		// so we use it to fetch the details form yummly API.
		@Override
		protected JSONObject doInBackground(String... args) {
			
			sharedPreference = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());

			String userChoise = sharedPreference.getString(
					MainActivity.SHARED_PREFERENCE_VALUE, MainActivity.HOURLY);
			preferenceQuery = "hourly";
			if(!MainActivity.HOURLY.equals(userChoise)){
				preferenceQuery = "forecast10day";
			}
			
			return ResponseGenerator.pullDataFromUWeather(
					Weather.getCurrentWeatherLocation(), preferenceQuery);

		}

		// This runs last (Third). Calls the Master Fragments data loading
		// method.
		// Also dismisses the progress bar.
		@Override
		protected void onPostExecute(JSONObject json) {
			
			ListDataFragment ldFragment = (ListDataFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragmentList);

			if("hourly".equals(preferenceQuery)){
				Weather.setHourlytWeather(json);
				Weather.setHourlyWeatherLocation(Weather
						.getCurrentWeatherLocation());
			}else{
				Weather.setDailytWeather(json);
				Weather.setDailyWeatherLocation(Weather
						.getCurrentWeatherLocation());
			}

			ldFragment.loadDataFromCache(preferenceQuery);
			progressDialog.dismiss();

		}
	}
}