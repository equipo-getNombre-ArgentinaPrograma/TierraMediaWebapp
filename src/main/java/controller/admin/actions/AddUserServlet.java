package controller.admin.actions;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AdminService;

@WebServlet("/users/add.do")
public class AddUserServlet extends HttpServlet {

	private static final long serialVersionUID = 34557210462278592L;
	private AdminService adminService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.adminService = new AdminService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		String name = req.getParameter("name");
		Double coins = Double.parseDouble(req.getParameter("coins"));
		Double time = Double.parseDouble(req.getParameter("time"));
		String type = req.getParameter("type");
		System.out.println(req.getParameter("admin"));
		Integer admin = Integer.parseInt(req.getParameter("admin"));

		if (adminService.createUser(name, coins, time, type, admin))
			req.setAttribute("flash", "¡Se ha agregado el usuario!");
		else
			req.setAttribute("flash", "Hubo un error.");
		dispatcher = getServletContext().getRequestDispatcher("/views/create.jsp");
		dispatcher.forward(req, resp);
	}
}
