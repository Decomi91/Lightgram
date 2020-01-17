<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix ="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src = "js/jquery-3.4.1.js"></script>
<link href="css/mypage.css"  rel="stylesheet" type="text/css" />
<script>
$(function(){
	$(".update").click(function(){
		location.href="ProfileUpdate.do";
	});
	
	function follower(){
		$.ajax({
			type : "post",
			url : "followercheck.do",
			data : {'id' : '${sessionScope.id}'},
			success : function(rdata){
				$('#follower').empty();
				$('#follower').append(rdata);
			}
		})
	}
	follower();
	
	function following(){
		$.ajax({
			type : "post",
			url : "followingcheck.do",
			data : {'id' : '${sessionScope.id}'},
			success : function(rdata){
				$('#following').empty();
				$('#following').append(rdata);
			}
		})
	}
	following();
	
	function postcount(){
		$.ajax({
			type : "post",
			url : "postCount.do",
			data : {'id' : '${sessionScope.id}'},
			success : function(rdata){
				$('#postCount').empty();
				$('#postCount').append(rdata);
			}
		})
	}
	postcount();
	
	function followerlist(){
		$.ajax({
			type : "post",
			url : "followerlist.do",
			data : {'id' : '${sessionScope.id}'},
			dataType : 'json',
			success : function(rdata){
				$('#modal_follower').empty();
				output = '';
				var count = 0;
				$.each(rdata[0], function(index, data){
					count = count + 1;
				});
				for(var i=0; i<count; i++){
					output += "<div class=\'modal-body\' id=\'modal_follower\'><img src=\'id/"+ rdata[0][i]  + "/" + rdata[1][i] +"' width='30px' style='border-radius:50px'>  " + rdata[0][i];
					output += "<a href='follow.net?selectedId=" + rdata[0][i] + "')'><button type='button' class='btn";
					
					if(rdata[2][i] == 1){
						output += " btn-light' style='float:right;'>팔로잉</button></a></div>";
					}else{
						output += " btn-primary' style='float:right;'>팔로우</button></a></div>";
					}
				}
				
				$('#modal_follower').append(output);
			}
		})
	}
	followerlist();
	
	function followinglist(){
		$.ajax({
			type : "post",
			url : "followinglist.do",
			data : {'id' : '${sessionScope.id}'},
			dataType : 'json',
			success : function(rdata){
				$('#modal_following').empty();
				output = '';
				var count = 0;
				$.each(rdata[0], function(index, data){
					count = count + 1;
				});
				for(var i=0; i<count; i++){
					output += "<div class=\'modal-body\' id=\'modal_follower\'>";
					output +=	"<a href='Search.do?search_word=" + rdata[0][i] + "&&search_option=id_sel'>";
					output += 		"<img src='id/"+ rdata[0][i]  + "/" + rdata[1][i] +"' width='30px' style='border-radius:50px'>  " + rdata[0][i] + "</a>";
					output += "<a href='follow.net?selectedId=" + rdata[0][i] + "')'><button type='button' class='btn btn-light' style='float:right;'>팔로잉</button></a></div>";
				}
				count = 0;
				$.each(rdata[2], function(index, data){
					output += "<div class=\'modal-body\' id=\'modal_follower\'>";
					output +=	"<a href='Search.do?search_word=" + this + "&&search_option=hash_sel'>#" + this + "</a>";
					output += "<a href='follow.net?selectedHash=" + this + "')'><button type='button' class='btn btn-light' style='float:right;'>팔로잉</button></a></div>";
				});
				
				$('#modal_following').append(output);
			}
		})
	}
	followinglist();
})
</script>
<jsp:include page="header.jsp"/>
</head>
<body style = "margin-top : 100px;">
	<div class = "mypage">
	<div class="main">
       	<div class = "bg">
       		<div class = "gg">
       			<div class = "profile"><img src = "id/${id }/${pic_url}" class = "profile"></div>
       		</div>
       		<div class = "gg2">
       			<div class = "h_font">${id }&nbsp;<span class = "name">${name }</span>&nbsp;<button class = "update">프로필 설정</button>
       			</div>
       		</div>
       		<div class = "gg3">
       		<span class = "email">${email }</span>
       		
       		</div>
       		<div class = "gg4">	
       			
       			<div class = "board"><h3 class = "m15">게시글</h3><h3 class = "m10" id = "postCount"></h3></div>
       			<div class = "board"><a href = "#" data-toggle="modal" data-target="#myModal2"><h3 class = "m15">팔로워</h3><h3 class = "m10" id = "follower"></h3></a></div>
       			<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  					<div class="modal-dialog">
		    			<div class="modal-content">
	      					<div class="modal-header">
	        					<h3 class="modal-title" id="myModalLabel">팔로워</h3>
	      					</div>
	      					<div class="modal-body" id="modal_follower">
	      					</div>
		      				<div class="modal-footer">
			        			<button type="button" class="btn btn-primary" data-dismiss="modal">닫기</button>
		      				</div>
	    				</div>
		  			</div>
				</div>
				
       			<div class = "board"><a href = "#" data-toggle="modal" data-target="#myModal"><h3 class = "m15">팔로잉</h3><h3 class = "m10" id = "following"></h3></a></div> 
		       	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
		 			<div class="modal-dialog">
		   				<div class="modal-content">
	    					<div class="modal-header">
	      						<h3 class="modal-title" id="myModalLabel2">팔로잉</h3>
	     					</div>
	    					<div class="modal-body" id="modal_following">
	    					</div>
		    				<div class="modal-footer">
		       					<button type="button" class="btn btn-primary" data-dismiss="modal">닫기</button>
		     				</div>
		   				</div>
			  			</div>
					</div>
       		</div>
       	</div>
       	<div class = "bg2">
	       	<c:forEach var="list" items="${profileList }" varStatus="status">
	       		<c:if test="${status.count %3 == 1 }">
	       			<div class="bg3">
					<div class="pi">		       		
		       			<a href="postView.do?id=${id}&&itemNum=${list.itemNum}"><img src="id/${id }/${list.itemNum }/${list.pic_url}"  class = "imagev"></a>
	       			</div>
	    		</c:if>
       			<c:if test="${status.count %3 == 2 }">
       				<div class="pi">		
		       			<a href="postView.do?id=${id}&&itemNum=${list.itemNum}"><img src="id/${id }/${list.itemNum }/${list.pic_url}"   class = "imagev"></a>
	       			</div>
       			</c:if>
	       		<c:if test="${status.count%3 == 0 }">
	       			<div class = "pi">
		       			<a href="postView.do?id=${id}&&itemNum=${list.itemNum}"><img src="id/${id }/${list.itemNum }/${list.pic_url}"   class = "imagev"></a>
	       			</div>
	    </div>
	       			<br>
	       		</c:if>
	       		<c:if test="${status.last }">
			    	<c:if test="${fn:length(profileList)%3 == 2}">
			       		<div class = "pi"></div>
			       	</c:if>
		       	</c:if>
	       	</c:forEach>
		       	<c:if test="${empty profileList}">
		       		<div class = "empty_pf"><a href = "AddF.do">등록된 사진이 없습니다.</a></div>
		       		<div class = "empty_pf2"><a href = "AddF.do">사진을 등록하러 가볼까요?</a></div>
		       	</c:if>
	       	</div>
       	</div>
	</div>
	</div>
	       	<div class="menu__width" style="width:200px"></div>
</body>
</html>