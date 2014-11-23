package com.davila.actionbarexample;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.davila.actionbarexample.utils.DataHandler;
import com.davilla.actionbarexample.R;

public class ListDataFragment extends ListFragment {

	public static ListDataActivity callBack;
	private static ArrayList<String> dataList = new ArrayList<String>();
	private static SelectionAdapter selectionAdaptor;

	public interface navigator {
		public void loadActionBar(int menuId, Menu menu);

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			callBack = (ListDataActivity) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ListDataFragment");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflates the list Fragment containing form to fill data.
		View view = inflater.inflate(R.layout.list_fragment, container, false);
		// Retrieves current data list
		dataList = DataHandler.getDataList();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, dataList);
		setListAdapter(adapter);

		selectionAdaptor = new SelectionAdapter(callBack.getBaseContext(),
				R.layout.row_list_item, R.id.textView1, dataList);
		setListAdapter(selectionAdaptor);

		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getActivity().getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.menu.menu_listactivity);

		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

		getListView().setMultiChoiceModeListener(new MultiChoiceModeListener() {

			private int selectionCount = 0;

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				selectionAdaptor.clearSelection();
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				selectionCount = 0;
				callBack.loadActionBar(R.menu.menu_contexual_delete, menu);
				return true;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {

				// Deletes the items once when the delete icon is clicked.
				case R.id.item_delete:
					selectionCount = 0;

					for (Integer element : selectionAdaptor
							.getCurrentCheckedPositionsInReverseOrder()) {
						dataList.remove(element.intValue());
					}
					selectionAdaptor.notifyDataSetChanged();
					mode.finish();
				}
				return false;
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				// Once an item is clicked, changes the select count
				// accordingly.
				// Uses a toggling techniques, since once an already selected
				// item is clicked again it becomes non-selected.
				if (checked) {
					selectionCount++;
					selectionAdaptor.setNewSelection(position, checked);
				} else {
					selectionCount--;
					selectionAdaptor.removeSelection(position);
				}
				// Also updates the action bar title with the selected item
				// count
				mode.setTitle(selectionCount + " selected");

			}
		});

		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				getListView().setItemChecked(position,
						!selectionAdaptor.isPositionChecked(position));
				return false;
			}
		});

	}

	// Refer Array Adapter documentation for more details.
	private class SelectionAdapter extends ArrayAdapter<String> {

		// Note that here we create a TreeMap which is a sorted map and we sort
		// it reversely using Collections reverseOrder.
		// This was done to avoid problems occurring when removing of elements
		// later using Action Bar.
		// When we remove items, we remove the largest index first, then the
		// next largest, ... so we won't delete an unintended value.
		// Note, say if we wan't to delete 0, 2 and 3 indexes of data List. If
		// we first delete the 0th element, next time when we try to delete 2nd
		// element, it will not be the original 2nd element but 3rd 1, since we
		// have deleted 1 element from the original list.
		private TreeMap<Integer, Boolean> mSelection = new TreeMap<Integer, Boolean>(
				java.util.Collections.reverseOrder());

		public SelectionAdapter(Context context, int resource,
				int textViewResourceId, ArrayList<String> objects) {
			super(context, resource, textViewResourceId, objects);
		}

		public void setNewSelection(int position, boolean value) {
			mSelection.put(position, value);
			notifyDataSetChanged();
		}

		public boolean isPositionChecked(int position) {
			Boolean result = mSelection.get(position);
			return result == null ? false : result;
		}

		public Set<Integer> getCurrentCheckedPositionsInReverseOrder() {
			return mSelection.keySet();
		}

		public void removeSelection(int position) {
			mSelection.remove(position);
			notifyDataSetChanged();
		}

		public void clearSelection() {
			mSelection = new TreeMap<Integer, Boolean>(
					java.util.Collections.reverseOrder());
			notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// let the adapter handle setting up the row views

			View v = super.getView(position, convertView, parent);
			// if the item is selected, sets it's color special.
			if (mSelection.get(position) != null) {
				// this is a selected position so make it blue
				v.setBackgroundColor(getResources().getColor(
						android.R.color.holo_blue_light));
				return v;
			}

			// Sets the default background color for each item
			v.setBackgroundColor(getResources().getColor(
					android.R.color.background_light));
			return v;
		}
	}
}
