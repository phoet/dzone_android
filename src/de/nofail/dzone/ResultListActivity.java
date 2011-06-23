package de.nofail.dzone;

import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class ResultListActivity extends ListActivity {

	int limit = 25;

	MenuHelper menuHelper = new MenuHelper.ResultListMenuHelper(this);

	private final List<ItemData> storedItems = Collections.emptyList();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (storedItems.isEmpty()) { // don't do it on rotation!
			loadItems();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return menuHelper.create(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return menuHelper.itemSelected(item);
	}

	void loadItems() {
		if (NetHelper.isOnline(getApplicationContext())) {
			loadAsync();
		} else { // otherwise an host-not-found exception is thrown, which is stupid!
			showError();
		}
	}

	private void loadAsync() {
		final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
		new AsyncTask<Void, Void, List<ItemData>>() {
			@Override
			protected List<ItemData> doInBackground(Void... params) {
				return NetHelper.getItems(limit);
			}

			@Override
			protected void onPostExecute(final List<ItemData> items) {
				String[] titles = new String[items.size()];
				for (int i = 0; i < titles.length; i++) {
					titles[i] = items.get(i).title;
				}
				ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, titles);
				setListAdapter(adapter);

				getListView().setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
						intent.putExtra(StringHelper.EXTRA_NAME_ITEM, items.get(position));
						startActivity(intent);
					}
				});
				dialog.dismiss();
			}
		}.execute();
	}

	private void showError() {
		new AlertDialog.Builder(this) //
				.setMessage("Please activate Internet access!") //
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).create().show();
	}
}