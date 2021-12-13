package controller.admin.actions;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AcquirableService;

@WebServlet("/admin/suggestions/delete.do")
public class SuggestionDeleteServlet extends HttpServlet {

	private static final long serialVersionUID = 3455721046062278592L;
	private AcquirableService acquirableService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.acquirableService = new AcquirableService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer suggestionId = Integer.parseInt(req.getParameter("id"));
		Boolean isPromotion = Boolean.parseBoolean(req.getParameter("prom"));
		
		if (acquirableService.delete(suggestionId, isPromotion)) {
			req.setAttribute("flash", "Se ha eliminado la sugerencia");
		} else
			req.setAttribute("flash", "Ha ocurrido un error con la accion");

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/suggestions.do");
		dispatcher.forward(req, resp);
	}
}
