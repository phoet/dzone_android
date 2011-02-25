package de.nofail.dzone;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabsActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec("list").setIndicator("Items").setContent(new Intent(this, ResultListActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
		tabHost.addTab(tabHost.newTabSpec("pref").setIndicator("Preferences").setContent(new Intent(this, PreferencesActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
	}

}
