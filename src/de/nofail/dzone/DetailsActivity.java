package de.nofail.dzone;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends Activity {

	private static final Logger log = Logger.create(NetHelper.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.details);

		final ItemData item = (ItemData) getIntent().getSerializableExtra(StringHelper.EXTRA_NAME_ITEM);

		TextView title = (TextView) findViewById(R.id.details_text_view_title);
		TextView description = (TextView) findViewById(R.id.details_text_view_description);
		Button browserButton = (Button) findViewById(R.id.details_button_open_in_browser);
		Button voteButton = (Button) findViewById(R.id.details_button_vote_for_item);

		title.setText(item.title);
		description.setText(item.description);
		loadImage(item.thumbnail);
		browserButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse(item.deepLink);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});

		voteButton.setOnClickListener(new View.OnClickListener() {
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

	private void loadImage(final String url) {
		new AsyncTask<Void, Void, Drawable>() {
			@Override
			protected Drawable doInBackground(Void... none) {
				try {
					InputStream is = (InputStream) new URL(url).getContent();
					return Drawable.createFromStream(is, url);
				} catch (Exception e) {
					throw log.toE(e);
				}
			}

			@Override
			protected void onPostExecute(Drawable drawable) {
				ImageView thumbnail = (ImageView) findViewById(R.id.details_image_view_thumbnail);
				thumbnail.setImageDrawable(drawable);
			}
		}.execute();
	}
}
