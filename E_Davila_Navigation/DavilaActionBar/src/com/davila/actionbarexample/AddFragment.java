package com.davila.actionbarexample;

import com.davila.actionbarexample.utils.DataHandler;
import com.davilla.actionbarexample.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class AddFragment extends Fragment {

	private static EditText editTextAddData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.add_fragment, container, false);

		editTextAddData = (EditText) view.findViewById(R.id.editTextAddData);
		return view;
	}

	public void clearData() {
		// Clears the already included data
		editTextAddData.setText("");
	}

	/**
	 * This method checks minimum requirements and stores the data on cache in
	 * the form of an serialized list Object.
	 */
	public void storeData() {

		String data = editTextAddData.getText().toString();
		if ("".equals(data)) {
			Toast.makeText(getActivity(), "Data field is mandatory",
					Toast.LENGTH_SHORT).show();
			return;
		}

		DataHandler.addDataToList(data);
		Toast.makeText(getActivity(), "Successfully Saved Data",
				Toast.LENGTH_SHORT).show();

	}
}