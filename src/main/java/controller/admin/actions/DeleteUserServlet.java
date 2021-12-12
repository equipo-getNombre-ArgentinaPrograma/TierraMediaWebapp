package controller.admin.actions;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AdminService;

@WebServlet("/admin/users/delete.do")
public class DeleteUserServlet extends HttpServlet {

	private static final long serialVersionUID = 3455721046062278592L;
	private AdminService adminService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.adminService = new AdminService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer userId = Integer.parseInt(req.getParameter("id"));
		
		if (adminService.deleteUser(userId)) {
			req.setAttribute("flash", "Se ha eliminado el usuario");
		} else
			req.setAttribute("flash", "Ha ocurrido un error con la accion");

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/users.do");
		dispatcher.forward(req, resp);
	}
}
