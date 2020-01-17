package net.post.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.info.db.UserId_post;
import net.member.db.Member;
import net.member.db.MemberDAO;

public class MypageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		MemberDAO dao = new MemberDAO();
		Member m = new Member();
		HttpSession session = request.getSession();
		String id = session.getAttribute("id").toString();
		
		m = dao.getprofile(id);
		
		String name = m.getName();
		String email = m.getEmail();
		
		String pic_url = dao.getProfileImg(id);
		
		List<UserId_post> profileList = dao.getMyProfileList(id);
		
		request.setAttribute("pic_url", pic_url);
		request.setAttribute("profileList", profileList);
		request.setAttribute("name", name);
		request.setAttribute("email", email);
		forward.setRedirect(false);
		forward.setPath("Post/Mypage.jsp");
		return forward;
	}
}
