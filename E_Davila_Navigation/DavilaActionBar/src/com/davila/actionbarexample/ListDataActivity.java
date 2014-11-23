package com.davila.actionbarexample;

import com.davilla.actionbarexample.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ListDataActivity extends ListActivity implements
		ListDataFragment.navigator {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_listactivity, menu);
		setTitle("List Data");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {

		case R.id.item_add:
			Intent i = new Intent(this, AddActivity.class);
			// Calls startActicity instead of startActivityForresult since we
			// don't expect details to be returned from the AddActivity.
			// This is possible since Data Handling is being done using a
			// separate class (DataHandler)
			startActivity(i);
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void loadActionBar(int menuId, Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_contexual_delete, menu);

	}

}