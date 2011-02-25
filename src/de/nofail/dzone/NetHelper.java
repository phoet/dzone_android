package de.nofail.dzone;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class NetHelper {

	private static final Logger log = Logger.create(NetHelper.class);

	private static final String ITEMS_URL = "http://dzone-api.heroku.com/items.json";

	/** http://dzone-api.heroku.com/items/:item-id/vote/:user/:pass */
	private static final String VOTE_URL = "http://dzone-api.heroku.com/items/%s/vote/%s/%s";

	public static List<ItemData> getItems() {
		try {
			String data = getDataFromUrl(ITEMS_URL);
			JSONArray array = new JSONArray(data);
			List<ItemData> items = new ArrayList<ItemData>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				Log.d("json", object.toString());
				items.add(ItemData.createFromJson(object));
			}
			return items;
		} catch (Exception e) {
			throw log.toE(e);
		}
	}

	public static void vote(Integer id, String user, String pass) {
		String urlString = String.format(VOTE_URL, id, user, pass);
		log.debug("Voting with url " + urlString);
		String data = getDataFromUrl(urlString);
		log.debug(data);
	}

	private static String getDataFromUrl(String urlString) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpUriRequest request = new HttpGet(ITEMS_URL);
			InputStream stream = client.execute(request).getEntity().getContent();

			byte[] b = new byte[1024 * 4];
			StringBuffer sb = new StringBuffer();
			int len = 0;
			while ((len = stream.read(b)) > 0) {
				sb.append(new String(b, 0, len));
			}
			stream.close();
			return sb.toString();
		} catch (Exception e) {
			throw log.toE(e);
		}
	}
}
