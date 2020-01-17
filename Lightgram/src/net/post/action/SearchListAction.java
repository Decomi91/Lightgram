package net.post.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.info.db.UserId_FollowDAO;
import net.info.db.UserId_post;
import net.info.db.UserId_postDAO;

public class SearchListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<UserId_post> list = new ArrayList<UserId_post>();
		UserId_postDAO dao = new UserId_postDAO();
		
		HttpSession session = request.getSession();
		boolean result = false;
		String id = session.getAttribute("id").toString();
		UserId_FollowDAO ufdao = new UserId_FollowDAO();
		request.setCharacterEncoding("utf-8");
		
		if(request.getParameter("search_word") != null) {
			String search_option = request.getParameter("search_option");
			String search = request.getParameter("search_word");
			if(search_option.equals("id_sel")) {
				list = dao.getList(id, search, "id");
				if(ufdao.followingList(id)[0].contains(search)) {
					request.setAttribute("followStatus", 1);
				} else {
					request.setAttribute("followStatus", 0);
				}
				search_option = "ID";
			} else if (search_option.equals("name_sel")) {
				list = dao.getList(id, search, "name");
				search_option = "이름";
			} else if (search_option.equals("hash_sel")) {
				list = dao.getList2(id, search, "hashTag");
				if(ufdao.followingList(id)[2].contains(search)) {
					request.setAttribute("followStatus", 1);
				} else {
					request.setAttribute("followStatus", 0);
				}
				search_option = "해시태그";
			} else if (search_option.equals("location_sel")) {
				list = dao.getList2(id, search, "location");
				search_option = "지역";
			}
			request.setAttribute("search_word", search);
			request.setAttribute("search_option", search_option);
			result = true;
		}
		if(!result)
			list = dao.getList(id);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("");
		return null;
	}

}
