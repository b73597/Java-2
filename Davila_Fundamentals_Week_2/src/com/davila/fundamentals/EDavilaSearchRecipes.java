package com.davila.fundamentals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yummly.utils.ResponseGenerator;

public class EDavilaSearchRecipes extends FragmentActivity implements
		OnPreferenceChangeListener, MyPreferenceFragment.PreferenceChanger {

	// Define the cache file path
	public static final String FILE_STR = Environment
			.getExternalStorageDirectory().getPath() + "/cacheFile.json";
	public static final File CACHE_FILE = new File(FILE_STR);
	public static final String SHARED_PREFERENCE_VALUE = "SHARED_VALUE";
	public static final String API = "API";
	public static final String CACHE = "CACHE";

	// Put your Yummly account details here (app ID and app Key)
	public static final String APP_ID = "d4c0ac95";
	public static final String APP_KEY = "421961af8f79b99be0d64fb1b1414229";

	// URL to get JSON Array
	public static final String YUMMLY_API_URL = "http://api.yummly.com/v1/api/recipes?_app_id="
			+ APP_ID + "&_app_key=" + APP_KEY;

	Button searchButton;
	EditText searchText;
	SharedPreferences sharedPreference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Getting an instance of shared preferences, that is being used in this
		// context
		sharedPreference = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		String userChoise = sharedPreference.getString(
				EDavilaSearchRecipes.SHARED_PREFERENCE_VALUE, EDavilaSearchRecipes.CACHE);

		// Here we initially check weather cache exists and provides user choice
		// based on that.
		// Note that here we assume that is cache exists it's having on empty
		// data.
		// This assumption is valid since we write non empty valid JSON data to
		// the cache
		if (EDavilaSearchRecipes.CACHE_FILE.exists()) {
			userChoise = EDavilaSearchRecipes.CACHE;
		}

		loadDataUsingPreference(userChoise);

		// Creates the search button and adds a listener to perform it's tasks
		// once clicked.
		searchButton = (Button) findViewById(R.id.searchBtn);
		searchButton.setOnClickListener(new View.OnClickListener() {

			// This does the same thing as if local cache is not available. This
			// retrieves data from API, Saves locally and populates the Mster
			// Fragment
			@Override
			public void onClick(View view) {
				new JSONParse().execute(); // Here it calls the JSONParse, which
											// is an Async Task
			}
		});

	}

	
	 // This method will be invoked once the preference has been changed. Method
	 // will load data according to the user selection.
	 
	@Override
	public void loadDataUsingPreference(String value) {
		
		searchButton = (Button) findViewById(R.id.searchBtn);
		searchText = (EditText) findViewById(R.id.searchTxt);
		if (value.equals(EDavilaSearchRecipes.API)) {
			// If user choice is api, make text and button wiil be visible

			if (!searchButton.isShown()) {
				searchButton.setVisibility(Button.VISIBLE);
			}

			if (!searchText.isShown()) {
				searchText.setVisibility(EditText.VISIBLE);
			}

			new JSONParse().execute();
		} else {
			Toast.makeText(getApplicationContext(), "Reading from cache",
					Toast.LENGTH_SHORT).show();
			if (searchButton.isShown()) {
				searchButton.setVisibility(Button.INVISIBLE);
			}

			if (searchText.isShown()) {
				searchText.setText("");
				searchText.setVisibility(EditText.INVISIBLE);
			}

			RecipeMasterFragment tbFragment = (RecipeMasterFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragment);
			tbFragment.loadDataFromCache();
		}

	}

	// This method is invoked by the ListPreference on changing its value 
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		return true;
	}

	//Async Taks to retrieve and save data locally.
	
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog progressDialog;

		// This method runs First, it kind of prepares the things. So we use
		// this method to display the progress Bar
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(EDavilaSearchRecipes.this);
			progressDialog.setMessage("Retrieving Data from Yummly ...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		// This method runs (Secondly) during the execution, at the background,
		// so we use it to fetch the details form yummly API.
		@Override
		protected JSONObject doInBackground(String... args) {

			// Retrieves the Search Text
			// searchText = (EditText) findViewById(R.id.searchText);
			// String query = searchText.getText().toString();
			searchText = (EditText) findViewById(R.id.searchTxt);

			String query = searchText.getText().toString();

			return pullDataFromYummly(query);

		}

		// This runs last (Third). Calls the Master Fragments data loading
		// method.
		// Also dismisses the progress bar.
		@Override
		protected void onPostExecute(JSONObject json) {
			saveDataInCache(json);
			RecipeMasterFragment tbFragment = (RecipeMasterFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragment);
			tbFragment.loadDataFromCache();
			progressDialog.dismiss();

		}
	}

	/**
	 * Saves the data in cache.
	 * 
	 * @param json
	 */
	private void saveDataInCache(JSONObject json) {

		// Save the JSONOvject, using standard file writing techniques.
		try {
			EDavilaSearchRecipes.CACHE_FILE.createNewFile();
			FileOutputStream fOut = new FileOutputStream(
					EDavilaSearchRecipes.CACHE_FILE);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(json.toString());
			myOutWriter.flush();
			myOutWriter.close();
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			Toast.makeText(EDavilaSearchRecipes.this, e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * Retrives data from Yummly API and returns a JSON Object data.
	 * 
	 * @param query
	 * @return
	 */
	private JSONObject pullDataFromYummly(String query) {
		ResponseGenerator jParser = new ResponseGenerator();

		// Checks weather user has included a search String, if not
		// parameter 'q' will not be incorporated for the API Call

		String completeUrl = EDavilaSearchRecipes.YUMMLY_API_URL;
		if (query != null && !("".equals(query))) {
			// You should always Encode the URL , Didn't encode the entire
			// URL since we hard coded it on top and there's no mysteries
			// in that :)
			try {
				completeUrl += "&q=" + URLEncoder.encode(query, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}

		// Getting JSON from URL
		JSONObject json = jParser.getJSONFromUrl(completeUrl);

		// progressDialog.dismiss();
		return json;
	}

}