package net.post.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.info.db.UserId_likeDAO;

public class PostLikeListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserId_likeDAO uidao = new UserId_likeDAO();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String loginId = request.getParameter("loginId");
		String postId = request.getParameter("postId");
		int postNum = Integer.parseInt(request.getParameter("itemNum"));
		
		
		JsonArray result = uidao.getLikes(loginId, postId, postNum);
		
		out.print(result);
		return null;
	}

}
