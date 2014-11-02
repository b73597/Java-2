package com.davila.fundamentals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yummly.utils.ResponseGenerator;

public class MainActivity extends FragmentActivity {

	// Define the cache file path - Java-2 Requirement
	public static final String FILE_STR = Environment
			.getExternalStorageDirectory().getPath() + "/cacheFile.json";
	public static final File CACHE_FILE = new File(FILE_STR);

	// Put your Yummly account details here (app ID and app Key)
	public static final String APP_ID = "d4c0ac95";
	public static final String APP_KEY = "421961af8f79b99be0d64fb1b1414229";

	// URL to get JSON Array
	public static final String YUMMLY_API_URL = "http://api.yummly.com/v1/api/recipes?_app_id="
			+ APP_ID + "&_app_key=" + APP_KEY;

	Button searchButton;
	EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		// Java - 2 Assignment
		// Checks if data is in local cache, if so loads data from it,
		// otherwise uses AsyncTask to load them to local cache and then loads
		// data to Master Fragment
		if (!MainActivity.CACHE_FILE.exists()) {

			new JSONParse().execute();
		} else {
			MasterFragment tbFragment = (MasterFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragment);
			tbFragment.loadDataFromCache();
		}

		// Creates the search button and adds a listener to perform it's tasks
		// once clicked.
		searchButton = (Button) findViewById(R.id.searchBtn);
		searchButton.setOnClickListener(new View.OnClickListener() {

			// If local cache is not available. This retrieves data from API
			// Saves locally and populates the Master Fragment
			@Override
			public void onClick(View view) {
				new JSONParse().execute(); // Here it calls the JSONParse, which
											// is an AsyncTask
			}
		});

	}

	/**
	 * Async Taks to retrieve and save data locally.
	 *
	 */
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog progressDialog;

		// This method runs First, it kind of prepares the things. So we use
		// this method to display the progress Bar
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(MainActivity.this);
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
			editText = (EditText) findViewById(R.id.searchTxt);

			String query = editText.getText().toString();

			return pullDataFromYummly(query);

		}

		// This runs last (Third). Calls the Master Fragments data loading method.
		// Also dismisses the progress bar.
		@Override
		protected void onPostExecute(JSONObject json) {
			saveDataInCache(json);
			MasterFragment tbFragment = (MasterFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragment);
			tbFragment.loadDataFromCache();
			progressDialog.dismiss();

		}
	}

	/**
	 * Java -2 Assignment requirement
	 * Saves the data in cache.
	 * @param json
	 */
	
	private void saveDataInCache(JSONObject json) {

		// Save the JSONOvject, using standard file writing techniques.
		try {
			MainActivity.CACHE_FILE.createNewFile();
			FileOutputStream fOut = new FileOutputStream(
					MainActivity.CACHE_FILE);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(json.toString());
			myOutWriter.flush();
			myOutWriter.close();
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			Toast.makeText(MainActivity.this, e.getMessage(),
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

		String completeUrl = MainActivity.YUMMLY_API_URL;
		if (query != null && !("".equals(query))) {
			// Should always Encode the URL , Didn't encode the entire
			// URL since we hard coded it on top
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