<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<script src = "js/jquery-3.4.1.js"></script>
<jsp:include page="header.jsp"/>
<title>비밀번호 변경</title>
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
	}
	.pd{
		padding: 30px 20px 20px 40px;
	}
	.inp{
		width : 100%;
		height : 20%;
		padding : 30px;
	}
	.inp2{
		width : 100%;
		height : 20%;
		padding : 30px;
	}
	.inp3{
		width : 100%;
		height : 20%;
		padding : 30px;
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
		background :#1a8cff; 
		color : white;
		border : none;
		width : 50px;
		height : 25px;
		border-radius : 3px;
		margin-left : 195px;
		margin-top : 30px;	
	}
</style>
<script>
	$(function(){
		$('#myform').submit(function(){
			if ($.trim($('#newpass').val()) != $.trim($('#newpasscheck').val())){
				alert('새 비밀번호와 비밀번호 확인란의 비밀번호가 서로 다릅니다.');
				return false;
			}
		})
	})
</script>
</head>
<body>
<div class = "big">
	<div class = "half1">
		<div class = "pd"><a href = "Mypage.do">마이 페이지</a><br></div>
		<div class = "pd"><a href = "ProfileUpdate.do">프로필 변경</a><br></div>
		<div class = "pd"><a href = "passUpdate.net">비밀번호 변경</a><br></div>
		<div class = "pd"><a href = "logout.net">로그아웃</a><br></div>
		<div class = "pd"><a href = "member_delete_self.net">회원 탈퇴</a></div>
	</div>
	<form action = "passModify.net" method = "post" id = "myform">
		<div class= "half2">
			<div class = "inp">기존 비밀번호<br>
			<input type = "password" name = "password"></div>
			<div class = "inp2">새 비밀번호<br>
			<input type = "password" name = "newpassword" id = "newpass"></div>
			<div class = "inp3">비밀번호 확인<br>
			<input type = "password" name = "passwordcheck" id = "newpasscheck"></div>
			<button type = "submit" class = "change" style = "cursor:pointer">변경</button>
		</div>
		</form>
	</div>
</body>
</html>