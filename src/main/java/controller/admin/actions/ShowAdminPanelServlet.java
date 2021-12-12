package controller.admin.actions;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import inObject.User;
import services.AdminService;

@WebServlet("/user/admin.do")
public class ShowAdminPanelServlet extends HttpServlet {
	AdminService adminService;
	private static final long serialVersionUID = 345572104606278592L;

	@Override
	public void init() throws ServletException {
		super.init();
		this.adminService = new AdminService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		RequestDispatcher dispatcher;
		if (adminService.isAdmin(user)) {
			req.setAttribute("choose", 0);
			dispatcher = getServletContext().getRequestDispatcher("/views/admin.jsp");
		} else {
			req.setAttribute("flash", "No posee los permisos necesarios para acceder.");
			dispatcher = getServletContext().getRequestDispatcher("/user.jsp");
		}
		dispatcher.forward(req, resp);
	}
}
