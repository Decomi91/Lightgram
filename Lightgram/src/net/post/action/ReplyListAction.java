package net.post.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;

import net.info.db.UserId_reply;
import net.info.db.UserId_replyDAO;

public class ReplyListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserId_replyDAO urdao = new UserId_replyDAO();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String postId = request.getParameter("postId");
		int postNum = Integer.parseInt(request.getParameter("itemNum"));
		
		JsonArray jArray = urdao.getList(postId, postNum);
		out.print(jArray);
		return null;
	}

}
