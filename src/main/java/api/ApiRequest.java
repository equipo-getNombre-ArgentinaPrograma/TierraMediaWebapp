package api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiRequest {
	String reqUrl;

	public ApiRequest(String url, String access_key, String location) {
		reqUrl = url + "?access_key=" + access_key + "&query=" + location;
	}

	public String makeRequest() throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(reqUrl)).GET().build();

		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		String jsonResponse = response.body();
		return jsonResponse;
	}
}