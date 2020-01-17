<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<jsp:include page="/admin/admin_header.jsp" />
<style>
table caption {
	caption-side: top;
	text-align: center;
}
h1{
	text-align: center;
}
.center-block {
	display: flex;
	justify-content: center; /* 가운데 정렬 */
}
li .current{
	background:#faf7f7;
}
.container{
	margin-top: 100px;
	padding: 50px 150px;
} 
.top{
	margin: 30px;
	font-size: 30px;
}
th, td{
	text-align: center;
}
.input-group{
	float: right;
	width: 50%;
}
.page-item{
	margin: 0;
}
</style>
<script>
$(function(){
	var selectedValue = '${search_field}'
	if(selectedValue != '-1')
		$("#viewcount").val(selectedValue);
	
	$("button").click(function(){
		if($("input").val()==''){
			alert("검색어를 입력하세요");
			return false;
		}
	})
})
</script>
</head>
<body>
	<div class="container">
	<form action="member_list.net">
		<div class="input-group">
			<select id="viewcount" name="search_field">
				<option value="0" selected>아이디</option>
				<option value="1">이름</option>
				<option value="2">이메일</option>
			</select>
			<input name="search_word" type="text" class="form-control"
					placeholder="Search" value="${search_word }">
			<button class="btn btn-primary" type="submit">검색</button>
		</div>
	</form>
		<%--회원이 있는 경우 --%>
		<c:if test="${listcount > 0 }">
			<table class="table table-striped">
				<caption class="top">회원 목록</caption>
				<thead>
					<tr>
						<th colspan="3"></th>
						<th>
							<font size=3>회원 수 : ${listcount }</font>
						</th>
					</tr>
					<tr>
						<td>아이디</td>
						<td>이름</td>
						<td>이메일</td>
						<td>수정</td>
					</tr>
				</thead>
				<tbody>
					<c:set var="num" value="${listcount-(page-1)*10 }" />
					<c:forEach var="m" items="${totallist }">
						<tr>
							<td>${m.id }</td>
							<td>${m.name}</td>
							<td>${m.email}</td>
							<td><a href="admin_member_update.net?id=${m.id }">수정</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<div class="center-block">
				<div class="row">
					<div class="col">
						<ul class="pagination">
							<c:if test="${page <= 1 }">
								<li class="page-item"><a class="page-link" href="#">이전</a>
								</li>
							</c:if>
							<c:if test="${page > 1 }">
								<li class="page-item"><a
									href="member_list.net?page=${page-1 }&search_field=${search_field}&search_word=${search_word}" class="page-link">이전</a>
								</li>
							</c:if>

							<c:forEach var="a" begin="${startpage }" end="${endpage }">
								<c:if test="${a == page }">
									<li class="page-item">
										<a class="page-link current" href="#">${a }</a>
									</li>
								</c:if>
								<c:if test="${a != page }">
									<li class="page-item">
										<a href="member_list.net?page=${a }&search_field=${search_field}&search_word=${search_word}"
										   class="page-link">${a }</a>
										</li>
								</c:if>
							</c:forEach>

							<c:if test="${page >= maxpage }">
								<li class="page-item"><a class="page-link" href="#">다음</a>
								</li>
							</c:if>
							<c:if test="${page < maxpage }">
								<li class="page-item"><a
									href="member_list.net?page=${page+1 }&search_field=${search_field}&search_word=${search_word}" class="page-link">&nbsp;다음</a>
								</li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>

		</c:if>
		<c:if test="${listcount == 0 }">
			<br><br><br><br><br><br>
			<font size=5>검색된 회원이 없습니다.</font>
		</c:if>
	</div>
</body>
</html>