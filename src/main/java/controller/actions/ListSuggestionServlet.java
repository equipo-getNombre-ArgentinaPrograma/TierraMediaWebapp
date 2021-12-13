package controller.actions;

import java.io.IOException;
import java.util.List;

import inObject.Acquirable;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import inObject.User;
import services.AcquirableService;

@WebServlet("/suggestions/index.do")
public class ListSuggestionServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;
	private AcquirableService acquirablesService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.acquirablesService = new AcquirableService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) ((HttpServletRequest) req).getSession().getAttribute("user");
		acquirablesService.to(user);
		
		List<Acquirable> suggestions = acquirablesService.list();
		req.setAttribute("suggestions", suggestions);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/destinos.jsp");
		dispatcher.forward(req, resp);
	}
}
