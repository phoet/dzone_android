package de.nofail.dzone;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class MenuHelper {

	private static final Logger log = Logger.create(MenuHelper.class);

	private static final int ALL_PACKAGE_INFOS = 0; // there seems to be no constant for this...

	Activity activity;

	public static class ResultListMenuHelper extends MenuHelper {

		public ResultListMenuHelper(Activity activity) { // bah, constructors...
			super(activity);
		}

		@Override
		void handleMenu(Menu menu) {
			// empty
		}

		@Override
		boolean handleSelected(MenuItem item) {
			if (R.id.menu_item_load_items == item.getItemId()) {
				ResultListActivity a = (ResultListActivity) activity;
				a.limit += 5;
				a.loadItems();
				return true;
			}
			return false;
		}
	}

	public static class DetailsMenuHelper extends MenuHelper {

		public DetailsMenuHelper(Activity activity) {
			super(activity);
		}

		@Override
		void handleMenu(Menu menu) {
			menu.findItem(R.id.menu_item_load_items).setVisible(false);
		}

		@Override
		boolean handleSelected(MenuItem item) {
			return false;
		}

	}

	public MenuHelper(Activity activity) {
		this.activity = activity;
	}

	public boolean create(Menu menu) {
		MenuInflater inflater = activity.getMenuInflater();
		inflater.inflate(R.menu.options, menu);
		handleMenu(menu);
		return true;
	}

	abstract void handleMenu(Menu menu);

	public boolean itemSelected(MenuItem item) {
		if (handleSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.menu_item_version:
			String version = getVersionName();
			Toast.makeText(activity.getApplicationContext(), "You are using Version " + version, 5).show();
			return true;
		case R.id.menu_item_website:
			Uri uri = Uri.parse("http://nofail.de/dzone");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			activity.startActivity(intent);
			return true;
		case R.id.menu_item_settings:
			activity.startActivity(new Intent(activity.getApplicationContext(), PreferencesActivity.class));
			return true;
		default:
			throw new IllegalStateException("menu item not known!");
		}
	}

	abstract boolean handleSelected(MenuItem item);

	private String getVersionName() {
		try {
			return activity.getPackageManager().getPackageInfo(activity.getApplication().getPackageName(), ALL_PACKAGE_INFOS).versionName;
		} catch (NameNotFoundException e) { // hide fucking checked exception!
			throw log.toE(e);
		}
	}
}
