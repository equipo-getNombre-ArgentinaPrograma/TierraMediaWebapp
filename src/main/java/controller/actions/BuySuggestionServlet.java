package controller.actions;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import inObject.User;
import dao.UserDAO;
import services.BuyService;

@WebServlet("/suggestions/buy.do")
public class BuySuggestionServlet extends HttpServlet {

	private static final long serialVersionUID = 3455721046062278592L;
	private BuyService buyService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.buyService = new BuyService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer suggestionId = Integer.parseInt(req.getParameter("id"));
		Boolean isPromotion = Boolean.parseBoolean(req.getParameter("prom"));
		User user = (User) req.getSession().getAttribute("user");
		
		if (buyService.buy(user.getName(), suggestionId, isPromotion)) {
			User userAct = UserDAO.findByUsername(user.getName());
			req.getSession().setAttribute("user", userAct);
			req.setAttribute("flash", "¡Gracias por comprar!");
		} else
			req.setAttribute("flash", "Ha ocurrido un error con su compra");

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/suggestions/index.do");
		dispatcher.forward(req, resp);
	}
}
