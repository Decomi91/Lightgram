  $(function(){	  
	  $("#filevalue").text("사진을 올려주세요").css("color", "gray");
	  
	  $("#upfile").change(function(){
		    var ext = this.value.match(/\.(.+)$/)[1].toLowerCase();
		    switch (ext) {
		        case 'jpg':
		        case 'jpeg':
		        case 'png':
		        case 'gif':
		  		  $("#preview").css("display", "inline");
					$("#filevalue").val('');
					console.log("fakepath="+$(this).val());
					var inputfile=$(this).val().split("\\");
					$("#filevalue").text(inputfile[inputfile.length-1]);
					imageChange();
					$('.picture').css("display","none");
					$('#preview').css({
						'max-width': '600px',
						'height': 'auto'
					})
					$('.preview').css("display", "flex");
		            break;
		        default:
		            alert('사진 파일만 올려주세요');
		            this.value = '';
		            $("#filevalue").text("사진을 올려주세요").css("color", "gray");
		            $('.picture').css("display","block");
		            $("#preview").attr('src', '');
		    }
		});
	  
	  $("#filevalue").click(function(){
		 $("#upfile").click(); 
	  });

  })
  
  function imageChange(event){
	var input=document.boardform.pic_url;
	var fReader=new FileReader();
	fReader.readAsDataURL(input.files[0]);
	fReader.onloadend=function(event){
		
		console.log(event.target.result);		
		document.a.src=event.target.result;
	}
}