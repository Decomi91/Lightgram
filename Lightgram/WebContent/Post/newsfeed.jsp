<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix ="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" />
		<script src="js/jquery-3.4.1.js"></script>
		<script src="js/newsfeed.js"></script>
		<jsp:include page="header.jsp"/>
		<link rel="stylesheet" type="text/css" href="css/newsfeed.css" />
		<style>
			th > a { text-decoration: none;}
			table {background : white;border:1px solid lightgray; margin:0px auto;}
			body {background: #FAFAFA; margin-top: 120px;}
		</style>
	</head>
	<body>
		<input type="hidden" id="loginid" value="${id }" name="loginid">
		<script src="js/newsfeed.js"></script>
		
		<div class="moremore">
			
		</div>
	</body>
</html>