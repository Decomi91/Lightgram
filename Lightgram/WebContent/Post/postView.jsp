<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix ="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="js/jquery-3.4.1.js"></script>
<jsp:include page="header.jsp"/>
<link rel="stylesheet" href="css/postView.css">
</head>
<body>
<input type="hidden" id="loginid" value="${id }" name="loginid">
<input type="hidden" id="profileImg" value="id/${post.id }/${post.itemNum }/${post.pic_url}" name="profileImg">
<input type="hidden" id="postId" value="${post.id }" name="postId">
<input type="hidden" id="itemNum" value="${post.itemNum }" name="itemNum">
<script src="js/postView.js"></script>
<div class="more">
	<div id="detail_view">
		<div id="detail_picture">
			<img src="id/${post.id }/${post.itemNum }/${post.pic_url}">
		</div>
		<div id="detail_right">
			<div id="detail_header">
				<div id="detail_profile">
					<div id="detail_profile_pic">
						<a href='Search.do?search_word=${post.id }&&search_option=id_sel'><img src="id/${post.id }/${img}"></a>
					</div>
					<div id="detail_profile_id_location">
						<div id="detail_profile_id"><a href='Search.do?search_word=${post.id }&&search_option=id_sel'>${post.id }</a></div>
						<div id="detail_profile_location"><a href='Search.do?search_word=${post.location }&&search_option=location_sel'>${post.location }</a></div>
					</div>
				</div>
				<div class="moreIcon">
					<i class="fas fa-ellipsis-h"></i>
				</div>
				<div class="search_menu">
					<c:choose>
						<c:when test="${id eq post.id || id eq 'admin'}">
							<div onClick="location.href='postUpdate.do?id=${post.id}&itemNum=${post.itemNum}'">수정</div>
							<div onClick="location.href='postDelete.do?id=${post.id}&itemNum=${post.itemNum}'">삭제</div>
						</c:when>
						<c:otherwise>
							<div onClick="location.href='postReport.do?id=${post.id}&itemNum=${post.itemNum}'">신고</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div id="right_bottom">
				<div id="detail_text">
					${post.dataText }
				</div>
				<div id="detail_comments">
					<!-- <div class="detail_comment">
						<div class="detail_comment_left">
							<div class="detail_comment_id">id</div>
							<div class="detail_comment_text">text</div>
						</div>
						
						<div class="detail_comment_right">
							<i class="fa fa-reply" aria-hidden="true"></i>
						</div>
						
					</div> -->
				</div>
			</div>
			<div class="heart">
				<div class="heart_count">
					
				</div>
				<c:choose>
					<c:when test="${!empty post.alter_time}">
						<div class="time">
							${post.alter_time }
						</div>
					</c:when>
					<c:otherwise>
						<div class="time">
							${post.posted_time }
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="comment__insert">
				<input id="comment" type="text" name="comment" placeholder="댓글 달기..." />
				<input id="commentWrite" type="button" name="submit" value="게시" />
			</div>
		</div>
	</div>
	<div class="menu__width"></div>
</div>
</body>
</html>