package net.post.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;

import net.info.db.UserId_postDAO;

public class NewsfeedPostAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserId_postDAO udao = new UserId_postDAO();
		int scrollSize = Integer.parseInt(request.getParameter("scorllSize"));
		
		JsonArray jArray = udao.newsFeed(request.getSession().getAttribute("id").toString(), scrollSize);
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		
		out.print(jArray);
		return null;
	}

}
