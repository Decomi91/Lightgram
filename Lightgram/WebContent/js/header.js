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
		var menu = $('#menu');
		if(menu.css("display") == "none"){
			menu.slideDown(200);
			width = $('.menu__width').width();
			$('.comment__insert').animate({
				right: $('.menu__width').width(),
				left: '55%'
			});
			$('.heart').animate({
				right: $('.menu__width').width(),
				left: '55%'
			});
			$('.search_menu').animate({
				right: $('.menu__width').width()
			});
		} else {
			menu.slideUp(200);
			$('.comment__insert').animate({
				right: '0px',
				left: '60%'
			});
			$('.heart').animate({
				right: '0px',
				left: '60%'
			});
			$('.search_menu').animate({
				right: '-80px'
			});
		}
		/*$('#menu').slideToggle(200);*/
		$('.menu__width').animate({width: 'toggle'});
	})
})