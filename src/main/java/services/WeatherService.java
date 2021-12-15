package services;

import java.io.IOException;
import api.*;

public class WeatherService {
	public Weather getWeather() {
		try {
			ApiRequest apiReq = new ApiRequest("http://api.weatherstack.com/current",
					"4e570a5dea560efee2cd786c015d308b", "Argentina");
			Weather weather = RequestToObject.toWeather(apiReq.makeRequest());
			return weather;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
