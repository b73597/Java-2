package com.davila.caseone;

import java.util.ArrayList;
import java.util.HashMap;

import com.davilla.casetwo.R;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class MainActivity extends FragmentActivity implements
		MembersFragment.caller {

	private Spinner spinner1;
	private HashMap<String, ArrayList<String>> leaguePlayers;
	private MembersFragment membersFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		loadMembersList();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addListenerOnSpinnerItemSelection();
		membersFragment = (MembersFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment1);

	}

	public void addListenerOnSpinnerItemSelection() {

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				membersFragment.populateList(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	/**
	 * This just loads team player names, by a simple algorithm to avoid
	 * repeating. This is done by changing their initials.
	 */
	private void loadMembersList() {
		leaguePlayers = new HashMap<>();

		Resources res = getResources();
		String[] teamNames = res.getStringArray(R.array.league_teams);

		for (int i = 0, j = 65; i < teamNames.length; i++) {
			ArrayList<String> players = new ArrayList<>();

			// Once in every 2 loops, resets the first name of the players.
			if (i % 2 == 0) {
				j = 65;
			}

			players.add((char) j++ + ". " + (char) (i + 65) + ". Altuve");
			players.add((char) j++ + ". " + (char) (i + 65) + ". Carter");
			players.add((char) j++ + ". " + (char) (i + 65) + ". Keuchel");
			players.add((char) j++ + ". " + (char) (i + 65) + ". McHugh");
			players.add((char) j++ + ". " + (char) (i + 65) + ". Qualls");
			players.add((char) j++ + ". " + (char) (i + 65) + ". Keuchel");
			players.add((char) j++ + ". " + (char) (i + 65) + ". Silva");
			players.add((char) j++ + ". " + (char) (i + 65) + ". Rodrigo");
			players.add((char) j++ + ". " + (char) (i + 65) + ". sun");

			leaguePlayers.put(Integer.toString(i), players);
		}

	}

	@Override
	public HashMap<String, ArrayList<String>> getTeamPlayers() {
		return leaguePlayers;
	}
}