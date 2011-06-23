package de.nofail.dzone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends Activity {

	MenuHelper menuHelper = new MenuHelper.DetailsMenuHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.details);

		final ItemData item = (ItemData) getIntent().getSerializableExtra(StringHelper.EXTRA_NAME_ITEM);

		// typecasting is great! no need for wildcards...
		TextView title = (TextView) findViewById(R.id.details_text_view_title);
		TextView clicks = (TextView) findViewById(R.id.details_text_view_clicks);
		TextView votes = (TextView) findViewById(R.id.details_text_view_votes);
		TextView comments = (TextView) findViewById(R.id.details_text_view_comments);
		TextView tags = (TextView) findViewById(R.id.details_text_view_tags);
		TextView description = (TextView) findViewById(R.id.details_text_view_description);
		Button browserButton = (Button) findViewById(R.id.details_button_open_in_browser);
		Button voteButton = (Button) findViewById(R.id.details_button_vote_for_item);

		title.setText(item.title);
		clicks.setText(item.clicks + " clicks");
		votes.setText(item.voteUp + " votes");
		comments.setText(item.comments + " comments");
		tags.setText(TextUtils.join(", ", item.categories));
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
					Toast.makeText(getApplicationContext(), "Please enter your Credentials in the Settings Menu!", 5).show();
				} else {
					button.setVisibility(View.INVISIBLE);
					NetHelper.vote(item.id, username, password);
					Toast.makeText(getApplicationContext(), "Vote sent for " + username, 3).show();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return menuHelper.create(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return menuHelper.itemSelected(item);
	}

	private void loadImage(final String url) { // this is no common usecase, so please write more code, yeah!
		new AsyncTask<Void, Void, Drawable>() {
			@Override
			protected Drawable doInBackground(Void... none) {
				return NetHelper.getDrawableFromUrl(url);
			}

			@Override
			protected void onPostExecute(Drawable drawable) {
				ImageView thumbnail = (ImageView) findViewById(R.id.details_image_view_thumbnail);
				thumbnail.setImageDrawable(drawable);
			}
		}.execute();
	}
}
