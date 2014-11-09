package com.davila.fundamentals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class RecipeMasterFragment extends ListFragment {

	ArrayList<String> receipeList = new ArrayList<>();
	ArrayList<String> ingredientsList = new ArrayList<>();

	ArrayAdapter<String> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.master_fragment, container, false);

		// Simply loads the available data in receipeList to Master Fragment.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, receipeList);
		setListAdapter(adapter);
		return view;

	}

	/*
	 * Invokes when a Master Item is clicked, displays the detailed items.
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		Fragment fragment = new RecipeDetailFragment();

		// Bundles the corresponding Detailed list (ingredients) of the clicked
		// Master Item, and passed the control to Detailed Fragment
		Bundle bundle = new Bundle();
		bundle.putString("INGREDIENTS", ingredientsList.get(position));
		fragment.setArguments(bundle);
		fragmentTransaction.add(R.id.fragment2, fragment, "Fragment");
		fragmentTransaction.commit();
	}

	public void loadDataFromCache() {

		try {

			// Load in an object
			FileInputStream fIn = new FileInputStream(EDavilaSearchRecipes.CACHE_FILE);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(
					fIn));
			String aDataRow = "";
			String aBuffer = "";
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow;
			}

			JSONObject json1 = new JSONObject(aBuffer.toString());
			myReader.close();

			// Observe a sample response from the SearchReceipe for better
			// understanding, since following is tightly coupled with the
			// response format

			// Retrieves 'matches' JSON Array
			JSONArray array = json1.getJSONArray("matches");
			receipeList.clear();
			ingredientsList.clear();
			for (int i = 0; i < array.length(); i++) {

				JSONObject matchObject = array.getJSONObject(i);
				JSONArray ingrediants = matchObject.getJSONArray("ingredients");
				StringBuilder ingrediantsBuilder = new StringBuilder();

				// Here we receive all the ingredients one by one and adds a
				// carriage return between two of them to list them down one
				// by
				// one, Explore the UI.
				for (int j = 0; j < ingrediants.length(); j++) {
					ingrediantsBuilder.append(ingrediants.getString(j) + "\n");
				}

				receipeList.add(matchObject.getString("id"));
				ingredientsList.add(ingrediantsBuilder.toString());
			}

			adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, receipeList);
			setListAdapter(adapter);
			((BaseAdapter) adapter).notifyDataSetChanged();

			// Clears the Search text after populating data, so user can
			// type in a new query

		} catch (JSONException e1) {
			e1.printStackTrace();
		} catch (StreamCorruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IOException e4) {
			e4.printStackTrace();
		}

	}

}