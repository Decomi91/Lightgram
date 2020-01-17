<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Lightgram</title>
	      <script src="js/jquery-3.4.1.js"></script>
	      <script>
			$(function(){
				$(".join").click(function(){
					location.href="join.net";
				});
			})
	      </script>
	</head>
<link href="css/loginForm.css"  rel="stylesheet" type="text/css" /> 
</head>
<body>
	<form name="loginform" action="loginProcess.net" method="post">
		<div id="div">
		<fieldset id="fd1">
				<h1>Lightgram</h1>							
		<input type="text" name="id" placeholder="  아이디를 입력해주세요" 
		maxLength="30" id="id"><br>
		
		<br>
		<input type="password" name="pass" placeholder="  비밀번호" id="id2"
		maxLength="15"><br>
		
		
		<div class="login">
			<br><button type="submit" class="submitbtn" id="btn1">로그인</button>
			<div class="do">
				<div class="do1"></div><br>
				<div class="do2">또는</div><br>
				<div class="do3"></div>
			</div>		
			<a href="pswsearch.net" id="a1"><div id=d3>비밀번호를 잊으셨나요?</div></a>
			<br>
		</div>
	</fieldset>
	<br>
	<fieldset id="fd2">
			<div id="d1">계정이 없으신가요?</div>
			<br><buttun id="a2" class="join"><div id="d2">가입하기</div></a>
	</fieldset>
	</div>
</form>
</body>
</html>
