package net.post.action;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.info.db.HashTagsDAO;
import net.info.db.LocationsDAO;
import net.info.db.UserId_post;
import net.info.db.UserId_postDAO;

public class PostModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		
		String id = request.getSession().getAttribute("id").toString();
		int itemNum = Integer.parseInt(request.getParameter("itemNum"));
		
		HashTagsDAO hdao = new HashTagsDAO();
		hdao.deleteHashTag(id, itemNum);
		LocationsDAO ldao = new LocationsDAO();
		ldao.deleteLocation(id, itemNum);
		UserId_postDAO udao = new UserId_postDAO();
		UserId_post uid = new UserId_post();
		
		int level = Integer.parseInt(request.getParameter("level"));
		String location = "";
		String text = "";
		String data = "";
		if(request.getParameter("location") != null) {
			location = replaceParameter(request.getParameter("location"));
			ldao.insertLocations(id, itemNum, location);
		}
		if(request.getParameter("detail_text") != null) {
			text = request.getParameter("detail_text");
			data = replaceParameter(id, itemNum, text);
		}
		
		uid.setId(id);
		uid.setItemNum(itemNum);
		uid.setLevel(level);
		uid.setLocation(location);
		uid.setDataText(data);
		
		boolean result = udao.modifyPost(uid);
		
		if(!result) {
			PrintWriter out = response.getWriter();
			out.append("<script>alert('수정시 문제 발생'); history.back();</script>");
			out.close();
			return null;
		}
		forward.setRedirect(true);
		forward.setPath("/Lightgram/postView.do?id="+id+"&itemNum="+itemNum);
		
		return forward;
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
	
	private String replaceParameter(String id, int itemNum, String param) {
		String result = param;
		if(param != null) {
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}
		result = setHashTag(id, itemNum, result);
		return result;	
	}
	
	private String setHashTag(String id, int itemNum, String content) {
		
		Pattern p = Pattern.compile("\\#([0-9a-zA-Z가-힣_]*)");
		Matcher m = p.matcher(content);
		
		HashTagsDAO hdao = new HashTagsDAO();
		String result = content;
		while(m.find()) {
			hdao.insertHashTag(id, itemNum, m.group());
			result = result.replace(m.group(), "<a href='Search.do?search_word=" + m.group() + "&&search_option=hash_sel'>" + m.group() + "</a>");
		}
		
		return result; 
	}
}
