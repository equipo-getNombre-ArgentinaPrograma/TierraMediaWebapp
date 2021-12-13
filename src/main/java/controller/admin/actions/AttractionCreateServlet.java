package controller.admin.actions;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AcquirableService;

@WebServlet("/admin/suggestions/createA.do")
public class AttractionCreateServlet extends HttpServlet {

	private static final long serialVersionUID = 3455721046062278592L;
	private AcquirableService acquirableService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.acquirableService = new AcquirableService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("choose", 1);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		String name = req.getParameter("name");
		Double price = Double.parseDouble(req.getParameter("price"));
		Double duration = Double.parseDouble(req.getParameter("duration"));
		Integer quota = Integer.parseInt(req.getParameter("quota"));
		String type = req.getParameter("type");
		String description = req.getParameter("description");

		if (acquirableService.createAttraction(name, price, duration, quota, type, description))
			req.setAttribute("flash", "¡Se ha creado la atraccion!");
		else
			req.setAttribute("flash", "Hubo un error.");
		dispatcher = getServletContext().getRequestDispatcher("/user/admin.do");
		dispatcher.forward(req, resp);
	}
}
