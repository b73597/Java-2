package com.example.davila_multi_screen_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.davila_multi_screen_application.R;
import com.example.utils.PersonHandler;

/**
 * ViewActivity: Gets the person selected and loads their details to the view_fragment.xml
 * It has 2 buttons for showing the ListFragment and the other for deleting the person in the list
 *
 */
public class ViewActivity extends FragmentActivity {

	private String position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);

		// Retrieves the passed item index (using intents) by ListActivity and
		// displays all the information in View Fragment.
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			position = extras.getString("INDEX");
			if (position != null) {
				ViewFragment viewFragment = (ViewFragment) getSupportFragmentManager()
						.findFragmentById(R.id.fragmentView);
				viewFragment.displayContent(position);
			}
		}

		Button listButton = (Button) findViewById(R.id.listButton);

		// Once the listButton is clicked directs back to the ListView.
		listButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.putExtra("IS_DELETED", "FALSE");
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		Button deleteButton = (Button) findViewById(R.id.deleteButton);

		deleteButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.putExtra("IS_DELETED", "TRUE");
				PersonHandler.deletePerson(Integer.valueOf(position));
				setResult(RESULT_OK, intent);
				finish();
			}

		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == 200) {
			if (data.hasExtra("returnKey1")) {
				Toast.makeText(this, data.getExtras().getString("returnKey1"),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
