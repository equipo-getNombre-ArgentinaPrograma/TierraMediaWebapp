package controller.session;


import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.getSession().removeAttribute("user");
		req.setAttribute("flash", "¡Hasta pronto!");
		
		System.out.println("Se cierra una sesion");
		
		RequestDispatcher dispatcher = getServletContext()
  		      .getRequestDispatcher("/index.jsp");
  		    dispatcher.forward(req, resp); 	
    }
}
