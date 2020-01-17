package net.post.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.info.db.UserId_FollowDAO;

public class FollowerListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		
		UserId_FollowDAO ufdao = new UserId_FollowDAO();
		List<String>[] follows = ufdao.followerList(id);
		
		JsonArray idList = new JsonArray();
		JsonArray picList = new JsonArray();
		JsonArray jobj = new JsonArray();
		for(String f : follows[2]) {
			if(follows[0].contains(f)) {
				jobj.add(1);
			}else {
				jobj.add(0);
			}
			idList.add(f);
		}
		for(String f : follows[1]) {
			picList.add(f);
		}
		
		JsonArray jarray = new JsonArray();
		
		jarray.add(idList);
		jarray.add(picList);
		jarray.add(jobj);
		
		PrintWriter out = response.getWriter();
		out.print(jarray);
		
		return null;
	}

}
