package com.example.davila_multi_screen_application;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;

import com.example.davila_multi_screen_application.R;
import com.example.utils.Person;
import com.example.utils.PersonHandler;

public class ViewFragment extends Fragment {
	TextView firstNameTextView;
	TextView lastNameTextView;
	TextView ageTextView;
	TextView addressTextView;
	TextView companyTextView;
	TextView phoneTextView;
	ViewActivity viewActivityCallback;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			viewActivityCallback = (ViewActivity) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ToolbarListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.view_fragment, container, false);
		firstNameTextView = (TextView) view
				.findViewById(R.id.textViewFirstNameContent);
		lastNameTextView = (TextView) view
				.findViewById(R.id.textViewLastNameContent);
		ageTextView = (TextView) view.findViewById(R.id.textViewAgeContent);
		addressTextView = (TextView) view
				.findViewById(R.id.textViewAddressContent);
		companyTextView = (TextView) view
				.findViewById(R.id.textViewCompanyContent);
		phoneTextView = (TextView) view.findViewById(R.id.textViewPhoneContent);

		return view;
	}


	/**  
	 * Function gets the integer position of the person clicked from the list, it gets the details of the person
	 * from the file and loads them to the TextView in the view_fragment.xml file. 
	 * Makes the phone number clickable, which calls sends the number to the android phone dial with the number filled in
	 * @param String position
	 * @return
	 */
	public void displayContent(String position) {
		int i = Integer.valueOf(position);
		Person person = PersonHandler.getPerson(i); // Get the person from the list based on the index selected

		firstNameTextView.setText(person.getFirstName());
		lastNameTextView.setText(person.getLastName());
		ageTextView.setText(person.getAge());

		String address;
		String company;
		String phone;

		address = person.getAddress();
		company = person.getCompany();
		phone = person.getPhone();

		final String phoneNumber = phone;

		if ("".equals(address)) {
			address = "EMPTY";
		}
		addressTextView.setText(address);
		if ("".equals(company)) {
			company = "EMPTY";
		}

		if ("".equals(phone)) {
			phone = "EMPTY";
		} else {
			// The phone number is clickable, once clicked it calls an implicit intent which instructs android to call the
			// default ACTION_CALL activity to handle
			phoneTextView.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View view) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + phoneNumber));
					startActivity(callIntent);    
				}
			});
		}

		companyTextView.setText(company);
		phoneTextView.setText(phone);

	}
}