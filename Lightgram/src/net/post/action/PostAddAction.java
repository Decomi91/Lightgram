package net.post.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.info.db.HashTagsDAO;
import net.info.db.LocationsDAO;
import net.info.db.UserId_postDAO;
import net.info.db.UserId_post;

public class PostAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		UserId_postDAO udao = new UserId_postDAO();
		UserId_post uid = new UserId_post();
		ActionForward forward = new ActionForward();
		String id = request.getSession().getAttribute("id").toString();
		int itemNum = udao.countItemNum(id); 
		String path = "D:\\KH\\Semi_project\\Lightgram\\WebContent\\id\\"+id; //폴더 경로
		File Folder = new File(path);

		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder.exists()) {
			try{
			    Folder.mkdir(); //폴더 생성합니다.
			    System.out.println("폴더가 생성되었습니다.");
			}catch(Exception e){
			    e.getStackTrace();
			}        
		}else {
			System.out.println("이미 폴더가 생성되어 있습니다.");
		}
		
		path = "D:\\KH\\Semi_project\\Lightgram\\WebContent\\id\\"+id+"\\"+itemNum; //폴더 경로
		Folder = new File(path);

		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder.exists()) {
			try{
			    Folder.mkdir(); //폴더 생성합니다.
			    System.out.println("폴더가 생성되었습니다.");
			}catch(Exception e){
			    e.getStackTrace();
			}        
		}else {
			System.out.println("이미 폴더가 생성되어 있습니다.");
		}
		
		String realFoler = "";
		String saveFolder = id+"/"+itemNum;
		
		int fileSize = 100*1024*1024; // 업로드할 파일의 최대 사이즈 (100MB)
		
		//실제 저장경로 지정
		ServletContext sc = request.getServletContext();
		realFoler = sc.getRealPath(saveFolder);
		
		System.out.println("realFolder = " + realFoler);
		boolean result = false;
		
		MultipartRequest multi = new MultipartRequest(request, path, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		int level = Integer.parseInt(multi.getParameter("level"));
		String pic_url = multi.getFilesystemName("pic_url");
		String location="";
		if(!multi.getParameter("location").trim().equals("")) {
			location = replaceParameter(multi.getParameter("location"));
			LocationsDAO ldao = new LocationsDAO();
			ldao.insertLocations(id, itemNum, location);
		}
		if(multi.getParameter("content").trim().equals("")) {
			PrintWriter out = response.getWriter();
			out.print("<script> alert('글 내용을 입력해주세요'); history.back(); </script>");
			return null;
		}
		String data = replaceParameter(id, itemNum, multi.getParameter("content"));
		
		uid.setId(id);
		uid.setItemNum(itemNum);
		uid.setLevel(level);
		uid.setPic_url(pic_url);
		uid.setLocation(location);
		uid.setDataText(data);
		
		result = udao.addItem(uid);
		
		if(!result) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('등록시 문제 발생'); history.back() </script>");
			out.close();
			return null;
		}
		forward.setRedirect(true);
		forward.setPath("/Lightgram/AddF.do");
		
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
			result = result.replace(m.group(), "<a href='Search.do?search_word=" + m.group().substring(1) + "&&search_option=hash_sel'>" + m.group() + "</a>");
		}
		
		return result; 
	}

}
