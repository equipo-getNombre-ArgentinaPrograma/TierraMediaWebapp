package controller.admin.actions;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AcquirableService;

@WebServlet("/admin/suggestions/editA.do")
public class AttractionEditServlet extends HttpServlet {

	private static final long serialVersionUID = 3455721046062278592L;
	private AcquirableService acquirableService;
	private Integer attractionId;

	@Override
	public void init() throws ServletException {
		super.init();
		this.acquirableService = new AcquirableService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("choose", 1);
		attractionId = Integer.parseInt(req.getParameter("id"));
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/edit.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		Double newPrice = Double.parseDouble(req.getParameter("price"));
		Double newDuration = Double.parseDouble(req.getParameter("duration"));
		Integer newQuota = Integer.parseInt(req.getParameter("quota"));
		String newDescription = req.getParameter("description");

		if (acquirableService.editAttraction(attractionId, newPrice, newDuration, newQuota, newDescription))
			req.setAttribute("flash", "¡Se ha editado la atraccion!");
		else
			req.setAttribute("flash", "Hubo un error.");
		dispatcher = getServletContext().getRequestDispatcher("/user/admin.do");
		dispatcher.forward(req, resp);
	}
}
