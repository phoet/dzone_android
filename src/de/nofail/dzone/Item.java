package de.nofail.dzone;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {

	public static Item createFromJson(JSONObject json) {
		try {
			Item item = new Item();
			item.title = json.getString("title");
			item.description = json.getString("description");
			return item;
		} catch (JSONException e) {
			throw Logger.toE(Item.class, e);
		}
	}

	String title;
	String description;

}
