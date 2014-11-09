package com.yummly.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseGenerator {

	static InputStream inputStream = null;
	static StringBuilder contentBuilder;

	// constructor
	public ResponseGenerator() {

	}

	public JSONObject getJSONFromUrl(String url) {

		try {
			URL yummlyApiUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) yummlyApiUrl
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
}
