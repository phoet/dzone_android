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

	private static final String HTTP_DZONE_API_HEROKU_COM_ITEMS_JSON = "http://dzone-api.heroku.com/items.json";

	public static List<Item> getItems() {
		try {
			String data = getDataFromUrl(HTTP_DZONE_API_HEROKU_COM_ITEMS_JSON);
			JSONArray array = new JSONArray(data);
			List<Item> items = new ArrayList<Item>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				Log.d("json", object.toString());
				items.add(Item.createFromJson(object));
			}
			return items;
		} catch (Exception e) {
			throw Logger.toE(NetHelper.class, e);
		}
	}

	private static String getDataFromUrl(String httpDzoneApiHerokuComItemsJson) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpUriRequest request = new HttpGet(HTTP_DZONE_API_HEROKU_COM_ITEMS_JSON);
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
			throw Logger.toE(NetHelper.class, e);
		}
	}

}
