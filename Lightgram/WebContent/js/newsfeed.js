
$(function(){
	toggleCount = 0;
	likeCount = 0;
	$('.moremore').on('click', '.moreIcon', function(e){
		toggleCount = toggleCount + 1;
		if(toggleCount == 2){
			$(this).next().slideToggle(200);
			toggleCount = 0;
		}
	});
	
	$('.moremore').on('click', '#heart_icon', function(){
		toggleCount = toggleCount + 1;
		if(toggleCount == 2){
			var postId = $(this).prev().prev().val();
			var postNum = $(this).prev().val();
			likeCount = likeCount * 1;
			if($(this).prev().prev().prev().val() != null){
				likeCount = $(this).prev().prev().prev().val().substring(0,1) * 1;	
			}
			
			var clicked = $(this);
			loginid = $("#newsfeedPost").val();
			$.ajax({
				type : "POST",
				data :  {"loginId" : $("#loginid").val(), "postId" : postId, "itemNum":postNum},
				url : "likeAction.do",
				success : function(rdata){
//					clicked.parent().empty();
					output = '';
					output += '<input type="hidden" value="' + postId +  '">'
								+'<input type="hidden" value="' + postNum +'">';
					if(rdata == 1){
						likeCount += 1;
						output += '<i class="fas fa-heart" id="heart_icon" style="color:red"></i>';
						output += '<div class="count_text">' + likeCount + '개</div>';
						clicked.parent().html(output);
					}else if (rdata==0){
						likeCount = likeCount - 1*1;
						output += '<i class="fas fa-heart" id="heart_icon" style="color:black"></i>';
						output += '<div class="count_text">' + likeCount + '개</div>';
						clicked.parent().html(output);
					} else{
						alert('좋아요 실패');
					}	
				}
			});
			toggleCount = 0;
		}
	});
	
	setDisable = 0;
	scrollSize = 3;
	
	function getList(){
		loginid = $("#newsfeedPost").val();
		
		$.ajax({
			type : "POST",
			data :  {"postId" : loginid, "scorllSize" : scrollSize},
			dataType : "json",
			url : "newsfeedPost.do",
			success : function(rdata){
				if(rdata.length>0){
					output = '';
					$(".moremore").empty();
					$.each(rdata[0],function(index, item){
						if(rdata[1].full < scrollSize){
							setDisable = 1;
						}
						output += '<div class="more">'
									+ '<div class="container">'
									+ '<table class="table table-borderless" style="width:760px">'
										+ '<thead>'
										+	'<tr id="newsfeed_header">'
										+ 	'<th width="80%">'
											+ '<a href="Search.do?search_word=' + this.id + '&&search_option=id_sel" style="color: black; text-decoration: none;">'
											+ '<img src="id/' + this.id + '/' + this.profileImg + '" style="border-radius: 25px; width:50px;"></a><div class="header_id">'
											+ '<a href="Search.do?search_word=' + this.id + '&&search_option=id_sel" style="color: black; text-decoration: none;">'+ this.id + '</a>';
		if(this.location != null){
			output += '<a href="Search.do?search_word=' + this.location + '&&search_option=location_sel" style="color: black; text-decoration: none;">' + this.location + '</a>';
		}else{
			output+='<span></span>';
		}
		output								+= '</div></th>'
											+ '<th width="20%" style="text-align: right;">';
		if(this.id == rdata[2] || this.id == 'admin'){								
			output								+= '<img class="moreIcon" src="icons/more.png" style="width:30px">'
												+ '<div class="newsfeed_menu">';
						if(this.id != rdata[2] && this.id != 'admin'){
							
						} else {
							output += '<div onClick="location.href=';
							output += "'postUpdate.do?id=" + this.id + "&&itemNum=" + this.itemNum + "'";
							output += '">수정</div>';
							output += '<div onClick="location.href=';
							output += "'postDelete.do?id=" + this.id + "&&itemNum=" + this.itemNum + "'";
							output += '">삭제</div>';
						}
						output						+='</div>'
		}
		output									+='</th>'
											+'</tr>';
						output		+='</thead>';
						output		+='<tbody>'
										+'<tr>'
											+'<td colspan="2" style="padding:0" >'
												+ '<a href="postView.do?id=' + this.id + '&itemNum=' + this.itemNum + '">'
													+'<img src="id/' + this.id + '/' + this.itemNum + '/' + this.pic_url + '" style="width:759px;"></a></td>'
										+'</tr>'
										+'<tr>'		
											+'<td style="height:10px;">'
											+'<div class="hearts"><input type="hidden" value="' + this.likes[1] +  '">'
											+'<input type="hidden" value="' + this.id +  '">'
											+'<input type="hidden" value="' + this.itemNum +'">';
						if(this.likes[0] == 1){
							output += '<i class="fas fa-heart" id="heart_icon" style="color:red"></i>';
						} else{
							output += '<i class="far fa-heart" id="heart_icon" style="color:black"></i>';
						}
						output					+='<div class="count_text">' + this.likes[1] + '개</div></div></td>'
											+'<td></td>'
										+'</tr>'
										+'<tr>'
											+'<td><span style="font:bolder">'
												+'<a href="Search.do?search_word=' + this.id + '&&search_option=id_sel" style="color: black; text-decoration: none;">'+ this.id + '</a></span>&nbsp;&nbsp;&nbsp;&nbsp;' + this.dataText + '</td>';
					if(this.alter_time != null){
						output += '<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + this.alter_Time + '</td>';
					} else{
						output += '<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + this.posted_Time + '</td>';
					}
					output				+='</tr>';
					count = 0;
					$.each(this.replied, function(indexes, itemss){
						count = count + 1;
						if(indexes < 3){
							output += '<tr>'
										+'<td><a href="Search.do?search_word=' + this.reply_id + '&&search_option=id_sel" style="color: black; text-decoration: none;">';
							if(this.reply_no != this.ref_no){
								output += '<img src="icons/reply.png" style="width:15px;">';
							}
							output			+='<img src="id/' + this.reply_id + '/' +this.profileImg + '" style="width:15px">' + this.reply_id + '</a>' 
											+ '&nbsp;&nbsp;&nbsp;&nbsp;' + this.comments  + '</td>'
										+'<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + this.reply_time +'</td>';
							output += '</tr>';
						}
					});
					if(count>3){
						output += '<tr>';
						output +=	'<td colspan=2><a href="postView.do?id=' + this.id + '&itemNum=' + this.itemNum + '">';
						output +=					'<div class="goPage">댓글 ' + count + '개 모두 보기</div></a></td>';
						output += '</tr>';
					}
					output				+= '<tr style="border-top:1px solid lightgray">'
											+'<td><input type="text" name="reply" placeholder="댓글쓰기" class="input_reply" style="border: none;" size="70"></td>'
											+'<td><div class="reply_update" style="color:#CEE3F6; font-style: italic; opacity: 1.0; margin:0px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;댓글달기</div>'
												+'<input type="hidden" value="' + this.id +'"><input type="hidden" value="' + this.itemNum + '"</td>'
										+'</tr>'
									+'</tbody>'
								+'</table>'
							+'</div>'
							+'<div class="menu__width">'
						+'</div>'
						+'</div>';
					});
					$(".moremore").append(output);
				}
			}
		});
	}
	getList();
	
	$('.moremore').on('keyup', '.input_reply', function(){
		var keys = $(this).val();
		if(keys != null && keys != ""){
			$(this).parent().next().children().css('color', '#013ADF');
			$(this).parent().next().children().css('cursor', 'pointer');
		} else {
			$(this).parent().next().children().css('color', '#CEE3F6');
			$(this).parent().next().children().css('cursor', 'default');
		}
	});
	
	$('.moremore').on('click', '.reply_update', function(){
		toggleCount = toggleCount + 1;
		if(toggleCount == 2){
			updateReply = $(this);
			if($(this).css('cursor') == 'pointer'){
				loginid = $("#newsfeedPost").val();
				postId = $(this).next().val();
				postNum = $(this).next().next().val();
				comments = $(this).parent().prev().children().val();
				
				if(comments == ""){
					return;
				}
				$.ajax({
					type : "POST",
					data :  {"replyId" : $("#loginid").val(), "postId" : postId, "itemNum":postNum, "comments":comments},
					url : "newsfeedReplyAction.do",
					dataType : 'json',
					success : function(rdata){
						console.log(rdata);
						if(rdata != null){
							output = '';
							output += '<tr>'
										+'<td><a href="Search.do?search_word=' + rdata.reply_id + '&&search_option=id_sel" style="color: black; text-decoration: none;">'
										+'<img src="id/' + rdata.reply_id + '/' + rdata.profileImg + '" style="width:15px">' + rdata.reply_id + '</a>' 
										+ '&nbsp;&nbsp;&nbsp;&nbsp;' + rdata.comments  + '</td>'
										+'<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + rdata.reply_time +'</td>';
							output += '</tr>';
							if(updateReply.parent().parent().prev().children().attr('colspan') != 2){
								updateReply.parent().parent().before(output);
							}else{
								updateReply.parent().parent().prev().before(output);
							}
						} else{
							alert('댓글 달기 실패');
						}	
					}
				});
			}
			updateReply.parent().prev().children().val('');
			toggleCount = 0;
		}
	});
	
	$(window).on("scroll", function() {
		scrollHeight = $(document).height();
		scrollPosition = $(window).height() + $(window).scrollTop();	
		
		if(setDisable == 1){
			return;
		}
		
		if(Math.round( $(window).scrollTop()) == $(document).height() - $(window).height()){
			scrollSize += 1;
			getList();
		}
	});
})

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
				output = '<i class="fas fa-heart" id="heart_icon" style="color:black"></i>'
						+'<div class="count_text">'  + rdata[1] + '개</div>';
			}
			
			
			$(".heart .heart_count").append(output);
		}
	});
}
