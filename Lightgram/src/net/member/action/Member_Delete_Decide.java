package net.member.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDAO;

public class Member_Delete_Decide implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		MemberDAO dao = new MemberDAO();
		HttpSession session = request.getSession();
		int result = 0;
		String id = session.getAttribute("id").toString();
		String pass = request.getParameter("password");
		result = dao.isId(id, pass);
		if (result == -1) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호가 다릅니다.');");
			out.println("history.back()");
			out.println("</script>");
			out.close();
			return null;
		}
		result = 0;
		result = dao.delete(id);
		if (result == -1) {
			System.out.println("회원 탈퇴 실패");
			forward.setRedirect(false);
			request.setAttribute("message", "회원탈퇴에 실패했습니다.");
			forward.setPath("error/error.jsp");
			return forward;
		}
		System.out.println("회원탈퇴 성공");
		deleteFile("c:\\Eclipse_ee\\Lightgram\\WebContent\\id\\"+id);
		response.setContentType("text/html;charset=utf-8");
		session.invalidate();
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('회원탈퇴 되었습니다.');");
		out.println("location.href = 'lightgram';");
		out.println("</script>");
		out.close();
		return null;
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