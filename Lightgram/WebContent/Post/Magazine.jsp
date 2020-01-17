<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix ="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<jsp:include page="header.jsp"/>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src = "js/jquery-3.4.1.js"></script>
<link href="css/Magazine.css"  rel="stylesheet" type="text/css" />
<script>

</script>
</head>
<body>
	<main class="more">
	<div id="all">
		<div id="subject_hash1">#${id}</div>
		<div id="hash1_list">
		<table>
			<c:forEach var="list" items="${list}" varStatus="status">
			<c:if test="${status.count%2==1}">
			<tr>
				<td><div><a href="postView.do?id=${list.id}&itemNum=${list.itemNum}"><img src="id/${list.id }/${list.itemNum }/${list.pic_url}"></a></div></td>
			</c:if>
			<c:if test="${status.count%2==0}">
				<td><div><a href="postView.do?id=${list.id}&itemNum=${list.itemNum}"><img src="id/${list.id }/${list.itemNum }/${list.pic_url}"></a></div></td>
			</tr>	
			</c:if>
			<c:if test="${status.count==4}">
			</c:if>
			</c:forEach>
		</table>
		</div>
		<div id="subject_hash2">#해시태그2</div>
		<div id="hash2_list">
		<table>
			<tr>
				<td><div></div></td>
				<td><div></div></td>
			</tr>
			<tr>
				<td><div></div></td>
				<td><div></div></td>
			</tr>
		</table>
		</div>
		<div id="subject_loca1">지역1</div>
		<div id="loca1_list">
		<table>
			<tr>
				<td><div></div></td>
				<td><div></div></td>
			</tr>
			<tr>
				<td><div></div></td>
				<td><div></div></td>
			</tr>
		</table>
		</div>
		<div id="subject_loca2">지역2</div>
		<div id="loca2_list">
		<table>
			<tr>
				<td><div></div></td>
				<td><div></div></td>
			</tr>
			<tr>
				<td><div></div></td>
				<td><div></div></td>
			</tr>
		</table>
		</div>
	</div>
	<div class="menu__width"></div>
    </main>
</body>
</html>