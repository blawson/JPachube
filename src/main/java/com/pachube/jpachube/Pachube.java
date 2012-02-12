package com.pachube.jpachube;

import java.awt.Color;
import java.net.Socket;

import com.pachube.jpachube.httpClient.HttpClient;
import com.pachube.jpachube.httpClient.HttpMethod;
import com.pachube.jpachube.httpClient.HttpRequest;
import com.pachube.jpachube.httpClient.HttpResponse;
import com.pachube.jpachube.httpClient.SocketClient;

/**
 *
 * @author Sam Wilson
 */
public class Pachube {

	private HttpClient client;

	/**
	 * API key for your user account on Pachube
	 */
	private String apiKey;

	public Pachube(String apiKey) {
		super();
		this.apiKey = apiKey;
		this.client = new SocketClient("pachube.com");
	}

	/**
	 * Gets a Feed by Feed ID
	 *
	 * @param feed
	 *            Id of the Pachube feed to retrieve
	 * @return Feed which corresponds to the id provided as the parameter
	 * @throws PachubeException
	 *             If something goes wrong.
	 */
	public Feed getFeed(int feed) throws PachubeException {
		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/feeds/"
				+ feed + ".xml");
		hr.setMethod(HttpMethod.GET);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		HttpResponse g = client.send(hr);

