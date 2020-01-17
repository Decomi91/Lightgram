package net.lightgram.start;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/lightgram")
public class start extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public start() {
		super();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String id="";
		String cookie = request.getHeader("Cookie");
		
		if(cookie != null) {
			Cookie[] cookies = request.getCookies();
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("id")) {
					id = cookies[i].getValue();
				}
			}
			
			
			RequestDispatcher dispatcher =  request.getRequestDispatcher("/login.net");
			request.setAttribute("id", id);
			dispatcher.forward(request, response);
			return;
		}
		
		RequestDispatcher dispatcher =  request.getRequestDispatcher("/login.net");
		dispatcher.forward(request, response);			
	}	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}
}
