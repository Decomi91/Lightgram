$(document).ready(function() {
	
	$(window).scroll(function() {
		if ($(this).scrollTop() > 200) {
			$('#moveTop').fadeIn();
		} else {
			$('#moveTop').fadeOut();
		}
	});
	
	$('#moveTop').click(function() {
		$('html, body').animate({
			scrollTop : 0
		}, 400);
		return false;
	});
	
	$('#menu_icon').click(function() {
		$('#menu').slideToggle(200);
		$('.menu__width').animate({width: 'toggle'});
	})

})