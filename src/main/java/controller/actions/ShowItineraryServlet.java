package controller.actions;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import outObject.Itinerary;
import inObject.User;
import services.ItineraryService;

@WebServlet("/user/itinerary.do")
public class ShowItineraryServlet extends HttpServlet {

	private static final long serialVersionUID = 345572104606278592L;
	private ItineraryService itService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.itService = new ItineraryService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		Itinerary itinerary = itService.getFor(user);

		req.setAttribute("itinerary", itinerary);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user.jsp");
		dispatcher.forward(req, resp);
	}
}
