package de.nofail.dzone;

import java.io.InputStream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

public class ResultList extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String[] array = { "uschi", "muschi" };
		ListAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, array);
		setListAdapter(adapter);

		getListView().setTextFilterEnabled(true);
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					HttpClient client = new DefaultHttpClient();
					HttpUriRequest request = new HttpGet(
							"http://dzone-api.heroku.com/items.json");
					InputStream stream = client.execute(request).getEntity()
							.getContent();

					byte[] b = new byte[1024 * 4];
					StringBuffer sb = new StringBuffer();
					int len = 0;
					while ((len = stream.read(b)) > 0) {
						sb.append(new String(b, 0, len));
					}
					String data = sb.toString();
					JSONArray json = new JSONArray(data);
					sb = new StringBuffer().append("Data:\n");
					for (int i = 0; i < json.length(); i++) {
						JSONObject item = json.getJSONObject(i);
						Log.d("json", "" + i + item.toString());
						sb.append(item.get("title") + "\n");
					}
					Toast.makeText(getApplicationContext(), sb.toString(),
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}