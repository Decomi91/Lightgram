<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="header.jsp" />
<LINK REL="SHORTCUT ICON" HREF="http://morfik.homeip.com/favicon.ico">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
    integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf"
    crossorigin="anonymous">
<link rel="stylesheet" href="css/modifyForm.css">
<script src="js/modifyForm.js"></script>
</head>
<body>
<form action="modifyAction.do" method="post">
<input type="hidden" name="itemNum"
	   value="${post.itemNum }">
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
						<input type="text" id="detail_profile_location" name="location" value="${post.location }">
					</div>
				</div>
				<div id="button">
					<input type="submit" class="confirm" value="수정완료">
					<input type="button" class="confirm" onClick="history.back();" value="수정취소">
				</div>
			</div>
			<div id="right_bottom">
				<textarea name="detail_text" id="detail_text"
				  class="detail_text"
			      rows="10">${post.dataText }</textarea>
			</div>
			<select name='level' id="open_level">
				<option value='1' selected>전체 공개</option>
				<option value="2">팔로우 공개</option>
				<option value="3">비공개</option>
			</select>
		</div>
	</div>
	<div class="menu__width"></div>
</div>
</form>
</body>
</html>