package net.post.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import net.info.db.UserId_reply;
import net.info.db.UserId_replyDAO;

public class NewsfeedReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String replyId = request.getParameter("replyId");
		String postid = request.getParameter("postId");
		int itemNum = Integer.parseInt(request.getParameter("itemNum"));
		String comments = replaceParameter(request.getParameter("comments"));
		
		UserId_reply ure = new UserId_reply();
		ure.setItemNum(itemNum);
		ure.setReply_id(replyId);
		ure.setComments(comments);
		
		UserId_replyDAO urdao = new UserId_replyDAO();
		
		JsonObject jobj = urdao.writeComment(ure, postid, "newsfeed"); 
		
		if(jobj == null) {
			return null;
		}
		out.print(jobj);
		return null;
	}
	
	private String replaceParameter(String param) {
		String result = param;
		if(param != null) {
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}
		
		return result;	
	}
}
