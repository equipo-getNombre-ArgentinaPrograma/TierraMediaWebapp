package api;

import org.json.JSONObject;

public class Weather {
	private Double temperature;
	private String weather_descriptions,
				   weather_icons;

	public Weather(JSONObject jsonObject) {
		this.temperature = jsonObject.getDouble("temperature");
		this.weather_descriptions = jsonObject.get("weather_descriptions").toString();
		this.weather_icons = jsonObject.get("weather_icons").toString();
	}

	public Double getTemperature() {
		return temperature;
	}

	public String getWeatherDescriptions() {
		return weather_descriptions.substring(2, weather_descriptions.length()-2);
	}

	public String getWeatherIcons() {
		return weather_icons.substring(2, weather_icons.length()-2);
	}

	@Override
	public String toString() {
		return "temperature: " + getTemperature() + "\ndescriptions: " + getWeatherDescriptions()
				+ "\nicons: " + getWeatherIcons();
	}
}
