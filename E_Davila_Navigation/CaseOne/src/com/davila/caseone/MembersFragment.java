package com.davila.caseone;

import java.util.ArrayList;
import java.util.HashMap;

import com.davilla.casetwo.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MembersFragment extends ListFragment{

	private HashMap<String, ArrayList<String>> contentList;
	private ArrayList<String> players;
	private MainActivity callBack;
	ArrayAdapter<String> adapter;
	
	public interface caller{
		public HashMap<String, ArrayList<String>> getTeamPlayers();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			callBack = (MainActivity) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement MainActivity");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_players, container, false);
		contentList = callBack.getTeamPlayers();
		// Loads players of the first Team.
		players = contentList.get(Integer.toString(0));
		// Simply loads the available data in receipeList to Master Fragment.
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, players);
		setListAdapter(adapter);
		return view;
	}
	
	public void populateList(int pos){
		players = contentList.get(Integer.toString(pos));
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, players);
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
}
