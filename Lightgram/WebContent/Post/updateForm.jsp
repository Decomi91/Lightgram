<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/updateForm.css">
<script src="js/jquery-3.4.1.js"></script>
<script>
$(function(){
	var checkemail = true;
	$('form').submit(function(){
		if(!checkemail){
			alert('이메일 형식에 맞는 이메일을 입력하세요');
			$('input:eq(4)').val('');
			$('input:eq(4)').focus();
			$("#email_message").text('');
			return false;
		}
	})
	
	$("input:eq(4)").on('keyup', function(){
		$("#email_message").empty();
		//[A-Za-z0-9]와 동일한 것이 \w
		var pattern = /\w+@\w+[.]\w{3}/;
		var email = $("input:eq(4)").val();
		console.log(email);
		console.log(!pattern.test(email));
		if(!pattern.test(email)){
			checkemail=false;
		} else {
			checkemail=true;
		}//if
	});//keyup
	
	$(".cancelbtn").click(function(){
		history.back();
	});
});//ready
</script>
<jsp:include page="admin_header.jsp" />
</head>
<body>
	<form name="updateform" action="admin_member_update.net" method="post">
		<fieldset>
			<table>
				<tr>
					<td colspan="2">
						<img src="icons/cat_logo.jpg" />
					</td>
				</tr>
				<tr>
					<td>이름 </td>
					<td>
						<input type="text" name="id" value="${member.id }" id="id" required>
					</td>
				</tr>
				
				<tr>
					<td>비밀번호 </td>
					<td>
						<input type="password" name="pass" id="pass" value="${member.password}" required>
					</td>
				</tr>
				
				<tr>
					<td>이름</td>
					<td> 
						<input type="text" name="name" id="name" value="${member.name }">
					</td>
				</tr>
				
				<tr>
					<td>이메일주소</td>
					<td>
						<input type="email" name="email" id="email" value="${member.email }" required>
					</td>
				</tr>
				
				<tr>
					<td>
						<button type="submit">수정</button>
					
					</td>
					<td>
						<button type="button" onClick="history.go(-1)">취소</button>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>