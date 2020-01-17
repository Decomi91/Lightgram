$(function(){
	scrollSize = 30;
	function getList(){
		loginid = $("#loginId").val();
		word = $("#word").val();
		option = $("#option").val();
		
		$.ajax({
			type : "POST",
			data :  {"loginId" : loginId, "search_word" : word, "search_option":option, "scrollSize":scrollSize},
			dataType : "json",
			url : "SearchList.do",
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
})

