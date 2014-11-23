package com.davila.actionbarexample;

import com.davilla.actionbarexample.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class AddActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		setTitle("Add Data");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_data_addactivity, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		// Checks which menu item (back|add) to at accordingly.
		switch (item.getItemId()) {

		case R.id.item_list:
			Intent i = new Intent(this, ListDataActivity.class);
			startActivity(i);
			break;
		case R.id.item_addData:
			AddFragment addFragment = (AddFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragmentAdd);
			addFragment.storeData();
			break;
		case R.id.item_clear:
			addFragment = (AddFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragmentAdd);
			addFragment.clearData();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
