package de.nofail.dzone;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Details extends Activity {

	protected static final String EXTRA_ITEM = "extra_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.details);
		TextView description = (TextView) findViewById(R.id.description);
		Item item = (Item) getIntent().getSerializableExtra(EXTRA_ITEM);
		description.setText(item.description);
	}

}