		if (g.getHeaderItem("Status").equals("HTTP/1.1 200 OK")) {
			return PachubeFactory.toFeed(this, g.getBody());
		} else {
			throw new PachubeException(g.getHeaderItem("Status"));
		}
	}

	/**
	 * Creates a new feed from the feed provide. The feed provide should have no
	 * ID, and after this method is called is usless, to make chanegs to the new
	 * feed methods should be invoked on the return object.
	 *
	 * @param f
	 *            Feed to create, This Feed Should have no ID field and atleast
	 *            should have its title field filled in. This feed is not 'live'
	 *            any attempt to change this object will be ignored.
	 * @return Representation of the feed from pachube, this is a 'live' Feed
	 *         and method can invoked which will change the state of the online
	 *         feed.
	 * @throws PachubeException
	 *             If something goes wrong.
	 */

	public Feed createFeed(Feed f) throws PachubeException {

		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/feeds");
		hr.setMethod(HttpMethod.POST);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		hr.setBody(f.toXML());
		HttpResponse g = client.send(hr);

		if (g.getHeaderItem("Status").equals("HTTP/1.1 201 Created")) {

			String[] a = g.getHeaderItem("Location").split("/");
			Feed n = this.getFeed(Integer.parseInt(a[a.length - 1]));
			f = n;
			return n;
		} else {
			throw new PachubeException(g.getHeaderItem("Status"));
		}
	}

	/**
	 * This Method is not intended to be used by Users, instead get the Feed
	 * object using getFeed() and update the Feed from there, All changes will
	 * be made to the online Feed.
	 *
	 * @param feed
	 * @param s
	 * @return
	 * @throws PachubeException
	 */
	public boolean updateFeed(int feed, String s) throws PachubeException {
		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/feeds/"
				+ feed + ".xml");
		hr.setMethod(HttpMethod.PUT);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		hr.setBody(s);
		HttpResponse g = client.send(hr);

		if (g.getHeaderItem("Status").equals("HTTP/1.1 200 OK")) {
			return true;
		} else {
			throw new PachubeException(g.getHeaderItem("Status"));
		}
	}

	/**
	 * Delete a Feed specified by the feed id. If any Feed object exists that is
	 * a representation of the item to be deleted, they will no longer work and
	 * will throw errors if method are invoked on them.
	 *
	 * @param feed
	 *            If of the feed to delete
	 * @return HttpResponse
	 */
	public HttpResponse deleteFeed(int feed) {
		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/feeds/"
				+ feed);
		hr.setMethod(HttpMethod.DELETE);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		return client.send(hr);
	}

	/**
	 * This Method is not intended to be used by Users, instead get the Feed
	 * object using getFeed() and create Datastreams from there, All changes
	 * will be made to the online Feed.
	 *
	 * @param feed
	 * @param s
	 * @return
	 * @throws PachubeException
	 */
	public boolean createDatastream(int feed, String s) throws PachubeException {
		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/feeds/"
				+ feed + "/datastreams/");
		hr.setMethod(HttpMethod.POST);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		hr.setBody(s);
		HttpResponse g = client.send(hr);

		if (g.getHeaderItem("Status").equals("HTTP/1.1 201 Created")) {
			return true;
		} else {
			throw new PachubeException(g.getHeaderItem("Status"));
		}
	}

	/**
	 * This Method is not intended to be used by Users, instead get the Feed
	 * object using getFeed() and delete Datastreams from there, All changes
	 * will be made to the online Feed.
	 *
	 * @param feed
	 * @param datastream
	 * @return
	 */
	public HttpResponse deleteDatastream(int feed, String datastream) {
		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/feeds/"
				+ feed + "/datastreams/" + datastream);
		hr.setMethod(HttpMethod.DELETE);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		return client.send(hr);
	}

	/**
	 * This Method is not intended to be used by Users, instead get the Feed
	 * object using getFeed() and update Datastreams from there, All changes
	 * will be made to the online Feed.
	 *
	 * @param feed
	 * @param datastream
	 * @param s
	 * @return
	 */
	public HttpResponse updateDatastream(int feed, String datastream, String s) {
		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/feeds/"
				+ feed + "/datastreams/" + datastream);
		hr.setMethod(HttpMethod.PUT);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		hr.setBody(s);
		System.out.println(hr.getHttpCommand());
		return client.send(hr);
	}

	/**
	 * This Method is not intended to be used by Users, instead get the Feed
	 * object using getFeed() and get Datastreams from there.
	 *
	 * @param feed
	 * @param datastream
	 * @return
	 */
	public HttpResponse getDatastream(int feed, String datastream) {
		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/feeds/"
				+ feed + "/datastreams/" + datastream + ".xml");
		hr.setMethod(HttpMethod.GET);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		return client.send(hr);
	}

	/**
	 * This Method is not intended to be used by Users, instead get the Feed
	 * object using getFeed() and access Datastream history from there.
   *
   * @deprecated Access to this data should just be via the API now.
	 *
	 * @param feed
	 * @param datastream
	 * @return
	 */
	@Deprecated public String[] getDatastreamHistory(int feed, String datastream) {
		HttpRequest hr = new HttpRequest("http://www.pachube.com/feeds/" + feed
				+ "/datastreams/" + datastream + "/history.csv");
		hr.setMethod(HttpMethod.GET);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		String str = client.send(hr).getBody();
		String[] arr = str.split(",");
		String[] history = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			history[i] = arr[1];
		}

		return history;

	}

	/**
	 * This Method is not intended to be used by Users, instead get the Feed
	 * object using getFeed() and access Datastream archive from there.
	 *
	 * @param feed
	 * @param datastream
	 * @return
	 */
	public String[] getDatastreamArchive(int feed, String datastream) {
		HttpRequest hr = new HttpRequest("http://www.pachube.com/feeds/" + feed
				+ "/datastreams/" + datastream + "/archive.csv");
		hr.setMethod(HttpMethod.GET);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		String str = client.send(hr).getBody();
		return str.split("\n");

	}

	/**
	 * Creates a Trigger on pachube from the object provided.
	 *
	 * @param t
	 * @return
	 * @throws PachubeException
	 */
	public String createTrigger(Trigger t) throws PachubeException {
		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/triggers");
		hr.setMethod(HttpMethod.POST);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		hr.setBody(t.toString());
		HttpResponse h = client.send(hr);
		if (h.getHeaderItem("Status").equals("HTTP/1.1 201 Created")) {
			return h.getHeaderItem("Location");
		} else {
			throw new PachubeException(h.getHeaderItem("Status"));
		}

	}

	/**
	 * Gets a Trigger from pachube specified by the parameter
	 *
	 * @param id id of the Trigger to get
	 */
	public Trigger getTrigger(int id) throws PachubeException {
		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/triggers/"+id+".xml");
		hr.setMethod(HttpMethod.GET);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		HttpResponse h = client.send(hr);

		return PachubeFactory.toTrigger(h.getBody())[0];

	}

	/**
	 * Gets all the Triggers owned by the authenticating user
	 *
	 * @param id id of the Trigger to get
	 */
	public Trigger[] getTriggers() throws PachubeException {
		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/triggers/");
		hr.setMethod(HttpMethod.GET);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		HttpResponse h = client.send(hr);

		return PachubeFactory.toTrigger(h.getBody());

	}

	/**
	 * Deletes a Trigger from pachube
	 * @param id id of the trigger to delete
	 * @return
	 */
	public HttpResponse deleteTrigger(int id){
		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/triggers/"+id);
		hr.setMethod(HttpMethod.DELETE);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		return client.send(hr);

	}

	/**
	 * Updates a Trigger on pachube
	 * @param id id of the triggerto update
	 * @param t Trigger object of the new trigger
	 * @return
	 */
	public HttpResponse updateTrigger(int id,Trigger t){
		HttpRequest hr = new HttpRequest("http://www.pachube.com/api/triggers/"+id);
		hr.setMethod(HttpMethod.PUT);
		hr.addHeaderItem("X-PachubeApiKey", this.apiKey);
		hr.setBody(t.toString());
		return client.send(hr);

	}

	/**
	 * Gets a Pachube graph of the datastream
	 *
	 * @param feedID
	 *            ID of feed the datastream belongs to.
	 * @param streamID
	 *            ID of the stream to graph
	 * @param width
	 *            Width of the image
	 * @param height
	 *            Height of the image
	 * @param c
	 *            Color of the line
	 * @return String which can be used to form a URL Object.
	 */
	public String showGraph(int feedID, String streamID, int width, int height,
			Color c) {
		String hexRed = Integer.toHexString(c.getRed()).toString();
		String hexGreen = Integer.toHexString(c.getGreen()).toString();
		String hexBlue = Integer.toHexString(c.getBlue()).toString();
		if (hexRed.length() == 1) {
			hexRed = "0" + hexRed;
		}

		if (hexGreen.length() == 1) {
			hexGreen = "0" + hexGreen;
		}
		if (hexBlue.length() == 1) {
			hexBlue = "0" + hexBlue;
		}
		String hex = (hexRed + hexGreen + hexBlue).toUpperCase();

		return "http://www.pachube.com/feeds/" + feedID + "/datastreams/"
				+ streamID + "/history.png?w=" + width + "&h=" + height + "&c="
				+ hex;

	}

	/**
	 * @return the client
	 */
	public HttpClient getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(HttpClient client) {
		this.client = client;
	}

}
