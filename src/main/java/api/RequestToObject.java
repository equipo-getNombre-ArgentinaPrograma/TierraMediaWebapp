package api;

import org.json.JSONObject;

public class RequestToObject {
	public static Weather toWeather(String json) {
		JSONObject jsonObject = new JSONObject(json);
		JSONObject current = new JSONObject(jsonObject.get("current").toString());
		Weather weather = new Weather(current);
		return weather;
	}
}
