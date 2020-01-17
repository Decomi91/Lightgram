package net.member.action;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.info.db.UserId_FollowDAO;

public class FollowAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getSession().getAttribute("id").toString();
		
		
		String selectedId = "";
		String selectedHash = "";
		int result = -1;
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		if(request.getParameter("selectedId")!= null) {
			selectedId = request.getParameter("selectedId");
			UserId_FollowDAO ufdao = new UserId_FollowDAO(); 
			result = ufdao.followId(id, selectedId);
		}
		
		if(request.getParameter("selectedHash")!= null) {
			selectedHash = request.getParameter("selectedHash");
			UserId_FollowDAO ufdao = new UserId_FollowDAO(); 
			result = ufdao.followHash(id, selectedHash);
		}
		
		PrintWriter out = response.getWriter();
		if(result == -1) {
			out.print("<script> alert('팔로우 액션 시 에러 발생'); history.back(); </script>");
			return null;
		}
		
		if(request.getParameter("path") != null) {
			String[] path = request.getParameter("path").replaceFirst("_", "?").split(" ");
			
			String repath = path[0]+"&&"+path[1];
			
			forward.setRedirect(false);
			forward.setPath(repath);
			return forward;
		}
		
		forward.setRedirect(true);
		forward.setPath("/Lightgram/Mypage.do");
		return forward;
	}

}
