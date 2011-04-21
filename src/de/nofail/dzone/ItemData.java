package de.nofail.dzone;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemData implements Serializable {

	private static final Logger log = Logger.create(NetHelper.class);

	private static final long serialVersionUID = 1L;

	public static ItemData createFromJson(JSONObject json) {
		try {
			ItemData item = new ItemData();
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

	// created_at: "2011-02-07T18:22:41Z"
	// title: "HTML5 Video Facts And Fiction"
	// deep_link: http:// css.dzone.com/news/html5-video-facts-and-fiction
	// comments: 0
	// vote_down: 0
	// updated_at: "2011-02-07T18:22:41Z"
	// thumbnail: http:// www.dzone.com/links/images/thumbs/120x90/555421.jpg
	// submitter_name: "mitchp"
	// id: 555421
	// publishing_date: "2011-02-07T18:01:58Z"
	// clicks: 198
	// vote_up: 4
	// submitter_image: http:// www.dzone.com/links/images/avatars/478055.gif
	// description: "The next generation ..." 
	// categories: "css-html, news, standards, web design"
}
