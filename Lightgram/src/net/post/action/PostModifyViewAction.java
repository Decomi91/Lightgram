package net.post.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.info.db.HashTagsDAO;
import net.info.db.UserId_post;
import net.info.db.UserId_postDAO;
import net.member.db.MemberDAO;

public class PostModifyViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		
		UserId_postDAO uiddao = new UserId_postDAO();
		UserId_post post = new UserId_post();
		MemberDAO mdao = new MemberDAO();
		
		String id = request.getParameter("id");
		String itemNum = request.getParameter("itemNum");
		String img = mdao.getProfileImg(id);
		
		post=uiddao.board(id, itemNum);
		
		post.setDataText(replaceAnker(post.getDataText()));
		
		response.setContentType("utf-8");
		request.setAttribute("post", post);
		request.setAttribute("img", img);
		forward.setRedirect(false);
		forward.setPath("Post/modifyForm.jsp");
		return forward;
	}

	private String replaceAnker(String dataText) {
		if(dataText == null) {
			return "";
		}
		for(int i=0; i<dataText.length(); i++) {
			if(dataText.indexOf("<a") >= 0) {
				i = dataText.indexOf("<a");
				int j = dataText.indexOf("sel'>");
				String subText = new String(dataText);
				dataText = subText.substring(0,i) + dataText.substring(j+5);
				
				i=dataText.indexOf("</a>");
				subText = new String(dataText);
				dataText = subText.substring(0,i) + dataText.substring(i+4);
			}
		}
		dataText = dataText.trim();
		return dataText;
	}
}