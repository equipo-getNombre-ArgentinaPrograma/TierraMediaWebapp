package controller.admin.actions;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import outObject.Purchase;
import services.AdminService;

@WebServlet("/admin/history.do")
public class ShowHistoryServlet extends HttpServlet {
	AdminService adminService;
	private static final long serialVersionUID = 345572104606278592L;

	@Override
	public void init() throws ServletException {
		super.init();
		this.adminService = new AdminService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<Purchase> purchases = adminService.getHistory();
		req.setAttribute("purchases", purchases);
		req.setAttribute("choose", 3);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/admin.jsp");
		dispatcher.forward(req, resp);
	}
}
