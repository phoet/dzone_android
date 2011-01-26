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

public class Details extends Activity {

	public static final String EXTRA_ITEM = "extra_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.details);

		final Item item = (Item) getIntent().getSerializableExtra(EXTRA_ITEM);

		TextView title = (TextView) findViewById(R.id.details_text_view_title);
		TextView description = (TextView) findViewById(R.id.details_text_view_description);
		Button button = (Button) findViewById(R.id.details_button_open_in_browser);

		title.setText(item.title);
		description.setText(item.description);
		loadImage(item.thumbnail);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Web.class);
				intent.putExtra(EXTRA_ITEM, item);
				startActivity(intent);
			}
		});
	}

	private void loadImage(final String url) {
		new AsyncTask<String, Void, Drawable>() {
			@Override
			protected Drawable doInBackground(String... urls) {
				try {
					InputStream is = (InputStream) new URL(urls[0]).getContent();
					return Drawable.createFromStream(is, url);
				} catch (Exception e) {
					throw Logger.toE(getClass(), e);
				}
			}

			@Override
			protected void onPostExecute(Drawable drawable) {
				ImageView thumbnail = (ImageView) findViewById(R.id.details_image_view_thumbnail);
				thumbnail.setImageDrawable(drawable);
			};
		}.execute(url);
	}
}
