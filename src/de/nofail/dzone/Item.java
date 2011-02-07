package de.nofail.dzone;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * created_at: "2011-02-07T18:22:41Z"<br>
 * title: "HTML5 Video Facts And Fiction"<br>
 * deep_link: http://css.dzone.com/news/html5-video-facts-and-fiction<br>
 * comments: 0<br>
 * vote_down: 0<br>
 * updated_at: "2011-02-07T18:22:41Z"<br>
 * thumbnail: http://www.dzone.com/links/images/thumbs/120x90/555421.jpg<br>
 * submitter_name: "mitchp"<br>
 * id: 555421<br>
 * publishing_date: "2011-02-07T18:01:58Z"<br>
 * clicks: 198<br>
 * vote_up: 4<br>
 * submitter_image: http://www.dzone.com/links/images/avatars/478055.gif<br>
 * description: "The next generation ..." <br>
 * categories: "css-html, news, standards, web design"<br>
 * 
 * @author phoet
 */
public class Item implements Serializable {

	private static final Logger log = Logger.create(NetHelper.class);

	private static final long serialVersionUID = 1L;

	public static Item createFromJson(JSONObject json) {
		try {
			Item item = new Item();
			item.created = getDate(json, "created_at");
			item.title = json.getString("title");
			item.deepLink = json.getString("deep_link");
			item.comments = json.getInt("comments");
			item.voteDown = json.getInt("vote_down");
			item.updated = getDate(json, "updated_at");
			item.thumbnail = json.getString("thumbnail");
			item.submitterName = json.getString("submitter_name");
			item.id = json.getInt("id");
			item.published = getDate(json, "publishing_date");
			item.clicks = json.getInt("clicks");
			item.voteUp = json.getInt("vote_up");
			item.submitterImage = json.getString("submitter_image");
			item.description = json.getString("description");
			item.categories = getArray(json, "categories");
			return item;
		} catch (JSONException e) {
			throw log.toE(e);
		}
	}

	static String[] getArray(JSONObject json, String string) {
		try {
			return json.getString("categories").split(", ");
		} catch (JSONException e) {
			throw log.toE(e);
		}
	}

	static Date getDate(JSONObject json, String name) {
		try {
			// Parse Dates like 2011-02-07T18:01:58Z
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(json.getString(name));
		} catch (Exception e) {
			throw log.toE(e);
		}
	}

	Integer comments, id, clicks, voteUp, voteDown;

	String title, description, thumbnail, deepLink, submitterName, submitterImage;

	String[] categories;

	Date created, updated, published;
}
