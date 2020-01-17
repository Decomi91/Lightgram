<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원 탈퇴</title>
<script src = "js/jquery-3.4.1.js"></script>
<jsp:include page="header.jsp"/>
<link href="css/profile.css"  rel="stylesheet" type="text/css" />
<style>
	*{	 
		box-sizing : border-box;
	}
	body{
		margin-top : 150px;
	}
	.big{
		max-width : 760px;
		height : 500px;	
		margin : auto;
		border-collapse:collapse;
	}
	.half1{
		width : 30%;
		float : left;
		background : white;
		height : 400px;
		display:table; 
		border:1px solid lightgray;
		border-collapse:collapse;
	}
	.half2{
		width : 70%;
		float : left;
		background : white;
		height : 400px;
		border:1px solid lightgray; 
		border-width:1px 1px 1px 0px;
		text-align : left; -
	}
	.pd{
		padding: 30px 20px 20px 40px;
	}
	input{
		border-radius : 4px;
		border : 1px solid lightgray;
		width : 80%;
		height : 30px;
		padding : 5px;
		margin-top : 5px;
	}
	.change{
		background :#ff4d4d; 
		color : white;
		border : none;
		width : 50px;
		height : 25px;
		border-radius : 3px;
		margin-left : 195px;
		margin-top : 30px;	
	}
	.inp{
		width : 100%;
		height : 20%;
		padding : 30px;
	}
	.danger{
		margin-left : 25px;
		padding : 5px;
	}
	h3{
		margin-top : 5px;
	}
</style>
</head>
<body>
<div class = "big">
	<div class = "half1">
		<div class = "pd"><a href = "Mypage.do">마이 페이지</a><br></div>
		<div class = "pd"><a href = "ProfileUpdate.do">프로필 변경</a><br></div>
		<div class = "pd"><a href = "passUpdate.net">비밀번호 변경</a><br></div>
		<div class = "pd"><a href = "logout.net">로그아웃</a><br></div>
		<div class = "pd"><a href = "member_delete.net">회원 탈퇴</a></div>
	</div>
	<form action = "member_delete_decide.net" method = "post" id = "myform">
		<div class= "half2">
			<div class = "danger"><h3>정말 삭제하시겠습니까?</h3>
			<h3>삭제하시려면 비밀번호를 입력해주세요</h3></div>
			<div class = "inp">비밀번호<br>
			<input type = "password" name = "password"></div>
			<button type = "submit" class = "change">삭제</button>
		</div>
		</form>
	</div>
	
</body>
</html>