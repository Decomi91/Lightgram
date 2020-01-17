package net.post.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.member.db.Member;
import net.member.db.MemberDAO;

public class ProfileImgUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		
		String id = request.getSession().getAttribute("id").toString();
		
		String path = "D:\\KH\\Semi_project\\Lightgram\\WebContent\\id\\"+id; 
		
		String realFoler = "";
		String saveFolder = id;
		
		int fileSize = 100*1024*1024; 
		
		ServletContext sc = request.getServletContext();
		realFoler = sc.getRealPath(saveFolder);
		
		System.out.println("realFolder = " + realFoler);
		boolean result = false;
		
		File oldFile = new File(path);
		File[] delProfileImg = oldFile.listFiles();
		for(File delProfile : delProfileImg) {
			if(delProfile.isFile())
				delProfile.delete();
		}
		MultipartRequest multi = new MultipartRequest(request, path, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		String fileName = multi.getFilesystemName("profileImg");
		oldFile = new File(path + "\\" + fileName);
		fileName = id + fileName.substring(fileName.lastIndexOf("."));
		File newFile = new File(path + "\\" +fileName);
		oldFile.renameTo(newFile);
		
		MemberDAO dao = new MemberDAO();
		result = dao.profileImgUpdate(id, fileName);
		
//		String name = request.getParameter("name");
//		String email = request.getParameter("email");
//		Member m = new Member();
//		
//		m.setName(request.getParameter("name"));
//		m.setEmail(request.getParameter("email"));
//		
//		boolean result2 = dao.membermodify(m);
		
		if(!result) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('변경시 문제발생'); history.back() </script>");
			out.close();
			return null;
		} 
//		else if(!result2) {
//			forward.setRedirect(false);
//			request.setAttribute("message", "������ ���� ����");
//			forward.setPath("error/error.jsp");
//			return forward;
//		}
//		request.setAttribute("name", name);
//		request.setAttribute("email", email);
//		forward.setRedirect(true);
//		forward.setPath("/Lightgram/Mypage.do");
		return null;
	}

}
