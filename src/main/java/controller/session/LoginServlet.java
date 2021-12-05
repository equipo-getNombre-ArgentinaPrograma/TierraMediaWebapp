package controller.session;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import inObject.User;
import services.LoginService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginService loginService;

	@Override
	public void init() throws ServletException {
		super.init();
		loginService = new LoginService();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter response = resp.getWriter();
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String exitStatus = null;
		
		User user = loginService.login(username, password);
		
		if (!user.isNull()) {
			req.getSession().setAttribute("user", user);
			exitStatus = "1";
			jsonObj.put("status", exitStatus);
			jsonArray.put(jsonObj);
			response.println(jsonArray.toString());
			response.flush();
		} else {
			exitStatus = "2";
			jsonObj.put("status", exitStatus);
			jsonArray.put(jsonObj);
			response.println(jsonArray.toString());
			response.flush();
		}
	}
}
