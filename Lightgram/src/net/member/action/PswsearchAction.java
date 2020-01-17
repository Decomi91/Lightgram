package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.Member;
import net.member.db.MemberDAO;

public class PswsearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		ActionForward forward = new ActionForward();
		String id = request.getParameter("id");		
		String email = request.getParameter("email");
		MemberDAO dao = new MemberDAO();
		Member m = dao.pass(id, email);
		
		PrintWriter out = response.getWriter();
		if(m==null) {
			out.println("<script>");
			out.println("alert('해당하는 ID가 없습니다.')");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			
			return null;
		}
			
		out.println("<script>");
		out.println("alert('비밀번호는 :" + m.getPassword() + "');");
		out.println("location.href='login.net'");
		out.println("</script>");
		out.close();
		return null;
	}
	
	}


