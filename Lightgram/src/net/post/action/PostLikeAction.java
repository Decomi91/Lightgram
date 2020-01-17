package net.post.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.info.db.UserId_likeDAO;

public class PostLikeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String loginId = request.getParameter("loginId");
		String postId = request.getParameter("postId");
		int itemNum = Integer.parseInt(request.getParameter("itemNum"));
		
		UserId_likeDAO uidao = new UserId_likeDAO();
		
		int result = uidao.setLike(loginId, postId, itemNum);
		
		PrintWriter out = response.getWriter();
		
		if(result == -1) {
			out.print(-1);
			return null;
		}
		
		out.print(result);
		return null;
	}

}
