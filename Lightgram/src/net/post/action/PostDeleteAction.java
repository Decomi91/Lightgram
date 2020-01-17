package net.post.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.info.db.UserId_postDAO;

public class PostDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		String id = request.getParameter("id");
		int itemNum = Integer.parseInt(request.getParameter("itemNum"));
		UserId_postDAO udao = new UserId_postDAO();
		
		boolean result = udao.deletePost(id, itemNum);
		
		deleteFile("D:\\KH\\Semi_project\\Lightgram\\WebContent\\id\\"+id+"\\"+itemNum);
		
		if(!result) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('삭제시 문제 발생'); history.back() </script>");
			out.close();
			return null;
		}
		
		forward.setRedirect(true);
		forward.setPath("/Lightgram/Mypage.do");
		return forward;
	}
	
	
	public static void deleteFile(String path) {
		File deleteFolder = new File(path);

		if(deleteFolder.exists()){
			File[] deleteFolderList = deleteFolder.listFiles();
			
			for (int i = 0; i < deleteFolderList.length; i++) {
				if(deleteFolderList[i].isFile()) {
					deleteFolderList[i].delete();
				}else {
					deleteFile(deleteFolderList[i].getPath());
				}
				deleteFolderList[i].delete(); 
			}
			deleteFolder.delete();
		}
	}
}
