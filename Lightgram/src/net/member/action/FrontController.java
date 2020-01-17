package net.member.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.net")
public class FrontController extends javax.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontController() {
		super();
	}
	
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String RequestURI = request.getRequestURI();

		String contextPath = request.getContextPath();

		String command = RequestURI.substring(contextPath.length());

		ActionForward forward = null;
		Action action = null;
		if (command.equals("/login.net")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("Login/loginForm.jsp");
		} else if (command.equals("/join.net")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("Login/joinForm.jsp");
		} else if (command.equals("/joinProcess.net")) {
			action = new JoinProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/loginProcess.net")) {
			action = new LoginProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/member_update.net")) {
			action = new Member_updateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}  else if (command.equals("/logout.net")) {
			action = new LogoutAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/passUpdate.net")) {
			action = new PassUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/passModify.net")) {
			action = new PassModifyAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/admin.net")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("admin/adminPage.jsp");
		}else if(command.equals("/member_list.net")) {
			action = new MemberListAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/admin_member_update.net")) { 
			action = new Admin_Member_updateAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/idsearch.net")) {
			action = new IdsearchAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/passsearch.net")) {
			action = new PswsearchAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/joinok.net")) {
			action = new idokAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/follow.net")) {
			action = new FollowAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/pswsearch.net")) {
	   		forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("Login/pwdSearch.jsp"); 		
		}	else if(command.equals("/Idsearch.net")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("Login/idSearch.jsp");
		}else if(command.equals("/member_delete_self.net")) {
			action = new Member_Delete_SelfAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/member_delete_decide.net")) {
			action = new Member_Delete_Decide();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/emailcheck.net")) {
			action = new EmailCheckAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/idcheck.net")) {
			action = new IdCheckAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}
}