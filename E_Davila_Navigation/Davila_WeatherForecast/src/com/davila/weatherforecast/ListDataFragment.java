package com.davila.weatherforecast;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.yummly.utils.Weather;

public class ListDataFragment extends ListFragment {

	ArrayList<String> receipeList = new ArrayList<>();
	ArrayList<String> ingredientsList = new ArrayList<>();

	ArrayAdapter<String> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_fragment, container, false);

		// Simply loads the available data in receipeList to Master Fragment.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, receipeList);
		setListAdapter(adapter);
		return view;

	}

	public void loadDataFromCache(String value) {

		if ("hourly".equals(value)) {
			JSONObject json1 = Weather.getHourlytWeather();
			loadDataHourly(json1);
		} else {
			JSONObject json1 = Weather.getDailytWeather();
			loadDataWeekly(json1);
		}

	}

	private void loadDataWeekly(JSONObject json) {
		try {

			// Observe a sample response from the SearchReceipe for better
			// understanding, since following is tightly coupled with the
			// response format

			// Retrieves 'forecastday' JSON Array
			JSONArray array = json.getJSONObject("forecast")
					.getJSONObject("txt_forecast").getJSONArray("forecastday");
			receipeList.clear();
			ingredientsList.clear();
			for (int i = 0; i < array.length(); i++) {

				receipeList.add(array.getJSONObject(i).getString("title")
						+ "\n"
						+ array.getJSONObject(i).getString("fcttext_metric"));
			}

			adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, receipeList);
			setListAdapter(adapter);
			((BaseAdapter) adapter).notifyDataSetChanged();

			// Clears the Search text after populating data, so user can
			// type in a new query

		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}

	private void loadDataHourly(JSONObject json) {
		try {

			// Observe a sample response from the SearchReceipe for better
			// understanding, since following is tightly coupled with the
			// response format

			// Retrieves 'hourly_forecast' JSON Array
			JSONArray array = json.getJSONArray("hourly_forecast");
			receipeList.clear();
			ingredientsList.clear();
			for (int i = 0; i < array.length(); i++) {

				receipeList.add(array.getJSONObject(i).getJSONObject("FCTTIME")
						.getString("civil")
						+ " - "
						+ array.getJSONObject(i).getString("condition")
						+ ", humidity : "
						+ array.getJSONObject(i).getString("humidity"));
			}

			adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, receipeList);
			setListAdapter(adapter);
			((BaseAdapter) adapter).notifyDataSetChanged();

			// Clears the Search text after populating data, so user can
			// type in a new query

		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}

}