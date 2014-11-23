package com.davila.weatherforecast;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yummly.utils.ResponseGenerator;
import com.yummly.utils.Weather;

public class MainActivity extends FragmentActivity {

	// Define the cache file path
	public static final String SHARED_PREFERENCE_VALUE = "SHARED_VALUE";
	public static final String HOURLY = "HOURLY";
	public static final String DAILY = "DAILY";

	// Put your Underground Weather account details here (app ID and app Key)
	public static final String APP_KEY = "b24dd9c2f13aea3e";
	/**
	 * Location parameter to track the current location being queried, note that
	 * this defaults to newyork so loads it's data first time.
	 */
	private static String location = "newyork";

	// URL to get JSON Array
	public static final String UWEATHER_API_URL = "http://api.wunderground.com/api/"
			+ APP_KEY + "/%s/q/%s.json";

	Button searchButton, nextButton;
	EditText searchText;
	SharedPreferences sharedPreference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Creates the search button and adds a listener to perform it's tasks
		// once clicked.
		searchText = (EditText) findViewById(R.id.searchTxt);
		searchButton = (Button) findViewById(R.id.searchBtn);
		new JSONParse().execute();
		searchButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				new JSONParse().execute(); // Here it calls the JSONParse, which
											// is an Async Task
			}
		});

		nextButton = (Button) findViewById(R.id.buttonNext);
		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				Intent i = new Intent(getApplicationContext(),
						ForecastActivity.class);
				startActivity(i);
			}
		});
		nextButton.setEnabled(false);

	}

	// Async Task to retrieve and save data locally.

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog progressDialog;

		// This method runs First, it kind of prepares the things. So we use
		// this method to display the progress Bar
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			String query = searchText.getText().toString();
			if (!"".equals(query)) {
				location = query;
			} else {
				location = "newyork";
			}
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("Retrieving Data for location : "
					+ location);
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		// This method runs (Secondly) during the execution, at the background,
		// so we use it to fetch the details form UW API.
		@Override
		protected JSONObject doInBackground(String... args) {

			// return pullDataFromUWeather();

			return ResponseGenerator.pullDataFromUWeather(location,
					"conditions");

		}

		// This runs last (Third). Calls the Master Fragments data loading
		// method.
		// Also dismisses the progress bar.
		@Override
		protected void onPostExecute(JSONObject json) {
			// saveDataInCache(json);
			MasterFragment masterFragment = (MasterFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragment);
			Weather.setCurrentWeatherLocation(location);
			Weather.setCurrentWeather(json);
			boolean result = masterFragment.loadData();
			progressDialog.dismiss();
			super.onPostExecute(json);
			if (!result) {
				Toast.makeText(
						getApplicationContext(),
						"ERROR, failed to retrieve data for "
								+ searchText.getText().toString(),
						Toast.LENGTH_LONG).show();
				searchText.setText("");
			}
			nextButton.setEnabled(true);

		}

	}

}