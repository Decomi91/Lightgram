<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<script src = "js/jquery-3.4.1.js"></script>
<script src = "js/profile.js"></script>
<jsp:include page="header.jsp"/>
<link href="css/profile.css"  rel="stylesheet" type="text/css" />
</head>
<body>
	<body>
	<div class = "big">
	<div class = "half1">
		<div class = "pd"><a href = "Mypage.do">마이 페이지</a><br></div>
		<div class = "pd"><a href = "ProfileUpdate.do">프로필 변경</a><br></div>
		<div class = "pd"><a href = "passUpdate.net">비밀번호 변경</a><br></div>
		<div class = "pd"><a href = "logout.net">로그아웃</a><br></div>
		<div class = "pd"><a href = "member_delete_self.net">회원 탈퇴</a></div>
	</div>
				
		<div class = "half2">
			<img name = "ima" src = "id/${id }/${pic_url}">
			<div class = "pd2_1">이름</div>
			<div class = "pd2">이메일</div>
		</div>
		<div class = "half3">
			<form action = "profileImgUpdate.do" method = "post" enctype = "multipart/form-data"  name = "myform" id = "myform"><div class = "ig">
				<span name = "filevalue" id ="filevalue"></span>
				</div>
				<div>
					<span class = "idv">&nbsp;${id }</span>
				</div>
				<div class = "ig2">
					<div class = "change">&nbsp;
						<label class = "pf_change" style = "cursor:pointer">프로필 사진 변경하기
							<input type = "file" name = "profileImg" class = "ipfile" id = "file">
						</label>
					</div>
				</div>
			</form>
			<form action = "member_update.net" method = "post">
			<div><input type = "text" class = "ip" name = "name" value = "${member.name }"></div>
			<div><input type = "text" class = "ip"name = "email" value = "${member.email }"></div>
			<div><button type = "submit" class = "change2" style = "cursor:pointer">변경</button></div></form>
			</form>
		</div>
	</div>
</body>
</html>