<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Lightgram</title>
<style>
fieldset{
		height : 500px;
		background : white;
		border-radius : 4px;
		border : 1px solid gray;
		margin: auto; 
		text-align : center;
		width : 350px;
	}
	body{
		background : #fafafa;
		box-sizing : border-box;
	}
	table{
		width : 100%;
		height : 100%;
	}
	form{
		height : 650px;
		
	}
	tr{
	width : 40%;
	}
	td{
		text-align: center;
		width : 80%;
		
	}
	input{
		background : #fafafa;
		box-shadow : none;
		border : 1px solid gray;
		border-radius : 5px;
		width : 90%;
		height : 30%;
	}
	img{
		width : 200px;
		height : 200px;
	}
	.h70{
		height : 70px;
	 	margin-top : 30px;
	}
	tr:first-child{width : 40px;}
	button{
	margin-top: 10px;
		border : none;
		border-radius : 10px;
		background :#FDF8A6;
		width : 90%;
		height : 30%;
	}
	button:hover{
		cursor: pointer;
	}
	a{
	 text-decoration:none;
	}
	form{
		margin-top : 40px; 
	}
	#tr1{
		width:50%
	}
	body{
		margin-top:100px;
	}
</style>

</head>
<body>
	<form name="joinform" action="idsearch.net" method="post">
		<fieldset>
		<table>
			<tr>
				<td><img src = "image/aa.PNG"></td>
			</tr>
			<tr>
				<td>로그인에 문제가 있나요 ? <br>
				<br>
				사용자 이름 또는 이메일을 입력 하세요</td>
			</tr>
			<tr id="tr1">		
			<td><input type = "text" name = "email" id="email" placeholder = "  이메일을 입력하세요">
			<button type = "submit">아이디 찾기</button></td>
			</tr>
			<td>
			<a href="pswsearch.net">비밀번호를 찾으시려면 클릭하세요</a>
			</td>
		</table>
	</fieldset>
	<fieldset class = "h70">
		<table>
			<tr>
				<td>계정이 없으신가요?<a href ="join.net">&nbsp;회원가입</a></td>
			</tr>
			<tr>
				<td>계정이 있으신가요?<a href ="login.net">&nbsp;로그인</a></td>
			</tr>
		</table>
	</fieldset>
	</form>
</body>
</html>