package controller.admin.actions;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import inObject.Attraction;
import services.AcquirableService;

@WebServlet("/suggestions/createA.do")
public class CreateAttractionServlet extends HttpServlet {

	private static final long serialVersionUID = 3455721046062278592L;
	private AcquirableService acquirableService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.acquirableService = new AcquirableService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/suggestions/createAttraction.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		Double cost = Double.parseDouble(req.getParameter("cost"));
		Double duration = Double.parseDouble(req.getParameter("duration"));
		Integer capacity = Integer.parseInt(req.getParameter("capacity"));
		String type = req.getParameter("type");
		String description = req.getParameter("description");

		Attraction attraction = acquirableService.createAttraction(name, cost, duration, capacity, type, description);
		if (attraction.isValid()) {
			resp.sendRedirect("/turismo/attractions/index.do");
		} else {
			req.setAttribute("attraction", attraction);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/suggestions/create.jsp");
			dispatcher.forward(req, resp);
		}
	}
}
