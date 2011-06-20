package de.nofail.dzone;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class ResultListActivity extends ListActivity {

	private int limit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		limit = 25;

		loadItems();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_load_items:
			limit += 5;
			loadItems();
			return true;
		case R.id.menu_item_about:
			Uri uri = Uri.parse("http://nofail.de/dzone");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
			return true;
		case R.id.menu_item_settings:
			startActivity(new Intent(getApplicationContext(), PreferencesActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void loadItems() {
		if (NetHelper.isOnline(getApplicationContext())) {
			loadAsync();
		} else {
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