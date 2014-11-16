package com.example.davila_multi_screen_application;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.davila_multi_screen_application.R;
import com.example.utils.Person;
import com.example.utils.PersonHandler;

public class ListPersonFragment extends ListFragment {

	ArrayList<Person> receipeList = new ArrayList<Person>();
	ArrayList<String> personData = new ArrayList<String>();

	ArrayAdapter<String> adapter;
	ListActivity listActivityCallback;

	public interface callBack {
		public void caller(int position);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			listActivityCallback = (ListActivity) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ToolbarListener");
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_fragment, container, false);
		loadDataToList();
		// Simply loads the available data in receipeList to Master Fragment.
		// Note that this approach populates data freshly every time the
		// Activity
		// being created newly. But sine we treat the list in a Singleton
		// manner, won't effect too badly on performances.

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, personData);
		setListAdapter(adapter);
		return view;

	}

	/*
	 * Invokes when a List Item is clicked. To pass the position data to View
	 * Activity. Note that ArrayList positions and ListItem positions are both 0
	 * indexed. Therefore we can refer the mapping ArrayList item by simply
	 * using the List Item index itself.
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		listActivityCallback.caller(position);
	}

	/**
	 * This method loads the FrstName, LastName and Age data to the List to be
	 * displayed.
	 */
	private void loadDataToList() {

		ArrayList<Person> personList = PersonHandler.getList();
		if (personList != null) {
			if (personData.size() > 0) {
				personData.clear();
			}
			String personalData = "";
			for (Person person : personList) {
				personalData = "NAME: " + person.getFirstName() + " "
						+ person.getLastName() + "\n" + "AGE: "
						+ person.getAge();

				personData.add(personalData);
			}
		}

	}
}