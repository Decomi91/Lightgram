<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix ="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="header_search.jsp" />
<LINK REL="SHORTCUT ICON" HREF="http://morfik.homeip.com/favicon.ico">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="js/jquery-3.4.1.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
    integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf"
    crossorigin="anonymous">
	<link rel="stylesheet" href="css/Search.css">
	<script src="js/Search.js"></script>
	<style>
		a > img{width: 150px; height: 150px;}
		.search_text {
			font-size: xx-large;
		}
	</style>
  </head>
  <body style="background:#fafafa">
  <div class="search_result">
   <div style="width:760px;" id="search_result">
   			<c:if test="${empty requestScope.search_word}">
   				<span class="search_text">${id}</span>
			</c:if>
   			<c:if test="${!empty requestScope.search_word}">
				<br><span class="search_text" style="float:left">${requestScope.search_option} : 검색 결과</span>
				<br><br><br><br><span class="search_text">
					<c:if test="${requestScope.search_option eq '해시태그'}">#</c:if>${requestScope.search_word}</span>
				<c:if test="${requestScope.search_option eq 'ID' || requestScope.search_option eq '해시태그' }">
					<c:if test="${requestScope.list != null && requestScope.search_word != sessionScope.id}">
						<c:if test="${requestScope.search_option eq 'ID' }">
							<c:if test="${requestScope.followStatus == 0}">
								<a href="follow.net?selectedId=${requestScope.search_word}&&path=Search.do_search_word=${requestScope.search_word}+search_option=id_sel">
									<button type="button" class="btn btn-primary" id="follow" >팔로우</button>
								</a>
							</c:if>
							<c:if test="${requestScope.followStatus == 1}">
								<a href="follow.net?selectedId=${requestScope.search_word}&&path=Search.do_search_word=${requestScope.search_word}+search_option=id_sel">
									<button type="button" class="btn btn-light" id="follow">팔로잉</button>
								</a>
							</c:if>
						</c:if>
					</c:if>
					<c:if test="${requestScope.search_option eq '해시태그'}">
						<c:if test="${requestScope.followStatus == 0}">
							<a href="follow.net?selectedHash=${requestScope.search_word}&&path=Search.do_search_word=${requestScope.search_word}+search_option=hash_sel">
								<button type="button" class="btn btn-primary" id="follow" >팔로우</button>
							</a>
						</c:if>
						<c:if test="${requestScope.followStatus == 1}">
							<a href="follow.net?selectedHash=${requestScope.search_word}&&path=Search.do_search_word=${requestScope.search_word}+search_option=hash_sel">
								<button type="button" class="btn btn-light" id="follow">팔로잉</button>
							</a>
						</c:if>
					</c:if>
				</c:if>
			</c:if>
   		</div>
	<div class="menu__width"></div>
	</div>
   <main class="more">
   <input type="hidden" value="${requestScope.search_word }" id="word">
   <input type="hidden" value="${requestScope.search_option }" id="option">
   <input type="hidden" value="${sessionScope.id }" id="loginId">
  	 <div>
		<table>
			<thead>
				<tr class="first">
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<c:forEach var="list" items="${list}" varStatus="status">
				<c:if test="${status.count%3==1}">
				<tr>
					<td><div><a href="postView.do?id=${list.id}&itemNum=${list.itemNum}"><img src="id/${list.id }/${list.itemNum }/${list.pic_url}"></a></div></td>
				</c:if>
				<c:if test="${status.count%3==2}">
					<td><div><a href="postView.do?id=${list.id}&itemNum=${list.itemNum}"><img src="id/${list.id }/${list.itemNum }/${list.pic_url}"></a></div></td>
				</c:if>
				<c:if test="${status.count%3==0}"> 
					<td><div><a href="postView.do?id=${list.id}&itemNum=${list.itemNum}"><img src="id/${list.id }/${list.itemNum }/${list.pic_url}"></a></div></td>
				</tr>
				</c:if>
				<c:if test="${status.last}">
					<c:if test="${status.count%3==1}">
						<td></td><td></td></tr>	
					</c:if>
					<c:if test="${status.count%3==2}">
						<td></td></tr>
					</c:if>
					<c:set var="for_end" value="true"/>
				</c:if>
			</c:forEach>
		</table>
	  </div>
	<div class="menu__width"></div>
   </main>

 </body>
 </html>