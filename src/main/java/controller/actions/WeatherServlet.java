package controller.actions;

import java.io.IOException;
import java.io.PrintWriter;

import api.Weather;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.WeatherService;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/weather.do")
public class WeatherServlet extends HttpServlet {

	private static final long serialVersionUID = 345572104606278592L;
	private WeatherService wService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.wService = new WeatherService();
		System.out.println("weatherServlet");
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter response = resp.getWriter();
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		
		System.out.println("Buscando datos del tiempo actual...");
		Weather weather = wService.getWeather();
		
		String exitStatus = null;
		
		if (weather != null) {
			exitStatus = "1";
			jsonObj.put("status", exitStatus);
			jsonObj.put("temperature", weather.getTemperature());
			jsonObj.put("description", weather.getWeatherDescriptions());
			jsonObj.put("icon", weather.getWeatherIcons());
		} else {
			exitStatus = "2";
		}
		jsonObj.put("status", exitStatus);
		jsonArray.put(jsonObj);
		response.println(jsonArray.toString());
		response.flush();
	}
}
