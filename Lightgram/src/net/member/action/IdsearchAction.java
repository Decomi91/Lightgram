package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.Member;
import net.member.db.MemberDAO;

public class IdsearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		ActionForward forward = new ActionForward();
		String email = request.getParameter("email");
		MemberDAO dao = new MemberDAO();
		Member m = dao.id(email);
		
		PrintWriter out = response.getWriter();
		if(m==null) {
			out.println("<script>");
			out.println("alert('아이디가 존재하지 않습니다.')");
			out.println("history.back();");
			out.println("#email.focus();");
			out.println("</script>");
			out.close();
			
			return null;
		}
			
		out.println("<script>");
		out.println("alert('아이디는 :" + m.getId() + "');");
		out.println("location.href='login.net'");
		out.println("</script>");
		out.close();
		return null;
	}

}
