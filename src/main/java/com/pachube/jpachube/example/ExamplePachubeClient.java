package com.pachube.jpachube.example;
import com.pachube.jpachube.Data;
import com.pachube.jpachube.Feed;
import com.pachube.jpachube.Pachube;
import com.pachube.jpachube.PachubeException;
import com.pachube.jpachube.httpClient.HttpResponse;

public class ExamplePachubeClient {

	private static final String API_KEY = "XXX";

	public static void main(String[] args) {

		System.out.println("Running Pachube client");

		try {
			Feed feed = createFeed();

			printFeedForId(feed.getId());
		} catch (PachubeException e) {
			System.err.println(e.getMessage());
		}
	}

	static Feed createFeed() throws PachubeException {
		//Creates a Pachube object authenicated using the provided API KEY

		Pachube p = new Pachube(API_KEY);
		Feed f = new Feed();
		f.setTitle("Test Feed from JPachube");
		Data a = new Data();
		a.setId("0");
		a.setMaxValue(100d);
		a.setMinValue(0d);
		a.setTag("Test feed");
		a.setValue("30");
		f.addData(a);
		Feed g = p.createFeed(f);

		// The Feed 'f' is does not represent the feed on pachube any
		// Changes made to this object will not alter the online feed.
		System.out.println("The id of the new feed is:");
		System.out.println(g.getId());

		return g;
	}

	static void printFeedForId(int feedId) {
		Pachube pachubeClient = new Pachube(API_KEY);

		try {
			Feed feed = pachubeClient.getFeed(feedId);

			HttpResponse data = pachubeClient.getDatastream(feed.getId(), "1");

			System.out.println(data.getBody());
		} catch (PachubeException e) {
			System.err.println(e.getMessage());
		}
	}
}
