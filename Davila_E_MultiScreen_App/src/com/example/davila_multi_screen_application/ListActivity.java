package com.example.davila_multi_screen_application;

import com.example.davila_multi_screen_application.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

public class ListActivity extends FragmentActivity implements
		ListPersonFragment.callBack {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		Button button = (Button) findViewById(R.id.buttonAdd);
		// Add button click will direct back to the AddActivity.
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == 300) {
			if (data.hasExtra("IS_DELETED")) {
				String result = data.getExtras().getString("IS_DELETED");
				if ("TRUE".equals(result)) {
					finish();
					startActivity(getIntent());
				}
			}
		}
	}

	/*
	 * This method is being invoked by the List Fragment once a particular list
	 * Item has been selected by the user, So this method passes the clicked
	 * item position to the view Activity in the forms of intents.
	 */
	@Override
	public void caller(int position) {
		Intent i = new Intent(this, ViewActivity.class);
		i.putExtra("INDEX", Integer.toString(position));
		startActivityForResult(i, 300);
	}

}
