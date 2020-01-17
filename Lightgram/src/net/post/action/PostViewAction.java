package net.post.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.info.db.UserId_post;
import net.info.db.UserId_postDAO;
import net.member.db.MemberDAO;

public class PostViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		UserId_postDAO pdao = new UserId_postDAO();
		UserId_post uid = new UserId_post();
		MemberDAO mdao = new MemberDAO();
		
		String id = request.getParameter("id");
		String itemNum = request.getParameter("itemNum");
		String img = mdao.getProfileImg(id);
		
		uid = pdao.board(id, itemNum);
		
		response.setContentType("utf-8");
		request.setAttribute("post", uid);
		
		request.setAttribute("img", img);
		forward.setRedirect(false);
		forward.setPath("Post/postView.jsp");
		return forward;	
	}

}
