package net.post.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.info.db.UserId_reply;
import net.info.db.UserId_replyDAO;

public class ReplyDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String postid = request.getParameter("postid");
		int itemNum = Integer.parseInt(request.getParameter("itemNum"));
		int reply_no = Integer.parseInt(request.getParameter("reply_no"));
		UserId_reply ure = new UserId_reply();
		ure.setItemNum(itemNum);
		ure.setReply_no(reply_no);
		
		UserId_replyDAO urdao = new UserId_replyDAO();
		
		boolean result = urdao.deleteComment(ure, postid); 
		
		if(!result) {
			return null;
		}
		out.print("1");
		return null;
	}

}
