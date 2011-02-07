package de.nofail.dzone;

import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.json.JSONObject;

public class ItemTest extends TestCase {

	private static final String TEST_JSON = "{\"created_at\":\"2011-02-07T19:23:03Z\",\"title\":\"How to build a Server-Side Equinox/RAP Application\",\"deep_link\":\"http://eclipsesource.com/blogs/2011/02/07/how-to-build-a-server-side-equinoxrap-application/\",\"comments\":0,\"vote_down\":0,\"updated_at\":\"2011-02-07T19:23:03Z\",\"thumbnail\":\"http://www.dzone.com/links/images/thumbs/120x90/555545.jpg\",\"submitter_name\":\"hstaudacher\",\"id\":555545,\"publishing_date\":\"2011-02-07T19:07:19Z\",\"clicks\":6,\"vote_up\":5,\"submitter_image\":\"http://www.dzone.com/links/images/avatars/744713.gif\",\"description\":\"When you face the task of building a Server-Side Equinox or a RAP application (which is just a Server-Side Equinox application) you need to choose a build system from a fairly diverse palette. This choice is never easy because every build system has its pros and cons. In the end it comes down to which one you and others, love or hate...\",\"categories\":\"eclipse, java, open source, server\"}";

	private JSONObject json;

	@Override
	protected void setUp() throws Exception {
		json = new JSONObject(TEST_JSON);
	}

	public void test_dateParsing_withDate_shouldWork() throws Exception {
		Date date = Item.getDate(json, "publishing_date");
		Assert.assertEquals(new GregorianCalendar(2011, 1, 7, 19, 7, 19).getTime(), date);
	}

	public void test_arrayParsing_withArray_shouldWork() throws Exception {
		String[] array = Item.getArray(json, "categories");
		Assert.assertEquals("eclipse", array[0]);
	}
}
