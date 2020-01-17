package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDAO;

public class LoginProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		MemberDAO dao = new MemberDAO();
		int result = dao.isId(request.getParameter("id"), request.getParameter("pass"));
		
		if(result == 0) {
			HttpSession session = request.getSession();
			session.setAttribute("id", request.getParameter("id"));
			forward.setRedirect(true);
			forward.setPath("/Lightgram/Newsfeed.do");
		}else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('로그인 시 문제가 발생했습니다.'); history.back() </script>");
			out.close();
			return null;
		}
		
		return forward;
	}

}
