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
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
    integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf"
    crossorigin="anonymous">
    <script src="js/add.js"></script>
    <link rel="stylesheet" href="css/add.css">
  </head>
  <body>
    <main class="more">
    <div id="menu_action">
	<form action="addAction.do" method="post" enctype="multipart/form-data" name="boardform">
	<div class="form-group">
	<label for="upfile">
	<img src="image/attach.png" alt="첨부" >
	</label>
	<input type="file" accept=".gif, .jpg, .png, jpeg" id="upfile" name="pic_url" >
	<span id="filevalue"></span>
	</div>
<div class="picture">
</div>
<div class="preview">
	<img id="preview" name="a">
</div>
<textarea name="content" cols="500" rows="5" placeholder="어떤 사진인지 설명해주세요"></textarea>
<br>
<div class="location_select">
<select name='level' id="open_level">
			<option value='1' selected>전체 공개</option>
			<option value="2">팔로우 공개</option>
			<option value="3">비공개</option>
</select>	
<input type="text" name="location" id="location" placeholder="지금 어디 계시나요?"> &nbsp;
<!--  <button type="submit" class="btn btn-primary">등록</button>-->
</div>
<div class="form-group">
<button type="submit" class="btn btn-primary">등록</button>
</div> 
</form>
</div>
<div class="menu__width"></div>
</main>

</body>
</html>