
function getFullPath(){
	var fullPath = $('#contextPath').val()+"/"+
					$('#brandPath').val()+"/"+
					$('#regionPath').val()+"/"+
					$('#screenPath').val()+"/"+
					$('#startupPath').val();
	$('#showFullPath').text(fullPath);
	return fullPath;
}

$(document).ready(function() {
	
	$('#brandPath').css('max-width', 50);
	$('#regionPath').css('max-width', 50);
	$('#startupPath').css('width', 350);
	$('#submitButton').css('width', 330).css('height', 50);
	
	getFullPath();
	
	$('#brandPath').on('change', function(){
		getFullPath();
	});
	
	$('#regionPath').on('change', function(){
		getFullPath();
	});
	
	$('#startupPath').on('change', function(){
		getFullPath();
	});
	
	$('#submitButton').click(function() {
		var url = $('#contextPath').val()+"/"+
					$('#brandPath').val()+"/"+
					$('#regionPath').val()+"/"+
					$('#screenPath').val()+"/" +
					"initSession";
		
		var postData = $('#formWindowOpen').serializeArray();
		
		$.ajax({
			   type: "POST",
			   url : url,
			   data: postData,
			   statusCode: {
				    200: function(data){
				    	var formObj = $('#formWindowOpen');
				    	formObj.attr('action', getFullPath());
				    	formObj.submit();
				    }
			   },
			   timeout: 100000
		});
	});
});