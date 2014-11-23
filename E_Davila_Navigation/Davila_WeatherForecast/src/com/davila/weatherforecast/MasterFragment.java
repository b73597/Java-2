package com.davila.weatherforecast;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.yummly.utils.Weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MasterFragment extends Fragment {

	ArrayList<String> receipeList = new ArrayList<>();
	ArrayList<String> ingredientsList = new ArrayList<>();

	ArrayAdapter<String> adapter;
	private TextView textViewLocation, textViewFeels, textViewUpdated,
			textViewWeather, textViewWindDir;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.master_fragment, container, false);

		textViewLocation = (TextView) view.findViewById(R.id.textViewLocation);
		textViewFeels = (TextView) view.findViewById(R.id.textViewFeels);
		textViewUpdated = (TextView) view.findViewById(R.id.textViewUpdated);
		textViewWeather = (TextView) view.findViewById(R.id.textViewWeather);
		textViewWindDir = (TextView) view.findViewById(R.id.textViewWindDir);

		return view;

	}

	public boolean loadData() {

		try {

			// Retrieves 'matches' JSON Array
			JSONObject jsonObj = Weather.getCurrentWeather().getJSONObject(
					"current_observation");

			textViewLocation.setText(jsonObj.getJSONObject("display_location").getString("full"));
			textViewFeels.setText(jsonObj.getString("feelslike_string"));
			textViewUpdated.setText(jsonObj.getString("observation_time").replace("Last Updated on ", ""));
			textViewWeather.setText(jsonObj.getString("weather"));
			textViewWindDir.setText(jsonObj.getString("wind_dir"));

			return true;
		} catch (JSONException e1) {
			return false;
		}
	}
}