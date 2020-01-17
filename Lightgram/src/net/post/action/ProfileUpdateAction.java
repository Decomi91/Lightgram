package net.post.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.Member;
import net.member.db.MemberDAO;

import net.post.action.Action;
import net.post.action.ActionForward;

public class ProfileUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		MemberDAO dao = new MemberDAO();
		Member m = new Member();
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String pic_url = dao.getProfileImg(id);
		
		m = dao.getprofile(id);
		
		if(m == null) {
			forward.setRedirect(false);
			request.setAttribute("message", "프로필 편집 페이지 상세보기 실패");
			forward.setPath("error/error.jsp");
			return forward;
		}
		request.setAttribute("member", m);
		request.setAttribute("pic_url", pic_url);
		forward.setRedirect(false);
		forward.setPath("Post/profile.jsp");
		return forward;
	}
}
