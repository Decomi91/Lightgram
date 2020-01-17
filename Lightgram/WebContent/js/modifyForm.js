$(document).ready(function() {
	var imgheight = $('#detail_picture').height();
	$('.more').css({
		'height': imgheight,
		'margin-top': -imgheight/2
	})
	
	$('.confirm').css({
		'height' : $('#detail_header').height()/2
	})
});