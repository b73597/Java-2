package com.yummly.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.davila.weatherforecast.MainActivity;

public class ResponseGenerator {

	static InputStream inputStream = null;
	static StringBuilder contentBuilder;

	// constructor
	public ResponseGenerator() {

	}

	public static JSONObject getJSONFromUrl(String url) {

		try {
			URL uwApiUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) uwApiUrl
					.openConnection();
			// Check the status code of the response to proceed further, Note
			// that the above API call returns 200 OK STATUS with the response,
			// Refer API Document
			if (conn.getResponseCode() != 200) {
				throw new IOException(conn.getResponseMessage());
			}

			// Buffer the result into a string
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			contentBuilder = new StringBuilder();
			String line;
			// Reads the Response line by line
			while ((line = rd.readLine()) != null) {
				contentBuilder.append(line);
			}
			// It's important to close the streams in java to avoid unwanted
			// resource usages
			rd.close();
			conn.disconnect();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JSONObject contentJSONObject = null;
		try {
			// Since the YAMMLY API response is JSON, we use JSONObject to parse
			// and create JSONObject to be used later
			contentJSONObject = new JSONObject(contentBuilder.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// returns the JSON JSONObject
		return contentJSONObject;
	}

	public static JSONObject pullDataFromUWeather(String location,
			String condition) {

		
		// Checks weather user has included a search String, if not
		// parameter 'q' will not be incorporated for the API Call

		String completeUrl = "";
		try {
			completeUrl = String.format(MainActivity.UWEATHER_API_URL,
					condition,
					URLEncoder.encode(location, "UTF-8").replace("+", "%20"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// Getting JSON from URL
		JSONObject json = ResponseGenerator.getJSONFromUrl(completeUrl);
		System.out.println("111111111111111111111111" + completeUrl);

		// progressDialog.dismiss();
		return json;
	}
}
