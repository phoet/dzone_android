package de.nofail.dzone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class WebActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.web);

		final ItemData item = (ItemData) getIntent().getSerializableExtra(ExtraDataEnum.ITEM.name());

		WebView web = (WebView) findViewById(R.id.web_web_view);
		Button button = (Button) findViewById(R.id.web_button);

		web.loadUrl(item.deepLink);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View button) {
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				String username = preferences.getString("username", "");
				String password = preferences.getString("password", "");

				if (StringHelper.isOneBlank(username, password)) {
					Toast.makeText(getApplicationContext(), "Please enter your Credentials in the Preferences Tab!", 5).show();
				} else {
					button.setVisibility(View.INVISIBLE);
					NetHelper.vote(item.id, username, password);
					Toast.makeText(getApplicationContext(), "Vote sent for " + username, 3).show();
				}
			}
		});
	}
}
