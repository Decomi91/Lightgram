<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
	integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf"
	crossorigin="anonymous" />
<link rel="stylesheet" type="text/css" href="css/header.css" />
<link rel="shortcut icon" href="icons/cat_logo.jpg">
<script src="js/jquery-3.4.1.js"></script>
<script src="js/header.js"></script>
<c:if test="${!empty id }">
<title>Lightgram | ${id} </title>
</c:if>
<c:if test="${empty id }">
<title>Lightgram</title>
</c:if>
</head>
<body>
	<header id="header">
		<div class="header__column">
		</div>
		<div class="header__column">
			<span class="header__icon"> 
				<a href="Newsfeed.do"> 
					<img src="icons/cat_logo.jpg" />
				</a>
			</span>
		</div>
		<div class="header__column">
			<span id="menu_icon"> <i class="fas fa-bars"></i>
			</span>
		</div>
	</header>
	<div id="menu">
		<ul>
			<li><div class="menu" onClick="location.href='Newsfeed.do'">Newsfeed</div></li>
			<li><div class="menu" onClick="location.href='Search.do'">Search</div></li>
			<li><div class="menu" onClick="location.href='AddF.do'">Add</div></li>
			<li><div class="menu" onClick="location.href='Mypage.do'">My page</div></li>
			<li><div class="menu" onClick="location.href='logout.net'">Logout</div></li>
		</ul>
		<p id="copyright">
			<a href="#">
				&copy; Lightgram
			</a>
		<p>
	</div>
	<div id="moveTop">
		<img src="icons/arrow_up.png" />
	</div>
</body>
</html>