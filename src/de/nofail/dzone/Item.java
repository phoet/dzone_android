package de.nofail.dzone;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Item createFromJson(JSONObject json) {
		try {
			Item item = new Item();
			item.id = json.getString("id");
			item.title = json.getString("title");
			item.description = json.getString("description");
			item.thumbnail = json.getString("thumbnail");
			item.deepLink = json.getString("deep_link");
			return item;
		} catch (JSONException e) {
			throw Logger.toE(Item.class, e);
		}
	}

	String id, title, description, thumbnail, deepLink;
}
