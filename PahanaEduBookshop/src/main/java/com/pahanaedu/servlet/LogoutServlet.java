package com.pahanaedu.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1765053249551203796L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("login.jsp");
    }
}