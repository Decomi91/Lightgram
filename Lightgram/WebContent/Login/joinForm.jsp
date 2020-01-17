<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Lightgram</title>
<script src="js/jquery-3.4.1.js"></script>
<script>
	$(function() {
		var checkid=false;
		var checkemail=false;
		$('#joinform').submit(function(e){
			if(checkid || checkemail){
				e.preventDefault();
				return;
			}
		});

		$("input:eq(0)").on('keyup', function() {
				//$(".idimage").empty();
				var pattern = /^\w{5,12}$/;
				var id = $("input:eq(0)").val();
				if(id==''){
					$(".idimage").css('opacity', '0');
					return;
				}
				if (!pattern.test(id)) {
					$(".idimage").css('opacity', '1');
					checkid=false;
					return;
				}
	
				$.ajax({
					url : "idcheck.net",
					type: "post",
					data : {"id" : id},
					success : function(resp) {
						if (resp == -1) {
							$(".idimage").css('opacity', '1');
							checkid=true;

						} else {
							$(".idimage").css('opacity', '0');
							chidkid=false;
						}
					}
				});
			})
			$("input:eq(2)").on('keyup',function() {
				//$(".eimg").empty();
				var pattern = /\w+@\w+[.]\w{3}/;
				var email = $("input:eq(2)").val();
				if(email==''){
					$(".eimg").css('opacity', '0');
					return;
			}
				if (!pattern.test(email)) {
					$(".eimg").css('opacity', '1');
					checkemail=false;					
					return;
				}
				$.ajax({
					url : "emailcheck.net",
					type: "post",
					data : {"email" : email},
					success : function(resp2) {
						if (resp2 == -1) {
							$(".eimg").css('opacity', '1');
							checkid=true;
							return;
						} else {
							$(".eimg").css('opacity', '0');
							chidkid=false;
							return;
						}
					}
				})
			});
	})
</script>

</head>
<link href="css/joinForm.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<form name="joinform" action="joinProcess.net" method="post" id="joinform">
		<fieldset>	
				
			<table>
				<tr style="height:40px;">
				<td id="td1"><img src="icons/cat_logo.jpg"><br></td>				
				</tr>
			</table>
			
			<table>	
			<tr>
			<td height=40px>친구들의 사진을 보려면 <br> 가입하세요
			</td></tr>
					
				<tr style="height:-60px;">
				<td>
					<br>	
				<div id="div1">
					<input type="text" name="id" placeholder="  아이디" 
					id="id" required></input>
					<span id="message"><img src="image/X2.JPG" class='idimage'></span>
				</div>
				</td>			
				</tr>
				
				<tr style="height:70px;">
					<td><input type="text" name="name" id="name" placeholder="  사용자 이름"
					value=""></td>
				</tr>
				
				<tr style="height:40px;">	
				<td>			
				<div id="div2">				
				<input type="email" name="email"id="email" placeholder="  이메일" required>
				<span id="email_message"><img src="image/X2.JPG" class="eimg"></span>
				</div>
				</td>
				</tr>
				
				<tr style="height:60px;">
					<td><input type="password" name="pass" id="pass"placeholder="  비밀번호" required></td>
				</tr>
				<br>
				<tr style="height:30px;">
					<td><button type="submit">가입하기</button></td>
				</tr>
				
				
			</table>
		</fieldset>
		<br><br>
		<fieldset class="h70">
			<table>
				<tr>
					<td>계정이 있으신가요?<a href="login.net">&nbsp;로그인</a></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>