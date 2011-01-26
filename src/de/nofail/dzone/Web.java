package de.nofail.dzone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class Web extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.web);

		final Item item = (Item) getIntent().getSerializableExtra(Details.EXTRA_ITEM);

		WebView web = (WebView) findViewById(R.id.web_web_view);
		Button button = (Button) findViewById(R.id.web_button);

		web.loadUrl(item.deepLink);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO read preferences
				String user = "";
				String pass = "";
				String url = NetHelper.createVoteUrl(item.id, user, pass);
				Toast.makeText(getApplicationContext(), url, 3).show();
			}
		});
	}
}
