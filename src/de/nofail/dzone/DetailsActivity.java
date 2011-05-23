package de.nofail.dzone;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	private static final Logger log = Logger.create(NetHelper.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.details);

		final ItemData item = (ItemData) getIntent().getSerializableExtra(StringHelper.EXTRA_NAME_ITEM);

		TextView title = (TextView) findViewById(R.id.details_text_view_title);
		TextView description = (TextView) findViewById(R.id.details_text_view_description);
		Button button = (Button) findViewById(R.id.details_button_open_in_browser);

		title.setText(item.title);
		description.setText(item.description);
		loadImage(item.thumbnail);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), WebActivity.class);
				intent.putExtra(StringHelper.EXTRA_NAME_ITEM, item);
				startActivity(intent);
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
