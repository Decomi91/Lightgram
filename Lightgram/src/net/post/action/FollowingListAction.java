package net.post.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.info.db.UserId_FollowDAO;

public class FollowingListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		response.setContentType("text/html;charset=utf-8");
		UserId_FollowDAO ufdao = new UserId_FollowDAO();
		List<String>[] follows = ufdao.followingList(id);
		
		
		JsonArray idList = new JsonArray();
		JsonArray picList = new JsonArray();
		JsonArray hashTagList = new JsonArray();
		for(String f : follows[0]) {
			idList.add(f);
		}
		for(String f : follows[1]) {
			picList.add(f);
		}
		for(String f : follows[2]) {
			hashTagList.add(f);
		}
		JsonArray jarray = new JsonArray();
		jarray.add(idList);
		jarray.add(picList);
		jarray.add(hashTagList);
		PrintWriter out = response.getWriter();
		out.print(jarray);
		return null;
	}

}
