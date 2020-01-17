package net.post.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.do")
public class PostFrontController extends javax.servlet.http.HttpServlet {
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=utf-8");
	String RequestURI = request.getRequestURI();
	
	String contextPath = request.getContextPath();
	
	String command = RequestURI.substring(contextPath.length());
	
	ActionForward forward = null;
	Action action = null;
	if(command.equals("/Newsfeed.do")) {
		action = new NewsfeedAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/Mypage.do")) {
		action = new MypageAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/Magazine.do")) {
		action = new MagazineAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/AddF.do")) {
		action = new AddF_Action();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/Search.do")) {
		action = new SearchAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/ProfileUpdate.do")) {
		action = new ProfileUpdateAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/addAction.do")) {
		action = new PostAddAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/profileImgUpdate.do")) {
		action = new ProfileImgUpdateAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}else if(command.equals("/modifyAction.do")) {
		action = new PostModifyAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/followercheck.do")) {
		action = new MyFollowerCheckAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/followingcheck.do")) {
		action = new MyFollowingCheckAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/followerlist.do")) {
		action = new FollowerListAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}else if(command.equals("/postCount.do")) {
		action = new PostCountAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/followinglist.do")) {
		action = new FollowingListAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/postDelete.do")) {
		action = new PostDeleteAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}else if(command.equals("/postView.do")) {
		action = new PostViewAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}else if(command.equals("/replyList.do")) {
		action = new ReplyListAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} else if(command.equals("/replyWrite.do")) {
		action = new ReplyWriteAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}else if(command.equals("/reReplyWrite.do")) {
		action = new ReReplyWriteAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}else if(command.equals("/replyDelete.do")) {
		action = new ReplyDeleteAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}else if(command.equals("/postUpdate.do")) {
		action = new PostModifyViewAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}else if(command.equals("/newsfeedPost.do")) {
		action = new NewsfeedPostAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}else if(command.equals("/likeList.do")) {
		action = new PostLikeListAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}else if(command.equals("/likeAction.do")) {
		action = new PostLikeAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}else if(command.equals("/newsfeedReplyAction.do")) {
		action = new NewsfeedReplyAction();
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
//	else if(command.equals("/SearchList.do")) {
//		action = new SearchListAction();
//		try {
//			forward = action.execute(request, response);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	if(forward != null) {
		if(forward.isRedirect()) {
			response.sendRedirect(forward.getPath());
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
		}
	}
}
private static final long serialVersionUID = 1L;

public PostFrontController() {
    super();
}

protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	doProcess(request,response);
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	doProcess(request, response);
	}
}