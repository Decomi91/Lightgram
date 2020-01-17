package net.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.Member;
import net.member.db.MemberDAO;

public class MemberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		MemberDAO mdao = new MemberDAO();
		
		int page = 1;
		int limit = 3;
		
		if(request.getParameter("page")!=null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		System.out.println("넘어온 페이지 = " + page);
		
		List<Member> list = null;
		int listcount = 0;
		int index = -1;
		String search_word="";
		
		if(request.getParameter("search_word") == null 
				|| request.getParameter("search_word").equals("")) {
			
			listcount = mdao.getListCount();
			list = mdao.searchAll(page,limit);
		} else {
			index = Integer.parseInt(request.getParameter("search_field"));
			String search_field[] = new String[] {"id","name","email"};
			
			search_word = request.getParameter("search_word");
			listcount =
					mdao.getListCount(search_field[index], search_word);
			
			list
			= mdao.searchAll(search_field[index], search_word, page, limit);
		}
		
		int maxpage = (listcount+limit-1)/limit;
		System.out.println("총 페이지수 =" + maxpage);
		
		int startpage = ((page-1) / 10) * 10 + 1;
		System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);
		
		int endpage = startpage + 10 - 1;
		System.out.println("현재 페이지에 보여줄 마지막 페이지 수 = " + endpage);
		
		if(endpage > maxpage) endpage = maxpage;
		
		request.setAttribute("page", page);
		request.setAttribute("maxpage", maxpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("listcount", listcount);
		request.setAttribute("totallist", list);
		request.setAttribute("search_field", index);
		request.setAttribute("search_word", search_word);
		
		forward.setPath("admin/member_list.jsp");
		forward.setRedirect(false);		
		return forward;
	}

}
