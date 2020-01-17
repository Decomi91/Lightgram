$(document).ready(function() {
	var imgheight = $('#detail_picture').height();

	$('.more').css({
		'height': imgheight,
		'margin-top': -imgheight/2
	})
	$('#detail_comments').css({
		'height': $('#detail_picture').height()-$('#detail_header').height()-$('#detail_header').height()-$('#detail_header').height()/2
	})
	$('.detail_comment_text').css({
		'max-width': $('.detail_comment').width()-$('.detail_comment_id').width()-$('.detail_comment_date').width()-$('.detail_comment_buttons').width()*2
	})
});


$(function(){
	$('.moreIcon').click(function(){
		$(this).next().slideToggle(200);
	})
	$('#commentWrite').click(function(){
		buttonText = $('#commentWrite').val();
		console.log(buttonText);
		if($('#comment').val() == ''){
			return;
		}
		if(buttonText == '게시'){
			url = "replyWrite.do";
			data = {"reply_id" : $("#loginid").val(), "postid" : $("#postId").val(), "comment" : $('#comment').val(), "itemNum":$("#itemNum").val()}; 
		} else {
			url = "reReplyWrite.do";
			data = {"reply_id" : $("#loginid").val(), "postid" : $("#postId").val(), "comment" : $('#comment').val(), "itemNum":$("#itemNum").val(), "ref_no" : num};
		}
		$("#commentWrite").val("게시");
		$.ajax({
			type : "POST",
			data : data,
			url : url,
			success : function(result){
				if(result==1){
					getList();
					$('#comment').val('');
				} else{
					alert('댓글 입력시 오류 발생');
				}
			}
		});
	});
	prevNum = 0;
	$("#detail_comments").on('click', '.detail_comment_reply', function(){//바로 업데이트 클래스로 갈 수 없으니 코멘트를 경유해서 들어감
		num = $(this).parent().next().next().children().next().val(); //수정할 댓글번호를 저장합니다.
		console.log(num);
		if($("#commentWrite").val() == "댓글" && prevNum == num){
			$("#commentWrite").val("게시");
		}else{
			$("#commentWrite").val("댓글"); //등록버튼의 라벨을  '수정완료'로 변경합니다.
			prevNum = num;
		}
	});
	
	$("#detail_comments").on('click', '.remove', function(){//바로 업데이트 클래스로 갈 수 없으니 코멘트를 경유해서 들어감
		num = $(this).next().val(); //수정할 댓글번호를 저장합니다.
		console.log(num);
	    var conf = confirm('댓글을 삭제하시겠습니까?');
	    if(conf){
	    	var num = $(this).next().next().val(); //댓글번호
	    	
	    	$("#commentWrite").val("게시");
			 
	    	$.ajax({
				type:"post",
				url : "replyDelete.do",
				data : {"postid" : $("#postId").val(), "itemNum":$("#itemNum").val(), "reply_no": num},				
				success:function(result){
					if(result==1){
						$('#comment').val('');
						getList();
					}
				}
			})//ajax end
	    }
	})
	
	$('.heart').on('click', '#heart_icon',function(){
		$.ajax({
			type : "POST",
			data :  {"loginId" : $("#loginid").val(), "postId" : $("#postId").val(), "itemNum":$("#itemNum").val()},
			url : "likeAction.do",
			success : function(rdata){
				if(rdata != -1){
					getLikes();
				}else{
					alert('좋아요 실패');
				}	
			}
		});
	})
})
function getList(){
	$.ajax({
		type : "POST",
		data :  {"postId" : $("#postId").val(), "itemNum":$("#itemNum").val()},
		dataType : "json",
		url : "replyList.do",
		success : function(rdata){
			if(rdata.length>0){
				output = '';
				$("#detail_comments").empty();
				$.each(rdata,function(index, item){
					img = ''; 
					re = '';
					if(this.reply_no != this.ref_no)
						re = '<img src="icons/reply.png" style="width:15px;">';
					
					if(this.reply_id == $("#loginid").val() || $("#loginid").val() == 'admin'){
						img = "<img src='image/remove.png' width='15px' class='remove'>";
					}
					
					output += '<div class="detail_comment" style="display:flex;justify-content:space-between;align-items:center;"><div class="detail_comment_left">'
						+'<div class="detail_comment_id">'
							+ re +'<a href="Search.do?search_word=' + this.reply_id +'&&search_option=id_sel">'
								+'<img src="id/' + this.reply_id + '/' + this.profileImg + '" style="width:15px">'
								+ this.reply_id+'</div>'
							+'</a>'
						+'<div class="detail_comment_text">' +this.comments + '</div>'
						+ '<div class="detail_comment_reply">';
					if(re == '' ){
						output += '<img src="icons/bubble.png" style="width:15px" >';
					}
					output	+= '</div></div>'
						+ '<div class="detail_comment_date">' + this.reply_time + '</div>';
					output += '<div class="detail_comment_buttons">' + img + '<input type="hidden" value="' + this.ref_no + '">'
					+ '<input type="hidden" value="' + this.reply_no + '"></div>'
					+ '</div>';
				});
				$("#detail_comments").append(output);
			}
		}
	});
}
getList();
function getLikes(){
	$.ajax({
		type : "POST",
		data :  {"loginId" : $("#loginid").val(), "postId" : $("#postId").val(), "itemNum":$("#itemNum").val()},
		dataType : 'json',
		url : "likeList.do",
		success : function(rdata){
			output = '';
			
			$(".heart .heart_count").empty();
			if(rdata[0] == 1){
				output = '<i class="fas fa-heart" id="heart_icon" style="color:red"></i>'
						+'<div class="count_text">' + rdata[1] + '개</div>';
			} else{
				output = '<i class="far fa-heart" id="heart_icon" style="color:black"></i>'
						+'<div class="count_text">'  + rdata[1] + '개</div>';
			}
			
			
			$(".heart .heart_count").append(output);
		}
	});
}
getLikes();


