package controller.admin.actions;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AdminService;

@WebServlet("/admin/users/add.do")
public class UserCreateServlet extends HttpServlet {

	private static final long serialVersionUID = 34557210462278592L;
	private AdminService adminService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.adminService = new AdminService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("choose", 0);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		Double coins = Double.parseDouble(req.getParameter("coins"));
		Double time = Double.parseDouble(req.getParameter("time"));
		String type = req.getParameter("type");
		Integer admin = Integer.parseInt(req.getParameter("admin"));
		
		System.out.println("hola");
		
		if (adminService.createUser(name, password, coins, time, type, admin))
			req.setAttribute("flash", "¡Se ha agregado el usuario!");
		else
			req.setAttribute("flash", "Hubo un error.");
		dispatcher = getServletContext().getRequestDispatcher("/user/admin.do");
		dispatcher.forward(req, resp);
	}
}
