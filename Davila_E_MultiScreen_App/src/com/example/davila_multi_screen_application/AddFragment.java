package com.example.davila_multi_screen_application;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.davila_multi_screen_application.R;
import com.example.utils.Person;
import com.example.utils.PersonHandler;

/**
 * AddFragement class loads the add_fragment.xml fields which are used to add a new Person. It uses the default onCreateView
 *  function to inflate the xml into the view container.
 * - the storeData function creates a new Person object to load the text returned from the EditText fields in the add_fragment.xml
 *  file and stores them in a serial object file.
 */
public class AddFragment extends Fragment {

	private static EditText editTextFirstName;
	private static EditText editTextLastName;
	private static EditText editTextAge;
	private static EditText editTextAddress;
	private static EditText editTextCompany;
	private static EditText editTextPhone;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.add_fragment, container, false);

		editTextFirstName = (EditText) view
				.findViewById(R.id.editTextFirstName);
		editTextLastName = (EditText) view.findViewById(R.id.editTextLastName);
		editTextAge = (EditText) view.findViewById(R.id.editTextAge);
		editTextAddress = (EditText) view.findViewById(R.id.editTextAddress);
		editTextCompany = (EditText) view.findViewById(R.id.editTextCompany);
		editTextPhone = (EditText) view.findViewById(R.id.editTextPhone);

		return view;
	}

	/**
	 * This method checks minimum requirements and stores the data on cache in
	 * the form of an serialized list Object.
	 */
	public void storeData() {
		Person dao = new Person();
		String firstName = editTextFirstName.getText().toString();
		String lastName = editTextLastName.getText().toString();
		String age = editTextAge.getText().toString();

		if ("".equals(firstName) || "".equals(lastName) || "".equals(age)) {
			Toast.makeText(getActivity(),
					"FirstName, LastName and Age are mandatory",
					Toast.LENGTH_SHORT).show();
			return;
		}

		// Set the person object attributes
		dao.setFirstName(firstName);
		dao.setLastName(lastName);
		dao.setAge(age);
		dao.setAddress(editTextAddress.getText().toString());
		dao.setCompany(editTextCompany.getText().toString());
		dao.setPhone(editTextPhone.getText().toString());

		boolean status = PersonHandler.savePerson(dao);
		if (status) {
			Toast.makeText(getActivity(), "Successfully Saved Data",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getActivity(), "Something went wrong !!!",
					Toast.LENGTH_SHORT).show();
		}
	}
}