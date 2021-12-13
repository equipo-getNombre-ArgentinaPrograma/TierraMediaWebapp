package controller.admin.actions;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AdminService;

@WebServlet("/admin/users/edit.do")
public class UserEditServlet extends HttpServlet {

	private static final long serialVersionUID = 3455721046062278592L;
	private AdminService adminService;
	private Integer userId;

	@Override
	public void init() throws ServletException {
		super.init();
		this.adminService = new AdminService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("choose", 0);
		userId = Integer.parseInt(req.getParameter("id"));
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/edit.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		String newName = req.getParameter("name");
		String newPassword = req.getParameter("password");
		Double newCoins = Double.parseDouble(req.getParameter("coins"));
		Double newTime = Double.parseDouble(req.getParameter("time"));
		String newType = req.getParameter("type");
		
		if (adminService.editUser(userId, newName, newPassword, newCoins, newTime, newType))
			req.setAttribute("flash", "¡Se ha editado el usuario!");
		else
			req.setAttribute("flash", "Hubo un error.");
		dispatcher = getServletContext().getRequestDispatcher("/user/admin.do");
		dispatcher.forward(req, resp);
	}
}
