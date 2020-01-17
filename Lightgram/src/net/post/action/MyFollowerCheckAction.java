package net.post.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDAO;

public class MyFollowerCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		MemberDAO dao = new MemberDAO();
		String id = session.getAttribute("id").toString();
		PrintWriter out = response.getWriter();
		
		int count = dao.followercount(id);
		
		out.print(count);
		return null;
	}

}
