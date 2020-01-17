package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.Member;
import net.member.db.MemberDAO;

public class Admin_Member_updateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		String id = request.getParameter("id");
		MemberDAO mdao = new MemberDAO();
		Member m = mdao.member_info(id);
		
		if(m==null) {
			System.out.println("정보 가져오기 실패");
			forward.setRedirect(false);
			request.setAttribute("message","정보 가져오기 실패입니다.");
			forward.setPath("error/error.jsp");
			return forward;
		}
		
		forward.setPath("admin/updateForm.jsp");
		forward.setRedirect(false);
		request.setAttribute("member", m);
		return forward;
	}

}
