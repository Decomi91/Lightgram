package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.Member;
import net.member.db.MemberDAO;

public class PassModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("utf-8");
		MemberDAO dao = new MemberDAO();
		Member m = new Member();
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String pass = request.getParameter("password");
		String newpass = request.getParameter("newpassword");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		boolean result2 = false;
		int result = 0;
		result = dao.passcheck(id , pass);
		result2 = dao.passmodify(id , newpass);
		
		if(result == 1 && result2 == true){
			out.println("<script>");
			out.println("alert('비밀번호 변경 완료');");
			out.println("location.href='Mypage.do'");
			out.println("</script>");
			out.close();
			return null;
		} else if(result2 == true || result == 1) {
			response.setContentType("text/html;charset=utf-8");
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		} else {
			forward.setRedirect(false);
			request.setAttribute("message", "비밀번호 변경 실패");
			forward.setPath("error/error.jsp");
			return forward;
		}
	}
}
