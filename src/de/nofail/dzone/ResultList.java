package de.nofail.dzone;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class ResultList extends ListActivity {

	private List<Item> items;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// initialize
		items = NetHelper.getItems();

		// prepare view
		String[] titles = new String[items.size()];
		for (int i = 0; i < titles.length; i++) {
			titles[i] = items.get(i).title;
		}
		ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
		setListAdapter(adapter);

		// prepare actions
		getListView().setTextFilterEnabled(true);
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), Details.class);
				intent.putExtra(Details.EXTRA_ITEM, items.get(position));
				startActivity(intent);
			}
		});
	}
}