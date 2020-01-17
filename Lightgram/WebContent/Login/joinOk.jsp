<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix ="c"  uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<html>
<head>
<title>Lightgram</title>
<script src="jquery-3.4.1.min.js"></script>

<style>

	#fd1{
		width:350px; 
		margin:0 auto; 
		border:none;
		border-radius:10px;
		background:white;
		margin-top:50px;
		}
	
	body{
		background:#fafafa;
	}

	button{
		color:gray;
		border-radius:10px;
	}	

	.submitbtn{
		width:350px;
		height:40px;
		background:#FDF8A6;		
		border-right:none;
		border-left:none;
		border-top:none;
		border-bottom:none;
	}

	#img1{
		border-radius:200px;
	}
</style>
</head>
<body>
	<form name="joinform" action="logout.net">
		<div id="div">
		<fieldset id="fd1">
				<h1>${id}님!</h1>
				<h2>회원가입을 축하 합니다~</h2>
						
		<img src="image/cat_logo.jpg" id="img1">
		
		<div class="login">
			<br><a href="Newsfeed.do"><button type="button" class="submitbtn" id="btn1">버튼을 눌러 주변 친구를 찾아보세요</button></a>
			<br>
			<br><button type="submit" class="submitbtn" id="btn1">로그인 하러 가기</button>
			<br>
			<span id="message"></span>			
		</div>
		
	</fieldset>
	<br>
	
	</div>
</form>
</body>
</html>
