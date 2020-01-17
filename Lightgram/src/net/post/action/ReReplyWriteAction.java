package net.post.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.info.db.UserId_reply;
import net.info.db.UserId_replyDAO;

public class ReReplyWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String reply_id = request.getParameter("reply_id");
		String postid = request.getParameter("postid");
		int itemNum = Integer.parseInt(request.getParameter("itemNum"));
		int ref_no = Integer.parseInt(request.getParameter("ref_no"));
		String comments = replaceParameter(request.getParameter("comment"));
		
		UserId_reply ure = new UserId_reply();
		ure.setItemNum(itemNum);
		ure.setRef_no(ref_no);
		ure.setReply_id(reply_id);
		ure.setComments(comments);
		
		UserId_replyDAO urdao = new UserId_replyDAO();
		
		boolean result = urdao.writeReComment(ure, postid); 
		
		if(!result) {
			return null;
		}
		out.print("1");
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
