 $(function(){
	$('input[type="file"]').change(function () {
	    var ext = this.value.match(/\.(.+)$/)[1].toLowerCase();
	    switch (ext) {
	        case 'jpg':
	        case 'jpeg':
	        case 'png':
	        case 'gif':
	            $('myform').attr('disabled', false); 
	            break;
	        default:
	            alert('지원하지 않는 형식입니다');
	            this.value = '';
	    }
	})
	$('#file').change(function(){
		imageChange();
		uploadFile();
	})
	function imageChange(event){
		var input = document.myform.profileImg;
		var fReader = new FileReader();
		fReader.readAsDataURL(input.files[0]);
		fReader.onloadend = function(event){
				document.ima.src=event.target.result;
		}
	}
	function uploadFile(){
		
		var form = $('#myform')[0];
		var formData = new FormData(form);
		
		$.ajax({
			type : 'POST',
			url : 'profileImgUpdate.do',
			data : formData,
			contentType : false,
			processData : false        
		})
	}
 });
	
