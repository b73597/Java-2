package com.example.davila_multi_screen_application;

import java.io.File;

import com.example.davila_multi_screen_application.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;


/**
 * AddActivity Class: The activity class for the AddFragment, it has 2 buttons that link to ListActivity and the add
 * button that takes everything from the addFragment and stores them to the CACHE file.
 * 
 */
public class AddActivity extends FragmentActivity {

	// Define the cache file path
	public static final String FILE_STR = Environment
			.getExternalStorageDirectory().getPath() + "/daoListOne.obj";

	public static final File CACHE_FILE = new File(FILE_STR);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		Button button = (Button) findViewById(R.id.addButton);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// stores the data inside the fragmentAdd to cache
				AddFragment addFragment = (AddFragment) getSupportFragmentManager()
						.findFragmentById(R.id.fragmentAdd);
				addFragment.storeData();

			}
		});

		// listButton click will start the list Activity
		Button button2 = (Button) findViewById(R.id.listButton);

		button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Redirects user to List of People screen
				Intent i = new Intent(getApplicationContext(),
						ListActivity.class);
				startActivity(i);
			}
		});

	}

}
